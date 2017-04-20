package com.mpd.app;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.Arrays;


public class NasdaqBinaryRead{
	private static ByteOrder endianness = ByteOrder.BIG_ENDIAN;
	
	public static void main(String args[]) throws IOException {
		//read("/home/mikedes/Downloads/10292016.NASDAQ_ITCH50");
		scatterItchBytes();
		//readpartial();
	}
	
	private static void readpartial() throws IOException {
		// TODO Auto-generated method stub
		try {
			RandomAccessFile in = new RandomAccessFile("/home/mikedes/Downloads/10292016.NASDAQ_ITCH50","r");
			int payloadByteLength = in.readInt();
			byte type = in.readByte();
			int beginOfData = in.readInt();
			byte[] tempStore = null;
			in.read(tempStore, 0, 16);
			String id = new String(tempStore);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void read(String filename){
		try {
			FileInputStream fis = new FileInputStream(new File("/home/mikedes/Downloads/10292016.NASDAQ_ITCH50"));
			ByteBuffer bBuffer = ByteBuffer.allocateDirect(8);
			byte[] bb = new byte[8];
			int total = 0;
			int nRead = 2;
			
			
			while((nRead = fis.read(bb))!= -1){
				for (int i = 0;i<nRead; i++) {
					//get first 2 bytes
					byte[] filteredByteArray = Arrays.copyOfRange(bb, 2, bb.length -2 );
					String bin=Integer.toBinaryString(0xFF & filteredByteArray[i] | 0x100).substring(1);
					//System.out.println(ByteBuffer.wrap(filteredByteArray).getInt());
					System.out.print(bin);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Reading file complete.");
		
	}

	public static void scatterItchBytes() {

		try{
			ScatteringByteChannel scatterer = createChannelInstance("/home/mikedes/Downloads/10292016.NASDAQ_ITCH50",false);
			GatheringByteChannel gatherer = createChannelInstance("/tmp/output.txt",true);
			ByteBuffer payLoad = ByteBuffer.allocate(4);
			ByteBuffer message = ByteBuffer.allocate(1024);
			ByteBuffer[] paymessArray =  {payLoad,message};
			
			
			while(scatterer.read(paymessArray) != -1){
				payLoad.rewind();
				message.rewind();
				
				
				int p_length = payLoad.getInt();
                //System.out.println(p_length);
                //short p_lenshort = (short)(p_length - 7);
                //System.out.println("My short: " + p_lenshort);
                //message.allocate(p_lenshort);
                //String messageContent = message.asCharBuffer().toString();
                gatherer.write(new ByteBuffer[] {payLoad, message});
                System.out.print("\n");
                //System.out.print(messageContent+"\n");

                payLoad.flip();
				message.flip();
                //System.out.print(p_length+" ");
                //System.out.println(messageContent);
			}
		
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static int getUnsignedShort(ByteBuffer bb, int value) {
        return (bb.getShort(value) & 0xffff);
    }
	
	public static FileChannel createChannelInstance(String file, boolean isOutput) 
    {
        FileChannel fc = null;
        try
        {
            if (isOutput) {
                fc = new FileOutputStream(file).getChannel();
            } else {
                fc = new FileInputStream(file).getChannel();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return fc;
    }
	
}	
