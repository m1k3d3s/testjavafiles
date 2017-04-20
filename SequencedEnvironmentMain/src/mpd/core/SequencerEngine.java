package mpd.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SequencerEngine {

	private static final Logger LOGGER = Logger.getLogger( SequencerEngine.class.getName() );
	
	ServerSocket myServerSocket;
	boolean ServerOn = true;
	
	public SequencerEngine(){
		try{
			myServerSocket = new ServerSocket(2910);
			
		} catch(IOException ioe){
			LOGGER.log(Level.SEVERE,"Sequencer could not start listener port. Please check.");
		}
	}
	
	public static void main(String[] args){
		new SequencerEngine();
	}

}
