package com.app.feeds;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestFeed {
	public static void main(String[] args) throws InterruptedException {
		int numTests = 200000;
		int portNumber = 3679;
		try {
			System.out.println("Sender start");
			ServerSocketChannel ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(true);
			ssChannel.socket().bind(new InetSocketAddress(portNumber));
			
			for (int i = 0; i < numTests; i++) {
				SocketChannel sChannel = ssChannel.accept();
				ObjectOutputStream oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
				int shares = ThreadLocalRandom.current().nextInt(100, 999);
				double price = truncatePrice(ThreadLocalRandom.current().nextDouble(1.99, 19.99));
				String stock = "TEST";
				FeedMessage stock_m = new FeedMessage(stock, shares, price);
				oos.writeObject(stock_m);
				//oos.close();
				//System.out.println("printed");
				Thread.sleep(50);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static double truncatePrice(Double pr) {
		
		return BigDecimal.valueOf(pr).setScale(3, RoundingMode.HALF_UP).doubleValue();
		
	}
}
