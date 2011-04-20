/* Primary Class
*	Created by Nathan Paulhus, 2011
*
* This is the launch class for the program.
* On initialization it creates and displays the UI
*/
import java.io.IOException;


public class Primary {
	//Create a new screen object, which is a JFrame
	public static Screen screen = new Screen();
	
	public static void main(String[] args) throws IOException{	
		//Create a new updates object, which repaints the console in the gui
		new Updater();
		screen.getPanel().appendToConsole("Enter the desired search tags in the text area above seperated by +'s.\nImages will be saved to a subfolder in this directory called GelbooruImages.\n");
	}
	public static Screen getScreen(){
		return screen;
	}
}
