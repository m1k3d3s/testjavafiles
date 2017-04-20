package com.mpd.itch.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;

import com.mpd.itch.app.ItchParsers;


public class ItchParse {
	private String yamlFile = "itch5.yaml";
	private boolean parse2print;
	private InputStream itchinput;
	ItchParsers parsers;
	ItchParseDS parsedatastructure;
	private String filename;
	
	public ItchParse(String filename, boolean parse2print){
		this.parse2print = parse2print;
		try{
			parsedatastructure = new ItchParseDS(yamlFile);
		}catch(FileNotFoundException e){
			System.out.println("Yaml File not found ... Check filename / path");
		}
		parsers = new ItchParsers(parsedatastructure);
		this.filename = filename;
		try {
			itchinput = new FileInputStream(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found ... Check filename / path");
		}
	}
	
	//public ArrayList<String> parse() throws IOException, InterruptedException {
	public byte[] parse() throws IOException, InterruptedException {
		if(itchinput.read() == -1) {// EOF
			return null;
		}
		int payLength = itchinput.read();
		byte[] payBytes = new byte[payLength]; // Get the payload
		ArrayList<String> itchmessageArr = null;
		itchinput.read(payBytes);
		if(parse2print) {
			//ArrayList<String> itchmessageArr = null;
			itchmessageArr = (parsers.messageIn(payBytes));
			//System.out.println(itchmessageArr);
		}
		
		return payBytes;
		//return itchmessageArr;
		
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		int portNumber = 35648;
		
		String itchfile = "/home/mikedes/Downloads/12302015.NASDAQ_ITCH50";
		ItchParse ip = new ItchParse(itchfile,true);
		while (ip.parse() != null) {
			try (
				ServerSocket itchserver = new ServerSocket(portNumber);
				Socket clientSocket = itchserver.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			 ){
	            //String inputLine;
	            while (ip.parse()  != null) {
	                out.println(ip.parse());
	                //out.flush();
	            }
			} catch(IOException e) {
				System.out.println("Exception caught when trying to listen on port "
		                + portNumber + " or listening for a connection");
		            System.out.println(e.getMessage());
			}
		}	
	}
}
