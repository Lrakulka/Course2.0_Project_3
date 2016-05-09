package com.homework.analyzer;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import gem_xsd.JewelType;
import gem_xsd.JewelType.VisualParameters;

public class SAXParseHandler extends DefaultHandler {
    List<JewelType> javels;
    private JewelType jewel;
    private byte elementPos;
    private VisualParameters visual;
	
    SAXParseHandler() {
	javels = new ArrayList<>();
	elementPos = -1;
    }
	
    @Override
    public void startElement(String uri, String localName, String qName, 
		Attributes attributes) throws SAXException {
	switch (qName) {
	    case "jewel" : 
	    	jewel = new JewelType();
	    	jewel.setId(attributes.getValue("id"));
	    	break;
	    case "Name" :
	    	elementPos = 1;
	    	break;
	    case "Preciousness" :
		elementPos = 2;
	    	break;
	    case "Origin" :
	    	elementPos = 3;
	    	break;
	    case "Visual_Parameters" : 
		visual = new VisualParameters();
		break;
	    case "Color" :
	    	elementPos = 4;
	    	break;
	    case "Transparency" :
    	    	elementPos = 5;
	    	break;
    	    case "Cut" :
    	    	elementPos = 6;
	    	break;	    	    
    	    case "Value" :
	    	elementPos = 7;
	    	break;
	}
    }
	
    @Override
    public void endElement(String uri, 
		String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("jewel")) {
    	javels.add(jewel);
        }
        if (qName.equalsIgnoreCase("Visual_Parameters")) {
    	jewel.setVisualParameters(visual);
        }
    }
	
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
	String value = new String(ch, start, length);
	switch (elementPos) {
	    case 1:
		jewel.setName(value);
		break;
	    case 2:
		jewel.setPreciousness(Boolean.valueOf(value));
		break;
	    case 3:
		jewel.setOrigin(value);
		break;
	    case 4:
		visual.setColor(value);
		break;
	    case 5:
		visual.setTransparency(Byte.valueOf(value));
		break;
	    case 6:
		visual.setCut(Byte.valueOf(value));
		break;
	    case 7:
		jewel.setValue(Double.valueOf(value));
		break;
	}
	elementPos = -1;
    }
}
