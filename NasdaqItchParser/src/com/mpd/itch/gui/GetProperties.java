package com.mpd.itch.gui;

import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;

public class GetProperties {
	Properties prop = new Properties();
	String itchPort;
	String itchServer = null;
	String stock = null;
	
	{ 
		try {
			prop.load(new FileInputStream("gui.config"));
		}catch(IOException e){
			System.out.println("Gui config file missing-unable to continue");
		}
		
		for (String key : prop.stringPropertyNames()){
			String value = prop.getProperty(key);
			if(key.matches("itchPort") && value != null){
				itchPort = String.valueOf(itchPort);
			}else if(key.matches("itchServer") && value != null){
				itchServer = value;
			}else if(key.matches("stock") && value != null){
				stock = value;
			}
		}
	}

	public String getItchPort() {
		return itchPort;
	}

	public String getItchServer() {
		return itchServer;
	}

	public String getItchStock() {
		return stock;
	}

	
}
