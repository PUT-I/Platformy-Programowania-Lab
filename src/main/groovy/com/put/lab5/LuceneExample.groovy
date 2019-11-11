package com.put.lab5

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.Query
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.TopDocs
import org.apache.lucene.store.Directory
import org.apache.lucene.store.RAMDirectory

class LuceneExample {

    static void run() {
        Directory index = new RAMDirectory()

        fillIndex(index)

        ScoreDoc[] hits = searchIndex(index)

        printSearchResults(index, hits)
    }

    private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document()
        doc.add(new TextField("title", title, Field.Store.YES))
        doc.add(new StringField("isbn", isbn, Field.Store.YES))
        w.addDocument(doc)
    }

    private static void fillIndex(Directory index) {
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer())
        new IndexWriter(index, config).withCloseable { IndexWriter writer ->
            addDoc(writer, "Lucene in Action", "193398817")
            addDoc(writer, "Lucene for Dummies", "55320055Z")
            addDoc(writer, "Managing Gigabytes", "55063554A")
            addDoc(writer, "The Art of Computer Science", "9900333X")
        }
    }

    private static ScoreDoc[] searchIndex(Directory index) {
        IndexReader reader = DirectoryReader.open(index)
        IndexSearcher searcher = new IndexSearcher(reader)

        Query q = new QueryParser("title", new StandardAnalyzer())
                .parse('Lucene')

        final int HITS_PER_PAGE = 10
        TopDocs docs = searcher.search(q, HITS_PER_PAGE)
        return docs.scoreDocs
    }

    private static printSearchResults(Directory index, ScoreDoc[] hits) {
        IndexReader reader = DirectoryReader.open(index)
        IndexSearcher searcher = new IndexSearcher(reader)

        println("Found " + hits.length + " hits.")
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc
            Document d = searcher.doc(docId)
            println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"))
        }
    }

}
