import java.util.HashMap;



class Digits {

    private HashMap<String, String> digithash = null;

    public static String concatenateDigits(int... digits) {
        StringBuilder sb = new StringBuilder(digits.length);       
        for (int digit : digits) {
            sb.append(digit);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for(int i = 1; i <= 100000; i++) {
            String string = concatenateDigits(i);
            string += concatenateDigits(i);
            System.out.println(string);
            digithash.put(string , string);
            int sizeofhash = digithash.size();
        }
        System.out.println(sizeofhash);
    }
}
