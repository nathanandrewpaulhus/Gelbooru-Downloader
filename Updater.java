/* Updater Class
* Created by Nathan Paulhus, 2011
*
* This class is used to repaint the console to ensure the messages
* written to it are visible while the images download
*/

public class Updater implements Runnable {
	
	Screen screen;
	
	public Updater(){
		//Create the updater thread
		Thread tr =new Thread(this);
		tr.start();
	}
	public void run() {
	   
       Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	   
       while(true){
    	   //Repaint the screen every time it runs
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
