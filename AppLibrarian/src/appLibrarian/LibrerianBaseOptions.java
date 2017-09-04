package appLibrarian;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LibrerianBaseOptions extends JFrame implements ActionListener{
	JButton modData,modPsw,delete;
	Socket socket;
	String userID;
	
	public LibrerianBaseOptions(Socket socket, String userID) {

		this.userID=userID;
		this.socket=socket;
		
		modData=new JButton("Modifica Dati");
		modData.addActionListener(this);
		add(modData);
		modPsw=new JButton("Modifica Password");
		modPsw.addActionListener(this);
		add(modPsw);
		delete=new JButton("Cancella Profilo");
		delete.addActionListener(this);
		add(delete);
		
		setSize(250, 350);
		setLocation(450, 160);
		setTitle("Options");
		setResizable(false);
		setLayout(new GridLayout(5,1,10,10));
	
		
		setVisible(true);

	}
	
	

	
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==modData){
			
		}
		if(a.getSource()==modPsw){
			
		}
		if(a.getSource()==delete){
			
		}
		
	}
}
