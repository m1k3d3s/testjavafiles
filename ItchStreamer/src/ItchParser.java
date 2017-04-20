import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class ItchParser {
	public static void main(String[] args) throws IOException{
		RandomAccessFile aFile = new RandomAccessFile("/home/mikedes/Downloads/03302016.NASDAQ_ITCH50","rw");
		FileChannel inChannel = aFile.getChannel();
		
		byte[] read = new byte[2];
		ByteBuffer bb = ByteBuffer.wrap(read);
		
		int noOfBytesRead = inChannel.read(bb);
		
		long channelPos = inChannel.position();
		long channelSize = inChannel.size();
		
		//System.out.println(channelPos + " " + channelSize);
		bb.limit(2);
		
		bb.flip();
	
		System.out.print(bb.capacity()+"\n");
		System.out.print(bb.limit()+"\n");
		System.out.print(bb.mark()+"\n");
		
		short shortVal = bb.getShort(0);
		
		System.out.print(shortVal);
		
		byte[] payload = new byte[shortVal];
		System.out.println(payload.length);
		System.out.print(payload[1]);
		
		bb.clear();
		
	}
}