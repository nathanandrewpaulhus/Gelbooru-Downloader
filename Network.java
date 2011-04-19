/* Network Object
 * (c) Nathan Paulhus, 2011
 *
 * This class is instantiated with a set of tags, which the class reads and turns into urls.
 * After which, it attempts to download all the images on the page.
 * When it runs out of images, it will attempt to go to the next page.
 * If that contains no images, then it ends running thread and stops the object.
 */


import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Network implements Runnable{

	private String t;
	private Thread tr;
	private boolean c = true;
	
	public Network(String ta){
		//Create a new thread that runs inside this class
		tr = new Thread(this);
		tr.start();
		//Set this classes tags value equal to the entered tags
		this.t = ta;
	}
	@SuppressWarnings("deprecation")
	public void run() {
			
	       Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		   
	       while(true){//Always
	    	   	//Loop to 20k times, the max number of images Gelbooru will allow
				for (int x = 0;x<20000;x+=25){
					//If continue is no longer true, then stop the thread
		    	   	if (!c){
		    	   		tr.stop();
		    	   	}else{
						try {
							//Start the main downloading process
							initalizeProcess(t,x);
						} catch (IOException e) {
							Primary.getScreen().getPanel().appendToConsole("No more images remaining\n");
							tr.stop();
						}
		    	   	}
				}
	           try{
	               Thread.sleep(50);
	           }catch(InterruptedException ex){
	               //Do nothing
	           }
	       
	           Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		       
	       }
	}
	public void initalizeProcess(String tags,int num) throws IOException{
		//Creates a new empty url
		URL url = null;
		//Assigns that url to a given url
		try {
			//Create the new url giving it both the tags from the users entry and the number from the loop above
			url = new URL("http://gelbooru.com/index.php?page=post&s=list&tags="+tags+"&pid="+num);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//Creates an arraylist of the contents of the page
		ArrayList<String> info = getImageInfo(url);
		if (info.size() != 0){
			Primary.getScreen().getPanel().appendToConsole(url.toString()+"\n");
			//Get the lines that contain image info
			for (int i = 0;i<info.size();i++){
				info.set(i, getPID(info.get(i)));
			}
			//Get the url of the images
			for (int i = 0;i<info.size();i++){
				info.set(i,getImageUrl(info.get(i)));
			}
			//Download images from those urls
			for (int i = 0;i<info.size();i++){
				downloadImage(info.get(i),i);
			}
			//Confirm completion
			if (info.size() == 25){
				Primary.getScreen().getPanel().appendToConsole("Moving To Next Page\n");
			}else{
			}
		}else{
			this.c = false;
			Primary.getScreen().getPanel().appendToConsole("No more images\n");
		}
	}
	public static void downloadImage(String s,int i) throws IOException{
		//Get the name of the image as its index tag
		String name = s.substring(s.indexOf("images/") + 11,s.lastIndexOf("."));
		//Get the image format (ie, jpg or png)
		String format = s.substring(s.lastIndexOf(".",s.length()));
		//Initialize the URL object
		URL url= null;
		try {
			//Set the url object equal to the provided url in the arguements
			url = new URL(s);
		} catch (MalformedURLException e) {
			Primary.getScreen().getPanel().appendToConsole(e.toString()+"\n");
		}
		//Create the GelbooruImages folder if it does not currently exist
		File folder = new File("GelbooruImages");
		if (!folder.exists()){
			folder.mkdir();
		}
		//Initalize the image to be rendered
		RenderedImage bi = null;

		try{
			//Read the image into the RenderedImage object
			bi = ImageIO.read(url);
			//Write the RenderedImage to a file
			File f = new File(folder+"/"+name+format);
			ImageIO.write(bi,format.replace(".",""),f);
			//Announce on console that an image has been downloaded
			Primary.getScreen().getPanel().appendToConsole("Image "+i+" Downloaded\n");
		}catch(IllegalArgumentException e){
			Primary.getScreen().getPanel().appendToConsole("Error Downloading Image\n");
		}
	}
	public static ArrayList<String> getImageInfo(URL url) throws IOException{
		//Initalize the arraylist of strings and the buffered reader for reading the url
		BufferedReader in = null;
		ArrayList<String> info = new ArrayList<String>();
		
		try{
			//Set the bufferedReader equal to the html stream of the url provided in the arguements
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			int x = 0;
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				if (inputLine.indexOf("class=\"thumb\"") > 0){
					info.add(x,inputLine);
					x++;
					//Read all the lines of html, and add the lines that contain the image references to the arraylist of objects
				}
			}
			in.close();
			
			
		}catch(UnknownHostException e){
			Primary.getScreen().getPanel().appendToConsole("Invalid URL\n");
		}
		
		return info;
	}
	public static String getPID(String s){
		//The procedure to get the pictures id, simply referencing the location of the src tag start and end.
		int i = s.indexOf("<img src=\"");	
		int j = s.indexOf("\" alt=\"");
		String st = (s.substring(i+10,j));

		return st;
	}
	public static String getImageUrl(String s){
		//Method to create the url
		//Get the location of the necessary parts of the tags
		int chars_start = s.indexOf("umbs/") + 5;
		int chars_end = s.indexOf("/thumbnail");
		String chars = s.substring(chars_start,chars_end);
		
		int id_start = s.indexOf("nail_") + 5;
		int id_end = s.indexOf("?");
		String id = s.substring(id_start,id_end);
		//Concatenate these parts into the url.
		String url = ("http://img4.gelbooru.com//images/"+chars+"/"+id);		
		return url;
		
	}

}
