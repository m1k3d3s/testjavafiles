package com.mpd.app;

import java.io.File;
import java.io.IOException;

public class JaxConverter {
	

	public static void main(String[] args) throws IOException{
        File csvfile = new File("/home/mikedes/historicaldata/csvfiles/sbux_table.csv");
        XMLCreators xmlc = new XMLCreators();
        //xmlc.convertFile("/home/mikedes/historicaldata/csvfiles/sbux_table.csv", "/home/mikedes/testfile_sbux.xml", ",");
        xmlc.convertFile(csvfile, "/home/mikedes/testfile_sbux.xml", ",");
	}
}