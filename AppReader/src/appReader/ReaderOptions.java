package appReader;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReaderOptions extends JFrame implements ActionListener{
	JButton modData,modPsw,Delete;
	
	public ReaderOptions() {
		
		
		modData=new JButton("Modifica Dati");
		modData.addActionListener(this);
		add(modData);
		modPsw=new JButton("Modifica Password");
		modPsw.addActionListener(this);
		add(modPsw);
		Delete=new JButton("Cancella Profilo");
		Delete.addActionListener(this);
		add(Delete);
		
		setSize(250, 150);
		setLocation(450, 160);
		setTitle("Options");
		setResizable(false);
		setLayout(new GridLayout(3,1,10,10));
	
		
		setVisible(true);
	}
	
	

	
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==modData){
			
		}
		if(a.getSource()==modPsw){
			
		}
		if(a.getSource()==Delete){
			
		}
		
	}
}
