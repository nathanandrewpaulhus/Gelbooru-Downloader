import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 2195379760914381351L;
	
	private JTextArea console = new JTextArea();
	private JTextField tags = new JTextField();
	private JScrollPane scrollPane = new JScrollPane(console);
	
	public Board(){
		
		setLayout(new BorderLayout());
		
		add(tags,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		
		
		tags.setPreferredSize(new Dimension(400,25));
		
		tags.addActionListener(this);		
	}
	public void actionPerformed(ActionEvent e) {
		console.append("Initializing Downloads\n");
		
		String input = tags.getText();
		
		new Network(input);
		
	}
	public void appendToConsole(String s){
		console.append(s);
	}
	
	

}
