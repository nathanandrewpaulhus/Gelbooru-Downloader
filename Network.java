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
		tr = new Thread(this);
		tr.start();
		this.t = ta;
	}
	@SuppressWarnings("deprecation")
	public void run() {
		
	       Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		   
	       while(true){
	    	   	
				for (int x = 0;x<20000;x+=25){
		    	   	if (!c){
		    	   		tr.stop();
		    	   	}else{
						try {
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
		
		String name = s.substring(s.indexOf("images/") + 11,s.lastIndexOf("."));
		
		String format = s.substring(s.lastIndexOf(".",s.length()));
		
		URL url= null;
		try {
			url = new URL(s);
		} catch (MalformedURLException e) {
			Primary.getScreen().getPanel().appendToConsole(e.toString()+"\n");
		}
		
		File folder = new File("GelbooruImages");
		if (!folder.exists()){
			folder.mkdir();
		}
		
		RenderedImage bi = null;

		try{
			bi = ImageIO.read(url);
			
			File f = new File(folder+"/"+name+format);
			ImageIO.write(bi,format.replace(".",""),f);

			Primary.getScreen().getPanel().appendToConsole("Image "+i+" Downloaded\n");
		}catch(IllegalArgumentException e){
			Primary.getScreen().getPanel().appendToConsole("Error Downloading Image\n");
		}
	}
	public static ArrayList<String> getImageInfo(URL url) throws IOException{
		
		BufferedReader in = null;
		ArrayList<String> info = new ArrayList<String>();
		
		try{
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			int x = 0;
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				if (inputLine.indexOf("class=\"thumb\"") > 0){
					info.add(x,inputLine);
					x++;

				}
			}
			in.close();
			
			
		}catch(UnknownHostException e){
			Primary.getScreen().getPanel().appendToConsole("Invalid URL\n");
		}
		
		return info;
	}
	public static String getPID(String s){
		
		int i = s.indexOf("<img src=\"");	
		int j = s.indexOf("\" alt=\"");
		String st = (s.substring(i+10,j));
	
		return st;
	}
	public static String getImageUrl(String s){
		
		int chars_start = s.indexOf("umbs/") + 5;
		int chars_end = s.indexOf("/thumbnail");
		String chars = s.substring(chars_start,chars_end);
		
		int id_start = s.indexOf("nail_") + 5;
		int id_end = s.indexOf("?");
		String id = s.substring(id_start,id_end);
		
		String url = ("http://img4.gelbooru.com//images/"+chars+"/"+id);		
		return url;
		
	}

}
