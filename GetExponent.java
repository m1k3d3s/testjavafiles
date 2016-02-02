import java.lang.Math;

public class GetExponent {
    public static void main (String [] args) {
        float myfloat = 6.3f;
        double mydub = 6.58392;
        int b = Math.getExponent(myfloat);
        double md = Math.abs(mydub);
        double cos = Math.cos(mydub);
        double log = Math.log(mydub);
        System.out.println(b);
        System.out.println(md);
        System.out.println(cos);
        System.out.println(log);

    }
}
