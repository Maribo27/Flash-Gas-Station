package model.Parser;

import model.Level;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLParser extends DefaultHandler{
    private List<Level> levels;
    private Level level;
    private int currentLevel;


    XMLParser() {
        levels = new ArrayList<>();
        level = null;
    }

    int getCurrentLevel() {
        return currentLevel;
    }

    List<Level> getLevels() {
        return levels;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase(XMLConst.LEVEL_INFO)) {
            currentLevel = Integer.parseInt(attributes.getValue(XMLConst.CURRENT_LEVEL));
        }

        if (qName.equalsIgnoreCase(XMLConst.LEVEL_DATA)) {
            level = new Level();
            level.setLevelNumber(Integer.parseInt(attributes.getValue(XMLConst.LEVEL_NAME)));
            level.setCountOfCars(Integer.parseInt(attributes.getValue(XMLConst.COUNT_OF_CARS)));
            level.setCountOfPumps(Integer.parseInt(attributes.getValue(XMLConst.COUNT_OF_PUMPS)));
            level.setGoal(Integer.parseInt(attributes.getValue(XMLConst.GOAL)));
            level.setSpeed(Integer.parseInt(attributes.getValue(XMLConst.SPEED)));
            level.setCoefficient(Integer.parseInt(attributes.getValue(XMLConst.COEFFICIENT)));
            levels.add(level);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }
}
