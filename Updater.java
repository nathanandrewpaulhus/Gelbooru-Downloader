
public class Updater implements Runnable {
	
	Screen screen;
	
	public Updater(){
		Thread tr =new Thread(this);
		tr.start();
	}
	public void run() {
	   
       Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	   
       while(true){
    	   
    	   screen = Primary.getScreen();
    	   screen.repaint();
           try{
               Thread.sleep(50);
           }catch(InterruptedException ex){
               //Do nothing
           }
       
           Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	       
       }
   }
	
}
