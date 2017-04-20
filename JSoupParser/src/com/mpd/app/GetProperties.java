package com.mpd.app;

import java.util.Properties;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GetProperties {
	Properties prop = new Properties();
	String stock = null;
	String url = null;
	String logfile = null;
	String classname = null;
	String mysqldriver = null;
	String mysqluser = null;
	String mysqlpasswd = null;
	String myjdbccxn = null;
	String multivaluelist = null;
	
	{
		try{
			prop.load(new FileInputStream("bidask.config"));
		}catch(IOException e){
			System.out.println(e);
			System.out.println("Unable to continue.");
		}
		
		for(String key : prop.stringPropertyNames()) {
			String value = prop.getProperty(key);
			if(key.matches("stock") && value != null) {
				stock = value;
			}else if(key.matches("url") && value != null) {
				url = value;
			}else if(key.matches("logfile") && value != null){
				logfile=value;
			}else if(key.matches("classname") && value != null){
				classname=value;
			}else if(key.matches("mysqldriver") && value != null){
				mysqldriver=value;
			}else if(key.matches("mysqluser") && value != null){
				mysqluser=value;
			}else if(key.matches("mysqlpasswd") && value != null){
				mysqlpasswd=value;
			}else if(key.matches("myjdbccxn") && value != null){
				myjdbccxn=value;
			} else
				try {
					if(key.matches("stocklist") && value != null){
						multivaluelist=value;
						try {
							FileReader fileReader = new FileReader(new File(multivaluelist));
							BufferedReader br = new BufferedReader(fileReader);
							String line = null;
							while((line = br.readLine()) != null){
								
							}
									
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						System.out.println("Unknown key/value pair. Skipping.");
						System.out.println(key + " " + value);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public String selectStock() {
		return stock;
	}
	public String selectUrl(){
		return url;
	}
	public String selectLogFile(){
		return logfile;
	}
	public String selectClassName(){
		return classname;
	}
	public String selectMysqlDriver(){
		return mysqldriver;
	}
	public String selectMysqlUser(){
		return mysqluser;
	}
	public String selectMysqlPasswd(){
		return mysqlpasswd;
	}
	public String selectJdbcDriver() {
		return myjdbccxn;
	}
	public String selectMultiValueList(){
		return multivaluelist;
	}
	
}
