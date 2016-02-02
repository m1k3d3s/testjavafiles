import java.util.ArrayList;

public class Array {

public static void main(String [] args)
    {
        ArrayList <Integer> myList = new ArrayList<Integer>();

        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        System.out.println("Ascending order");
        for (int i=0;i<myList.size();++i)
        {
            System.out.println(myList.get(i));
        }
    }

}
