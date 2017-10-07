package model.XMLParser;

import controller.GasStation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Maria on 07.10.2017.
 */
public class XMLFile {
    
    private String fileName;
    private GasStation controller;

    public XMLFile(String fileName, GasStation controller) {
        this.fileName = fileName;
        this.controller = controller;
    }
    
    public void writeFile() throws IOException, TransformerException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element mainElement = doc.createElement(XMLConst.STATE);
        doc.appendChild(mainElement);

        Element newElem = doc.createElement(XMLConst.CURRENT);
        mainElement.appendChild(newElem);
        newElem.setAttribute(XMLConst.LEVEL, controller.getCurrentLevel() + "");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        Properties outFormat = new Properties();
        outFormat.setProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperties(outFormat);
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public void readFile() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLParser statFileHandler = new XMLParser();
            saxParser.parse(new File(fileName), statFileHandler);

            int currentLevel = statFileHandler.getCurrentLevel();
            if (currentLevel < 1) currentLevel = 1;
            controller.setSavedLevel(currentLevel);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
