import java.io.*;

class callPyfrJava {
    public static void main(String[] args) {
        System.out.println("Test");
        try {
            Process p = Runtime.getRuntime().exec("python //home/mikedes/localcheckout/svnroot/pythoncode/orderbook.py");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            p.waitFor();
            String line = "";
            while (br.ready())
                System.out.println(br.readLine());
            } catch (Exception e) {
                String cause = e.getMessage();
                if (cause.equals("python: not found"))
                System.out.println("No python interpreter found.");
            }
    }
}
