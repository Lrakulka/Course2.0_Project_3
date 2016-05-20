package com.homework.terminal;

import com.homework.analyzer.Util;

public class Terminal {

    public static void main(String[] args) {
	String pathes[] = {"./src/gem.xsd", "./src/gem.xml", "./src/gem.xsl",
		"./src/gem.html", "./src/gem_.xml"};
	boolean isValid = Util.validateXMLSchema(pathes[0],pathes[1]);
	if (isValid) {
	   System.out.println(pathes[1] + " is valid against " + pathes[0]);
	} else {
	   System.out.println(pathes[1] + " is not valid against " + pathes[0]);
	   return;
	}	
	System.out.println("DOM parser");
	Util.sortList(Util.initListDOM(pathes[1])).stream().forEach(System.out::println);
	System.out.println("SAX parser");
	Util.sortList(Util.initListSAX(pathes[1])).stream().forEach(System.out::println);
	System.out.println("StAX parser");
	Util.sortList(Util.initListStAX(pathes[1])).stream().forEach(System.out::println);
	
	Util.convrtXMLtoHTML(pathes[4], pathes[2], pathes[3]);
    }
}