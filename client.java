import java.lang.*;
import java.io.*;
import java.net.*;

class Client {
   public static void main(String args[]) {
      File file = new File("users.txt");
      String path = file.getPath();
      System.out.println(path);

      try {
         Socket skt = new Socket("localhost", 1234);
         BufferedReader in = new BufferedReader(new
            InputStreamReader(skt.getInputStream()));
         PrintWriter printwriter = new PrintWriter(skt.getOutputStream(),true);
         System.out.print("Received string: '");
         printwriter.print("buy msft 100 .350");
            
         while (!in.ready()) {}
         System.out.println(in.readLine()); // Read one line and output it

         System.out.print("'\n");
         in.close();
      }
      catch(Exception e) {
         System.out.print("Whoops! It didn't work!\n");
      }
   }
}

