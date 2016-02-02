import java.io.*;

public class Snake_Ladder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here

 int counter = 100,iteration = -1;

        while(counter > 0){
            if(counter % 10 == 0 && counter != 100){
                if(iteration == -1)
                {
                    counter-=9;
                    iteration=1;

                }
                else
                {
                    System.out.println(counter);
                    counter-=10;
                    iteration=-1;                   
                }
                if(counter != 0){
            System.out.println("\n");
                    System.out.print(counter + "\t");
            }
            counter+=iteration;                             
            }

            else{
                System.out.print(counter + "\t");
                counter += iteration;
            }
        }
    }
}
