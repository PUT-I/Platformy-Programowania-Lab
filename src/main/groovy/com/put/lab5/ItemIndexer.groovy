package com.put.lab5

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.*
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.*
import org.apache.lucene.store.Directory
import org.apache.lucene.store.MMapDirectory

class ItemIndexer {

    private Directory index

    ItemIndexer(String directoryPath) {
        index = MMapDirectory.open(new File(directoryPath).toPath())
    }

    void addItems(ItemProvider provider) throws IOException {
        provider.withCloseable {
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer())
            new IndexWriter(index, config).withCloseable { IndexWriter writer ->
                while (provider.hasNext()) {
                    Item item = provider.next()
                    addItem(writer, item)
                }
            }
        }
    }

    private static addItem(IndexWriter writer, Item item) {
        Document doc = new Document()
        doc.add(new StringField("id", item.getId().toString(), Field.Store.YES))
        doc.add(new StoredField("price", item.getPrice()))
        doc.add(new FloatPoint("price", item.getPrice()))
        doc.add(new FloatDocValuesField("price", item.getPrice()))
        doc.add(new TextField("name", item.getName(), Field.Store.YES))
        doc.add(new TextField("category", item.getCategory(), Field.Store.YES))
        doc.add(new TextField("description", item.getDescription(), Field.Store.YES))
        writer.addDocument(doc)
    }

    private TopDocs executeQuery(Query query, Sort sort) {
        final int HITS_PER_PAGE = 1000
        IndexReader reader = DirectoryReader.open(index)
        IndexSearcher searcher = new IndexSearcher(reader)

        if (sort != null) {
            return searcher.search(query, HITS_PER_PAGE, sort)
        } else {
            return searcher.search(query, HITS_PER_PAGE)
        }
    }

    TopDocs search(String field, String query) {
        Query q = new QueryParser(field, new StandardAnalyzer())
                .parse(query)
        return executeQuery(q, null)
    }

    TopDocs search(String query) {
        Query q = new QueryParser("", new StandardAnalyzer())
                .parse(query)
        return executeQuery(q, null)
    }

    TopDocs rangeSearch(String field, float lowerBound, float upperBound) {
        Query query = FloatPoint.newRangeQuery(field, lowerBound, upperBound)
        Sort sort = new Sort(new SortedNumericSortField("price", SortField.Type.FLOAT))
        return executeQuery(query, sort)
    }

    void printSearchResults(TopDocs hits) {
        IndexReader reader = DirectoryReader.open(index)
        IndexSearcher searcher = new IndexSearcher(reader)

        println("Found " + hits.scoreDocs.length + " hits.")
        for (int i = 0; i < hits.scoreDocs.length; ++i) {
            int docId = hits.scoreDocs[i].doc
            Document d = searcher.doc(docId)
            println((i + 1) + ". " + d.get("id") + "\t"
                    + d.get("price") + "\t"
                    + d.get("name") + "\t"
                    + d.get("category"))
        }
    }

    void clearIndex() {
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer())
        new IndexWriter(index, config).withCloseable { IndexWriter writer ->
            writer.deleteAll()
        }
    }
}
