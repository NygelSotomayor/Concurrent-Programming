import java.util.Random;
public class Exercise {
    public static class Philosopher implements Runnable{
        private Object leftChopStick;
        private Object rightChopStick;
        private int hunger = 5;
        public Philosopher(Object leftChopStick,Object rightChopStick){
            this.leftChopStick = leftChopStick;
            this.rightChopStick= rightChopStick;
        }
        public void doAction(String action) throws InterruptedException{
            System.out.println(Thread.currentThread().getName()+" "+action);
            Thread.sleep((int)(Math.random()*100));
        }
        @Override
        public void run() {
            try{
                while(hunger>0){
                    doAction("Hunger remaining: "+this.hunger+"  Thinking" );
                    synchronized (leftChopStick){
                        doAction("Hunger remaining: "+this.hunger+ "  Picked up left chopstick");
                        synchronized (rightChopStick){
                            doAction("Hunger remaining: "+this.hunger+ "  Picked up right chopstick and started eating");
                            doAction("Hunger remaining: "+this.hunger+ "  Put down right chopstick");
                            this.hunger-=1;
                        }
                        doAction("Hunger remaining: "+this.hunger+ " Put down left chopstick and start thinking");
                    }
                }
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
    //Our Main Class
    public static void main(String[] args){
        //Instantiate our Random Variable n (bounded to 100 for now for testing purposes)
        Random random = new Random();
        int n = (int)(random.nextInt(100));
        //Our Philosopher array of size n
        Philosopher philosopher[] = new Philosopher[n];
        //Our Chopsticks array of size n
        Object chopsticks[] = new Object[n];
        //Initialize our chopstick objects
        for(int i=0;i<philosopher.length;i++){
            chopsticks[i] = new Object();
        }
        for(int i=0;i<philosopher.length;i++){
            Object leftChopStick = chopsticks[i];
            Object rightChopStick = chopsticks[(i+1)%n];
            //use this if condition to avoid dead lock
            if(i == philosopher.length-1){
                philosopher[i] = new Philosopher(leftChopStick,rightChopStick);
            }else{
                philosopher[i] = new Philosopher(rightChopStick,leftChopStick);
            }
            Thread t = new Thread(philosopher[i] ," Philosopher "+(i+1));
            t.start();

        }
    }
}
