/* Board Class
* Created by Nathan Paulhus, 2011
* 
* This class initalizes the GUI used in the program with two objects,
* the textfield for the entered tags and the textarea, with a scrollpane,
* for the "console" output. This is used simply so that the user can 
* recieve updates on what the program is doing.
*/

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
		
		//Set the default layout to the BorderLayour
		setLayout(new BorderLayout());
		
		//Add the tags textfield and the console scroll pane to the main panel
		add(tags,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		
		//Set the preferred size of the tags object to 400x25
		tags.setPreferredSize(new Dimension(400,25));
		//Add the action listener to tags to listen for the enter key
		tags.addActionListener(this);		
	}
	public void actionPerformed(ActionEvent e) {
		//Announce the start message that the downloading is beginning
		console.append("Initializing Downloads\n");
		//Get the entered tags from the tags text box
		String input = tags.getText();
		//Create a new network object with the parameters equal to the tags
		new Network(input);
		
	}
	public void appendToConsole(String s){
		//A public method allowing other methods to append to the console
		console.append(s);
	}
	
	

}
