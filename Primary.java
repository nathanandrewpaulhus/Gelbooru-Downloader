import java.io.IOException;


public class Primary {
	
	public static Screen screen = new Screen();
	
	public static void main(String[] args) throws IOException{	
		new Updater();
		screen.getPanel().appendToConsole("Enter the desired search tags in the text area above seperated by +'s.\nImages will be saved to a subfolder in this directory called GelbooruImages.\n");
	}
	public static Screen getScreen(){
		return screen;
	}
}
