package com.homework.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import gem_xsd.JewelType;
import gem_xsd.JewelType.VisualParameters;

public class Util {
    /**
     * Validate xml file
     * @param xsdPath
     * @param xmlPath
     * @return true if file validate or false if not
     */
    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
	try {
    	     SchemaFactory factory = 
    		     SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    	     Schema schema = factory.newSchema(new File(xsdPath));
    	     Validator validator = schema.newValidator();
    	     validator.validate(new StreamSource(new File(xmlPath)));
    	} catch (IOException e) {    
    	     System.out.println("Exception: " + e.getMessage());
	     return false;
	} catch(SAXException e1) {
	     System.out.println("SAX Exception: " + e1.getMessage());
	     return false;
	}
	return true;
    }
    
    /**
     * Initialization of List<JewelType> from xml file.
     * Uses SAX parser.
     * @param xmlPath
     * @return list of JewelType
     */
    public static List<JewelType> initListSAX(String xmlPath) {
	SAXParseHandler userhandler = null;
	try {	
	    File inputFile = new File(xmlPath);
	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    SAXParser saxParser = factory.newSAXParser();
	    userhandler = new SAXParseHandler();
	    saxParser.parse(inputFile, userhandler);     
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (userhandler == null) {
	    return Collections.<JewelType>emptyList();
	}
	return userhandler.javels;
    }
    /**
     * Initialization of List<JewelType> from xml file.
     * Uses DOM parser.
     * @param xmlPath
     * @return list of JewelType
     */
    public static List<JewelType> initListDOM(String xmlPath) {
	List<JewelType> list = new ArrayList<>();
	JewelType jewel;
	try {
    	    File inputFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = 
               DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
    	    dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList jewelList = doc.getElementsByTagName("jewel");
            NodeList jewelChildrList, jawelVisualParametersList;
            Node jawelNode, jawelChildNode;
            for (int i = jewelList.getLength() - 1; i > -1; --i) {
        	jawelNode = jewelList.item(i);
        	jewel = new JewelType();
        	if (jawelNode.getNodeType() == Node.ELEMENT_NODE) {
        	    jewel.setId(((Element) jawelNode).getAttribute("id"));
        	}
        	jewelChildrList = jawelNode.getChildNodes();
        	for(int j = jewelChildrList.getLength() - 1; j > -1; --j) {
        	    jawelChildNode = jewelChildrList.item(j);
                    if (jawelChildNode.getNodeType() == Node.ELEMENT_NODE) {
                	Element element = (Element) jawelChildNode;
                	switch (element.getNodeName()) {
                	case "Name":
                		jewel.setName(element.getTextContent());
                		break;
                	case "Preciousness":
                		jewel.setPreciousness(Boolean.
                			parseBoolean(element.getTextContent()));
                		break;
                	case "Origin":
                	    	jewel.setOrigin(element.getTextContent());
                	    	break;
                	case "Value":
                	    	jewel.setValue(Double.parseDouble(element.getTextContent()));
                	    	break;
                	case "Visual_Parameters":
                	    jawelVisualParametersList = jawelChildNode.getChildNodes();
                	    VisualParameters parameters = new VisualParameters();
                	    for (int k = jawelVisualParametersList.getLength() - 1; k > -1; --k) {
                		if (jawelVisualParametersList.item(k).getNodeType() 
                			== Node.ELEMENT_NODE) {
                        	   Element visualParameters = (Element) jawelVisualParametersList.item(k);
                        	   switch (visualParameters.getNodeName()) {
                        	   case "Color":
    				      parameters.setColor(visualParameters.getTextContent());
    				      break;
        			   case "Transparency":
        			       parameters.setTransparency(Byte.parseByte(visualParameters.getTextContent()));
        			       break;
        			   case "Cut":
        			       parameters.setCut(Byte.parseByte(visualParameters.getTextContent()));
        			       break;
                        	   }
                		}
                	    }     
                	    jewel.setVisualParameters(parameters);
                	    break;
                	}
                    }
        	}
        	list.add(jewel);
            }
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SAXException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return list;
    }
    /**
     * Initialization of List<JewelType> from xml file.
     * Uses StAX parser.
     * @param xmlPath
     * @return list of JewelType
     */
    public static List<JewelType> initListStAX(String xmlPath) {
	
	    return Collections.<JewelType>emptyList();
    }
    
    public static List<JewelType> sortList(List<JewelType> list) {
	List<JewelType> l = new ArrayList<>(list);
	l.sort((c1, c2) -> c1.compareTo(c2));
	return l;
    }
}
