import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * A shortcut class to read and write binary files.
 *
 * @author bishnoi
 */
public class ItchStreamer {

    private DataInputStream in;

    public ItchStreamer(String pathname) {
        this(new File(pathname));
    }

    /**
     * Creates a new instance of ItchStreamer to read the file for the given file.
     */
    public ItchStreamer(File file) {
        try {
            this.in = new DataInputStream(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a new instance of AsciiReader from an InputStream.
     */
    public ItchStreamer(InputStream source) {
        this.in = new DataInputStream(source);
    }

    public boolean hasNext() {
        return !this.eof();
    }

    public boolean eof() {
        try {
            return (this.in.available() < 1);
        } catch (IOException ex) {
            ex.printStackTrace();
            return true;
        }
    }

    public String next() {
        try {
            return this.in.readUTF();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String nextWord() {
        return this.next();
    }

    public String nextChar() {
        try {
            return String.valueOf(this.in.readChar());
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String nextChatNoWhitespace() {
        return this.nextChar();
    }

    public double nextDouble() {
        try {
            return this.in.readDouble();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Double.NaN;
        }
    }

    public float nextFloat() {
        try {
            return this.in.readFloat();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Float.NaN;
        }
    }

    public int nextInt() {
        try {
            return this.in.readInt();
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public long nextLong() {
        try {
            return this.in.readLong();
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0l;
        }
    }

    public short nextShort() {
        try {
            return this.in.readShort();
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public byte nextByte() {
        try {
            return this.in.readByte();
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean nextBoolean() {
        try {
            return this.in.readBoolean();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            this.in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


/*public class ItchStreamer {
	public static void main(String[] args) throws IOException {
		final String dataFile = "/home/mikedes/Downloads/12302015.NASDAQ_ITCH50.gz";
		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
		byte[] buffer = new byte[1024];
		int total = 0;
		int nRead = 0;
		while((nRead = in.read(buffer)) != -1){
			try {
				int offset = 0;
				for(int i =0; i < 8;i++){
					char c = buffer.toString().charAt(i);		
					System.out.println(c);
					offset++;
				}
			in.close();
			System.out.println("Read " + total + " bytes.");
			}catch(FileNotFoundException ex) {
				System.out.println(
						"Unable to open file '" + 
								dataFile + "'");                
			}catch(IOException ex) {
				System.out.println(
            "Error reading file '" 
            + dataFile + "'");                  
	}
		}
	}
	}

*/