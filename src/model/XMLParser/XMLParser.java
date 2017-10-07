package model.XMLParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler{
    private int currentLevel;

    XMLParser() {
        currentLevel = 1;
    }

    int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase(XMLConst.CURRENT)) {
            currentLevel = Integer.parseInt(attributes.getValue(XMLConst.LEVEL));
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }
}
