package model.Parser;

import controller.GasStation;
import model.Level;
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
import java.util.List;
import java.util.Properties;

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

        Element mainElement = doc.createElement(XMLConst.LEVEL_INFO);
        doc.appendChild(mainElement);
        mainElement.setAttribute(XMLConst.CURRENT_LEVEL, controller.getCurrentLevel() + "");

        List<Level> levels = controller.getLevels();

        for (Level level : levels) {
            Element newElem = doc.createElement(XMLConst.LEVEL_DATA);
            mainElement.appendChild(newElem);
            newElem.setAttribute(XMLConst.LEVEL_NAME, level.getLevelNumber() + "");
            newElem.setAttribute(XMLConst.COUNT_OF_CARS, level.getCountOfCars() + "");
            newElem.setAttribute(XMLConst.COUNT_OF_PUMPS, level.getCountOfPumps() + "");
            newElem.setAttribute(XMLConst.GOAL, level.getGoal() + "");
            newElem.setAttribute(XMLConst.SPEED, level.getSpeed() + "");
            newElem.setAttribute(XMLConst.COEFFICIENT, level.getCoefficient() + "");
        }

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
            List<Level> levels = statFileHandler.getLevels();
            if (currentLevel > levels.size()) {
                currentLevel = levels.size();
            }
            controller.setSavedLevel(currentLevel);
            controller.createLevels(levels);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
