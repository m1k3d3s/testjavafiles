package testpackage;


public class Hello {
    public static void main (String [] args) {
        //System.out.println("Args: "+args[0]+ " "+args[1]);
        String firstArg = args[0];
        String secondArg = args[1];
        System.out.println("Args "+firstArg+" "+secondArg);
        Date date = new Date();
        date.setHours(date.getHours() + 8);
        System.out.println(date);
        SimpleDateFormat simpDate;
        simpDate = new SimpleDateFormat("kk:mm:ss");
        System.out.println(simpDate.format(date));
        
        File file = new File("users.txt");
        String path = file.getAbsolutePath();
        System.out.println(path);

        Runtime rt = Runtime.getRuntime();
        System.out.println(rt);
    }
}
