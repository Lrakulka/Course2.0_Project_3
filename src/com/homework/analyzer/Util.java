package com.homework.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
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
    public static void convrtXMLtoHTML(String xmlPath, String xslPath, String htmlPath) {
	try {
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer =
	      tFactory.newTransformer
	         (new javax.xml.transform.stream.StreamSource
	            (xslPath));

	    transformer.transform
	      (new javax.xml.transform.stream.StreamSource
	            (xmlPath),
	       new javax.xml.transform.stream.StreamResult
	            ( new FileOutputStream(htmlPath)));
	}
	catch (Exception e) {
	    e.printStackTrace( );
	}
    }
    
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
            NodeList jewelList = doc.getElementsByTagName("g:jewel");
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
	List<JewelType> jewels = new ArrayList<>();
	JewelType jewel = null;
	VisualParameters visualParameters = null;
	byte elementPos = -1;
	try {
	     XMLInputFactory factory = XMLInputFactory.newInstance();
	     XMLEventReader eventReader =
	     factory.createXMLEventReader(new FileReader(xmlPath));
	     while(eventReader.hasNext()){
		 XMLEvent event = eventReader.nextEvent();
	         switch(event.getEventType()){
	             case XMLStreamConstants.START_ELEMENT:
	                 StartElement startElement = event.asStartElement();
	                 String qName = startElement.getName().getLocalPart();
	                 switch (qName) {
            	            case "jewel" : 
            	 	    	jewel = new JewelType();
            	 	    	jewel.setId(startElement.getAttributeByName(new 
    	                	     QName("id")).getValue());
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
            	 		visualParameters = new VisualParameters();
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
	              break;
	              case XMLStreamConstants.CHARACTERS:
	        	  Characters characters = event.asCharacters();
	        	  switch (elementPos) {
        	      	    case 1:
        	      		jewel.setName(characters.getData());
        	      		break;
        	      	    case 2:
        	      		jewel.setPreciousness(Boolean.valueOf(characters.getData()));
        	      		break;
        	      	    case 3:
        	      		jewel.setOrigin(characters.getData());
        	      		break;
        	      	    case 4:
        	      		visualParameters.setColor(characters.getData());
        	      		break;
        	      	    case 5:
        	      		visualParameters.setTransparency(Byte.valueOf(characters.getData()));
        	      		break;
        	      	    case 6:
        	      		visualParameters.setCut(Byte.valueOf(characters.getData()));
        	      		break;
        	      	    case 7:
        	      		jewel.setValue(Double.valueOf(characters.getData()));
        	      		break;
	        	  }
        	      	  elementPos = -1;
	              break;
	              case  XMLStreamConstants.END_ELEMENT:
	                     EndElement endElement = event.asEndElement();
	                     String name = endElement.getName().getLocalPart();
	                     if (name.equalsIgnoreCase("jewel")) {
	                	 jewels.add(jewel);
	                     } else    if (name.equalsIgnoreCase("Visual_Parameters")) {
	                	 jewel.setVisualParameters(visualParameters);
	                     }
	               break;
	         }	    
	     }
	} catch (FileNotFoundException e) {
	            e.printStackTrace();
	} catch (XMLStreamException e) {
	            e.printStackTrace();
	}
	return jewels;
    }
    
    public static List<JewelType> sortList(List<JewelType> list) {
	List<JewelType> l = new ArrayList<>(list);
	l.sort((c1, c2) -> c1.compareTo(c2));
	return l;
    }
}
