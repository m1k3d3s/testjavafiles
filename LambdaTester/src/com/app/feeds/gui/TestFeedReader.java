package com.app.feeds.gui;
import com.app.feeds.FeedMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class TestFeedReader {
	private boolean isConnected = false;
	
	public TestFeedReader() {
		
	}
	
	public void communicate() {
		while(!isConnected) {
			try {
				SocketChannel schannel = SocketChannel.open();
				schannel.configureBlocking(true);
				System.out.println("Connected");
				isConnected = true;
				while(isConnected) {
					if(schannel.connect(new InetSocketAddress("localhost", 3679))) {
						ObjectInputStream ois = new ObjectInputStream(schannel.socket().getInputStream());
						FeedMessage fm = (FeedMessage)ois.readObject();
						System.out.println("Object recieved = " + fm);
						//socket.close();
					}
				}
				System.out.println("End of transmission");
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException cn) {
				cn.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		TestFeedReader tfr = new TestFeedReader();
		tfr.communicate();
	}
}
