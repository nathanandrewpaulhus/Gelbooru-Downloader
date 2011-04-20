/* Screen Class
* Created by Nathan Paulhus, 2011
*
* This class is the JFrame of the application.
* It contains a JPanel called Board, which contains all
* the objects on the screen.
*/


import javax.swing.JFrame;

public class Screen extends JFrame{

	private static final long serialVersionUID = 5241834608422998589L;
	
	private Board panel;
	
	public Screen(){
		//Set all the standard options on JFrames
		setVisible(true);
		setResizable(false);
		setTitle("Gelbooru Downloader");
		setSize(500,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Add a new Board object, which is a JPanel
		panel = new Board();
		add(panel);
		
	}
	public Board getPanel(){
		return panel;
	}
	public void paintAgain(){
		repaint();
	}
	
}
