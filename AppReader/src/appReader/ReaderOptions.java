package appReader;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class ReaderOptions extends JFrame implements ActionListener{
	JButton modData,modPsw,Delete;
	private String userID;
	JLabel lmoPsw,lmnPsw,lmnPsw2;
	JPasswordField tmoPsw,tmnPsw,tmnPsw2;
	JLabel blank1;
	Socket socket;
	JPanel p1,p2,p3;
	
	InputStream inp = null;
    BufferedReader in = null;
    PrintWriter out = null;
    
	public ReaderOptions(Socket socket, String userID) {
		this.userID=userID;
		this.socket=socket;
		
		setSize(250, 150);
		setLocation(450, 160);
		setTitle("Options");
		setResizable(false);
		
		try{
			this.socket=socket;
			
			inp = socket.getInputStream();
		    in = new BufferedReader(new InputStreamReader(inp));
		    out = new PrintWriter(socket.getOutputStream());
		}catch(Exception z){}	
		
		JTabbedPane tabbed=new JTabbedPane();
		p1=new JPanel(new GridLayout(4,2));
		p2=new JPanel(new GridLayout(3,1));
		p3=new JPanel(new GridLayout(3,1));
		tabbed.addTab("Password",null,p1,"Modifica Password");
		tabbed.addTab("Data",null,p2,"Modifica Dati");
		tabbed.addTab("User",null,p3,"Cancella Utente");
		add(tabbed);
		//mod psw Panel
		lmoPsw=new JLabel("old psw:");
		lmnPsw=new JLabel("new psw:");
		lmnPsw2=new JLabel("new psw:");
		blank1=new JLabel();
		tmoPsw=new JPasswordField();
		tmnPsw=new JPasswordField();
		tmnPsw2=new JPasswordField();
		modPsw=new JButton("Modifica Password");
		modPsw.addActionListener(this);
		
		p1.add(lmoPsw);
		p1.add(tmoPsw);
		p1.add(lmnPsw);
		p1.add(tmnPsw);
		p1.add(lmnPsw2);
		p1.add(tmnPsw2);
		p1.add(blank1);
		p1.add(modPsw);
		
		modData=new JButton("Modifica Dati");
		modData.addActionListener(this);
		Delete=new JButton("Cancella Profilo");
		Delete.addActionListener(this);
		
		
		
	
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
	}
	
	

	
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==modData){
			
		}
		if(a.getSource()==modPsw){
			String tmp;
			out.println("GETPSW");
			out.flush();
			out.println(userID);
			out.flush();
			try {
				tmp=in.readLine();
				if(tmp.equals(tmoPsw.getText())){
					
					if(tmnPsw.getText().equals(tmnPsw2.getText())){
						out.println("SETPSW");
						out.flush();
						out.println(userID);
						out.flush();
						out.println(tmnPsw.getText());
						out.flush();
						tmoPsw.setText("");
						tmnPsw.setText("");
						tmnPsw2.setText("");
					}else{
						JOptionPane.showMessageDialog(this,"Le nuove Password non Combaciano","Inane warning",JOptionPane.WARNING_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(this,"Old Password Non Corretta","Inane warning",JOptionPane.WARNING_MESSAGE);
					tmoPsw.setText("");
					tmnPsw.setText("");
					tmnPsw2.setText("");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(a.getSource()==Delete){
			
		}
		
	}
}
