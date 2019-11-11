package com.put.lab5


import org.apache.lucene.search.TopDocs

class Lab5 {

    static void exercise1() {
        final String ITEMS_XML_PATH = "src/main/resources/xml/items.xml"
        final String INDEX_PATH = "src/main/resources/temp/index"

        ItemIndexer itemIndexer = new ItemIndexer(INDEX_PATH)
        itemIndexer.clearIndex()

        itemIndexer.addItems(new ItemProvider(ITEMS_XML_PATH))

        TopDocs queryResults1 = itemIndexer.search("name", "Gruszka AND NOT Silikonowa")
        TopDocs queryResults2 = itemIndexer.search(
                'name:"Gruszka fotograficzna" AND description:(gum? OR miękka)'
        )
        TopDocs queryResults3 = itemIndexer.search("category", 'To*')
        TopDocs queryResults4 = itemIndexer.search("name", "Gruszkacz~")
        TopDocs queryResults5 = itemIndexer.rangeSearch("price", 1000.0f, 1398.0f)

        println("Query results, point a)")
        itemIndexer.printSearchResults(queryResults1)
        println()

        println("Query results, point b)")
        itemIndexer.printSearchResults(queryResults2)
        println()

        println("Query results, point c)")
        itemIndexer.printSearchResults(queryResults3)
        println()

        println("Query results, point d)")
        itemIndexer.printSearchResults(queryResults4)
        println()

        println("Query results, point e)")
        itemIndexer.printSearchResults(queryResults5)
        println()
    }

    static void main(String[] args) {
        exercise1()
    }

}