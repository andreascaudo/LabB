package appLibrarian;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReaderOptions extends JFrame implements ActionListener{
	JButton modData,modPsw,delete,addBook,delateBook,registerUser;
	
	public ReaderOptions() {
		
		
		modData=new JButton("Modifica Dati");
		modData.addActionListener(this);
		add(modData);
		modPsw=new JButton("Modifica Password");
		modPsw.addActionListener(this);
		add(modPsw);
		delete=new JButton("Cancella Profilo");
		delete.addActionListener(this);
		add(delete);
		addBook=new JButton("Aggiungi libro");
		addBook.addActionListener(this);
		add(addBook);
		delateBook=new JButton("Elimina libro");
		delateBook.addActionListener(this);
		add(delateBook);
		registerUser = new JButton("Registra utente");
		registerUser.addActionListener(this);
		add(delateBook);
		
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
