package com.homework.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import gem_xsd.JewelType;

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
	List<JewelType> list = new ArrayList<>();
	
	return list;
    }
    /**
     * Initialization of List<JewelType> from xml file.
     * Uses DOM parser.
     * @param xmlPath
     * @return list of JewelType
     */
    public static List<JewelType> initListDOM(String xmlPath) {
	List<JewelType> list = new ArrayList<>();
	
	return list;
    }
    /**
     * Initialization of List<JewelType> from xml file.
     * Uses StAX parser.
     * @param xmlPath
     * @return list of JewelType
     */
    public static List<JewelType> initListStAX(String xmlPath) {
	List<JewelType> list = new ArrayList<>();
	
	return list;
    }
    
    public static List<JewelType> sortList(List<JewelType> list) {
	List<JewelType> l = new ArrayList<>(list);
	l.sort((c1, c2) -> c1.compareTo(c2));
	return l;
    }
}
