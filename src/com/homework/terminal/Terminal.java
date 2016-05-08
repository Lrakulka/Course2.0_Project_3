package com.homework.terminal;

import com.homework.analyzer.Util;

public class Terminal {

    public static void main(String[] arg) {
	String args[] = {"./src/gem.xsd", "./src/gem.xml"};
	boolean isValid = Util.validateXMLSchema(args[0],args[1]);
	if (isValid) {
	   System.out.println(args[1] + " is valid against " + args[0]);
	} else {
	   System.out.println(args[1] + " is not valid against " + args[0]);
	}	
    }
}