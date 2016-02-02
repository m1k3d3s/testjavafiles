import java.util.Random;

public class InterruptableProcess {
    public static final int TIMEOUT = 1000;

    public static void main(String[] args){
        for(int i=0; i<10; i++){
            getAnswer();
        }
    }

    public static double getAnswer(){
        long b4 = System.currentTimeMillis();
        // have an estimate pre-computed
        double estimate = Math.random();

        //try to get accurate answer
        //can take a long time
        //if longer than TIMEOUT, use estimate instead
        AccurateAnswerThread t = new AccurateAnswerThread();
        t.start();

        try{
            t.join(TIMEOUT);
        } catch(InterruptedException ie){
            ;
        }

        if(!t.isFinished()){
            System.err.println("Returning estimate: "+estimate+" in "+(System.currentTimeMillis()-b4)+" ms");
            return estimate;
        } else{
            System.err.println("Returning accurate answer: "+t.getAccurateAnswer()+" in "+(System.currentTimeMillis()-b4)+" ms");
            return t.getAccurateAnswer();
        }

    }

    public static class AccurateAnswerThread extends Thread{
        private boolean finished = false;
        private double answer = -1;

        public void run(){
            //call to external, non-modifiable code
            answer = accurateAnswer();
            finished = true;
        }

        public boolean isFinished(){
            return finished;
        }

        public double getAccurateAnswer(){
            return answer;
        }

        // not modifiable, emulate an expensive call
        // in practice, from an external library
        private double accurateAnswer(){
            Random r = new Random();
            long b4 = System.currentTimeMillis();
            long wait = r.nextInt(TIMEOUT*2);

            //don't want to use .wait() since
            //external code doesn't support interruption
            while(b4+wait>System.currentTimeMillis()){
                ;
            }
            return Math.random();
        }
    }
}
