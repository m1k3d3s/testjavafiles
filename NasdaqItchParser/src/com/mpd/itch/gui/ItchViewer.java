package com.mpd.itch.gui;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Base64;



public class ItchViewer {
	static GetProperties gp = new GetProperties();
	static String itchstock;
	static String itchport;
	static String itchserver;
	public static void main(String[] args){
		itchport = gp.getItchPort();
		itchserver = gp.getItchServer();
		itchstock = gp.getItchStock();
		Thread t = new Thread(new Reader());
		t.start();
	}		
}
class Reader implements Runnable
{

	static Socket socket;
	static InputStream in;
	static Object s;
	public void run()
	{       	
		try {
			//socket = new Socket(itchserver,Integer.parseInt(itchport));
			socket = new Socket("localhost",35648);
			//in = new ObjectInputStream(socket.getInputStream());
			in = socket.getInputStream();
			System.out.println("blah");
			//printwriter = new PrintWriter(socket.getOutputStream(),true);
		} catch (UnknownHostException e1) {
			//e1.printStackTrace();
			//log.log(Level.WARNING, "Unable to connect to host:port");
			System.out.println("Unable to connect to host:port");
		} catch (IOException e1) {
			//e1.printStackTrace();
			//log.log(Level.WARNING, "Unable to read data");
			System.out.println("Unable to read data - at connection");
		}
		while(true)
		{
			try {
				System.out.println("test message");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] content = new byte[ 2048 ];
				int bytesRead = -1;
				while((bytesRead = in.read(content)) != -1){
					//System.out.println(bytesRead);
					if(bytesRead>0){
						byte[] message = new byte[bytesRead];
						//int blah = in.read(message, 0, message.length);
						System.out.print(message+"\n");
						//for(int i = 0; i < bytesRead; i++){
						//	System.out.println(message);
						//}
					}
					
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				//log.log(Level.WARNING, "Unable to read data");
				System.out.println("Unable to read data - at reading");
			} finally {
				try {
					socket.close();
					System.out.println("Closed socket");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}



