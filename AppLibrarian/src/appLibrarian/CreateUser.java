package appLibrarian;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class CreateUser extends JFrame implements ActionListener{
	JButton loanBook,historyLoanBook,totalLoanBook,totalReservedBook,rankingBook,totalExpiredLoan,delateBook,registerUser,addBook;
	Socket socket;
	String userIDl;
	
	JLabel lblName, lblSurname, lblUserID, lblPhone, lblEmail, lblInq, lblInqSe;
	JTextField txtName, txtSurname, txtUserID, txtPhone, txtEmail;
	JComboBox jcInq, jcSez;
	
	JLabel lblTitleR,lblPsw;
	
	JButton signIn;

	InputStream inp = null;
    BufferedReader in = null;
    PrintWriter out = null;
    
	
	public CreateUser(Socket socket, String userID) {

		this.userIDl=userID;
		this.socket=socket;
		
		try {
			inp = socket.getInputStream();
		    in = new BufferedReader(new InputStreamReader(inp));
		    out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setSize(300, 440);
		setLocation(450, 160);
		setTitle("Create User");
		setResizable(false);
		setLayout(new GridLayout(6,1));
	    GridBagConstraints gbcOverReg = new GridBagConstraints();
		
		gbcOverReg.insets = new Insets(0,0,0,0);
        gbcOverReg.gridx = 0;
        gbcOverReg.gridy = 0;
        gbcOverReg.weighty = 2;
        lblTitleR = new JLabel("APP READER REGISTRATION", SwingConstants.CENTER);
        add(lblTitleR);
        
        JPanel panelFormReg1 = new JPanel();
        panelFormReg1.setLayout(new GridBagLayout()); 
        GridBagConstraints gbcReg = new GridBagConstraints();        
        
        JPanel panelFormReg2 = new JPanel();
        panelFormReg2.setLayout(new GridBagLayout());
        
        JPanel panelFormReg3 = new JPanel();
        panelFormReg3.setLayout(new GridBagLayout()); 
        
        JPanel panelFormReg4 = new JPanel();
        panelFormReg4.setLayout(new GridBagLayout()); 

        
        gbcReg.insets = new Insets(0,10,0,11);
        gbcReg.fill = GridBagConstraints.HORIZONTAL;
        gbcReg.gridx = 0;
        gbcReg.gridy = 0;
        gbcReg.weightx = 5;
        lblName = new JLabel("Nome");
        panelFormReg1.add(lblName,gbcReg);

        gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 0;
        gbcReg.weightx = 25;
        txtName = new JTextField("");
        panelFormReg1.add(txtName,gbcReg);

        gbcReg.insets = new Insets(0,10,0,11);
        gbcReg.gridx = 0;
        gbcReg.gridy = 1;
        gbcReg.weightx = 5;
        lblSurname = new JLabel("Cognome");
        panelFormReg1.add(lblSurname,gbcReg);

        gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 1;
        gbcReg.weightx = 25;
        txtSurname = new JTextField("");
        panelFormReg1.add(txtSurname,gbcReg);
        
        gbcReg.insets = new Insets(0,10,0,18);
        gbcReg.gridx = 0;
        gbcReg.gridy = 0;
        gbcReg.weightx = 0; 
        lblUserID = new JLabel("Codice fiscale");
        panelFormReg2.add(lblUserID,gbcReg);

        gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 0;
        gbcReg.weightx = 3;
        txtUserID = new JTextField("");
        panelFormReg2.add(txtUserID,gbcReg);
        
        gbcReg.insets = new Insets(0,10,0,18);
        gbcReg.gridx = 0;
        gbcReg.gridy = 1;
        gbcReg.weightx = 0;
        lblPhone = new JLabel("Telefono");
        panelFormReg2.add(lblPhone,gbcReg);

        gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 1;
        gbcReg.weightx = 3;
        txtPhone = new JTextField("");
        panelFormReg2.add(txtPhone,gbcReg);
        
        gbcReg.insets = new Insets(0,10,0,18);
        gbcReg.gridx = 0;
        gbcReg.gridy = 2;
        gbcReg.weightx = 0;
        lblEmail = new JLabel("Email");
        panelFormReg2.add(lblEmail,gbcReg);

        gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 2;
        gbcReg.weightx = 3;
        txtEmail = new JTextField("");
        panelFormReg2.add(txtEmail,gbcReg);
        

        lblPsw= new JLabel("GENERAZIONE AUTOMATICA DELLA PASSWORD", SwingConstants.CENTER);
        panelFormReg3.add(lblPsw);
        
        gbcReg.insets = new Insets(0,10,0,13);
        gbcReg.gridx = 0;
        gbcReg.gridy = 0;
        gbcReg.weightx = 0;
        lblInq = new JLabel("Inquadramento");
        panelFormReg4.add(lblInq,gbcReg);
        
    	String strInq[] = { "Studente", "Docente", "Tecnico", "Amministrativo" };
        
    	gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 0;
        gbcReg.weightx = 3;
		jcInq = new JComboBox<String>(strInq);
        panelFormReg4.add(jcInq,gbcReg);
        
        gbcReg.insets = new Insets(0,10,0,17);
        gbcReg.gridx = 0;
        gbcReg.gridy = 1;
        gbcReg.weightx = 0;
        lblInqSe = new JLabel("Sezione");
        panelFormReg4.add(lblInqSe,gbcReg);
        
    	String strSez[] = { "1A", "1B", "1C"};
        
    	gbcReg.insets = new Insets(0,0,0,10);
        gbcReg.gridx = 1;
        gbcReg.gridy = 1;
        gbcReg.weightx = 3;
        jcSez = new JComboBox<String>(strSez);
        panelFormReg4.add(jcSez,gbcReg);
        
        add(panelFormReg1);
        add(panelFormReg2);
        add(panelFormReg3);
        add(panelFormReg4);
        
        JPanel panelSignInBtn = new JPanel();
        panelSignInBtn.setLayout(new GridBagLayout());   
        gbcReg.insets = new Insets(0,20,0,20);
        gbcReg.gridwidth = GridBagConstraints.REMAINDER;
        gbcReg.fill = GridBagConstraints.HORIZONTAL;
        signIn = new JButton("Sign In");
        signIn.addActionListener(this);
        panelSignInBtn.add(signIn);
        
        add(panelSignInBtn);

		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == signIn){
			String temp;
			out.println("SIGNINU");
			out.flush();
			try {
				temp = in.readLine();
				if(temp.equals("OK")){
					out.println(txtName.getText());
					out.flush();
					out.println(txtSurname.getText());
					out.flush();
					out.println(txtUserID.getText());
					out.flush();
					out.println(txtPhone.getText());
					out.flush();
					out.println(txtEmail.getText());
					out.flush();
					Random r = new Random();
					int psw = r.nextInt(9999-1000)+1000;
					out.println("Temp-"+ psw);
					out.flush();
					out.println(jcInq.getSelectedItem());
					out.flush();
					if(jcInq.getSelectedItem().equals("Studente")){
						out.println(jcSez.getSelectedItem());
						out.flush();
					}
					if(in.readLine().equals("SIGNOK")){
						JOptionPane.showMessageDialog(this, "Registrazione effettuata","OK",JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
					}else{
						System.out.println("Dati inseriti non corretti!");
					}
				}else{
					System.out.println("Errore!");
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}