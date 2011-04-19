import javax.swing.JFrame;

public class Screen extends JFrame{

	private static final long serialVersionUID = 5241834608422998589L;
	
	private Board panel;
	
	public Screen(){
		
		setVisible(true);
		setResizable(false);
		setTitle("Gelbooru Downloader");
		setSize(500,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
