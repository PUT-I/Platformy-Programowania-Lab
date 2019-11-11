package com.put.lab5

import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.XMLStreamReader

class ItemProvider implements Iterator<Item>, AutoCloseable {

    private final Reader reader
    private final XMLStreamReader xml
    private boolean isItem = false

    ItemProvider(String filename) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance()
        File f = new File(filename)
        try {
            reader = new InputStreamReader(new FileInputStream(f), "UTF-8")
            xml = factory.createXMLStreamReader(reader)
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e)
        }
    }

    @Override
    boolean hasNext() {
        if (isItem) {
            return true
        }
        try {
            while (xml.hasNext()) {
                xml.next()
                if (xml.getEventType() == XMLStreamReader.START_ELEMENT
                        && "item" == xml.getLocalName()) {
                    isItem = true
                    return true
                }
            }
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex)
        }
        return false
    }

    @Override
    Item next() {
        if (!hasNext()) {
            throw new NoSuchElementException()
        }
        try {
            Item item = new Item()
            while (xml.hasNext()) {
                xml.next()
                if (xml.getEventType() == XMLStreamReader.END_ELEMENT
                        && "item" == xml.getLocalName()) {
                    isItem = false
                    break
                }
                if (xml.getEventType() == XMLStreamReader.START_ELEMENT
                        && "id" == xml.getLocalName()) {
                    item.setId(Integer.parseInt(xml.getElementText()))
                }
                if (xml.getEventType() == XMLStreamReader.START_ELEMENT
                        && "price" == xml.getLocalName()) {
                    item.setPrice(Float.parseFloat(xml.getElementText()))
                }
                if (xml.getEventType() == XMLStreamReader.START_ELEMENT
                        && "name" == xml.getLocalName()) {
                    item.setName(xml.getElementText())
                }
                if (xml.getEventType() == XMLStreamReader.START_ELEMENT
                        && "category" == xml.getLocalName()) {
                    item.setCategory(xml.getElementText())
                }
                if (xml.getEventType() == XMLStreamReader.START_ELEMENT
                        && "description" == xml.getLocalName()) {
                    item.setDescription(xml.getElementText())
                }
            }
            return item
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex)
        }
    }

    @Override
    void close() throws XMLStreamException, IOException {
        try {
            xml.close()
        } finally {
            reader.close()
        }
    }

}

