package appLibrarian;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LogLibrarian extends JFrame implements ActionListener{
	JLabel lblTitle, lblTitleR;
	//LOGIN
	JLabel lblUsernameLog, lblPswLog;
	JPasswordField txtPswLog;
	JTextField txtUsernameLog;
	//REGISTRAZIONE
	JLabel lblName, lblSurname, lblUserID, lblPhone, lblEmail, lblPswReg, lblConfPswReg;
	JPasswordField txtPswReg,txtConfPswReg;
	JTextField txtName, txtSurname, txtUserID, txtPhone, txtEmail;
	
	JButton signIn,logIn;
	Socket socket;
	private int PORT=8080;
	private String Server="localhost";
	
	InputStream inp = null;
    BufferedReader in = null;
    PrintWriter out = null;
	
	public LogLibrarian(){
	setSize(300, 440);
	setLocation(450, 160);
	setTitle("AppReader");
	setResizable(false);
	
	
	JTabbedPane tabbedPane = new JTabbedPane();

    JPanel panelLogin = new JPanel(false);
    panelLogin.setLayout(new GridLayout(6, 1));
    
    tabbedPane.addTab("Login", null, panelLogin, "Accedi");
    tabbedPane.setSelectedIndex(0);
    
    lblTitle = new JLabel("APP LIBRARIAN", SwingConstants.CENTER);
    panelLogin.add(lblTitle);
    
    JPanel panelFormLog = new JPanel();
    panelFormLog.setLayout(new GridBagLayout());
    GridBagConstraints gbcLog = new GridBagConstraints();        
    
    gbcLog.insets = new Insets(0,20,0,10);
    gbcLog.fill = GridBagConstraints.HORIZONTAL;
    gbcLog.gridx = 0;
    gbcLog.gridy = 0;
    gbcLog.weightx = 1;
    lblUsernameLog = new JLabel("Username");
    panelFormLog.add(lblUsernameLog,gbcLog);

    gbcLog.insets = new Insets(0,0,0,20);
    gbcLog.gridx = 1;
    gbcLog.gridy = 0;
    gbcLog.weightx = 10;
    txtUsernameLog = new JTextField("");
    panelFormLog.add(txtUsernameLog,gbcLog);

    gbcLog.insets = new Insets(0,20,0,10);
    gbcLog.gridx = 0;
    gbcLog.gridy = 1;
    gbcLog.weightx = 1;
    lblPswLog = new JLabel("Password");
    panelFormLog.add(lblPswLog,gbcLog);

    gbcLog.insets = new Insets(0,0,0,20);
    gbcLog.gridx = 1;
    gbcLog.gridy = 1;
    gbcLog.weightx = 10;
    txtPswLog = new JPasswordField("");
    panelFormLog.add(txtPswLog,gbcLog);
    
    panelLogin.add(panelFormLog);
    
    JPanel panelLoginBtn = new JPanel();
    panelLoginBtn.setLayout(new GridBagLayout());   
    gbcLog.insets = new Insets(0,20,0,20);
    gbcLog.gridwidth = GridBagConstraints.REMAINDER;
    gbcLog.fill = GridBagConstraints.HORIZONTAL;
    logIn = new JButton("Login");
    logIn.addActionListener(this);
    panelLoginBtn.add(logIn,gbcLog);
    
    panelLogin.add(panelLoginBtn);
    
    JLabel lblPswFrgt = new JLabel("Hai dimenticato la password?",SwingConstants.CENTER);
    Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
    fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    lblPswFrgt.setForeground(Color.BLUE);
    lblPswFrgt.setFont(new Font("Serif",Font.BOLD, 12).deriveFont(fontAttributes));
    lblPswFrgt.addMouseListener( new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            System.out.println("Do something");
        }
        public void mouseEntered(MouseEvent e) {
            //You can change the appearance here to show a hover state
        }
        public void mouseExited(MouseEvent e) {
            //Then change the appearance back to normal.
        }
    });
    
    panelLogin.add(lblPswFrgt);
    
    
    JPanel panelRegistration = new JPanel(false);
    panelRegistration.setLayout(new GridLayout(6,1));
    GridBagConstraints gbcOverReg = new GridBagConstraints();  
    tabbedPane.addTab("Registrazione", null, panelRegistration, "Crea il tuo account");
 
    gbcOverReg.insets = new Insets(0,0,0,0);
    gbcOverReg.gridx = 0;
    gbcOverReg.gridy = 0;
    gbcOverReg.weighty = 2;
    lblTitleR = new JLabel("APP LIBRARIAN", SwingConstants.CENTER);
    panelRegistration.add(lblTitleR);
    
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
    
    gbcReg.insets = new Insets(0,10,0,18);
    gbcReg.gridx = 0;
    gbcReg.gridy = 0;
    gbcReg.weightx = 1;
    lblPswReg = new JLabel("Password");
    panelFormReg3.add(lblPswReg,gbcReg);

    gbcReg.insets = new Insets(0,0,0,10);
    gbcReg.gridx = 1;
    gbcReg.gridy = 0;
    gbcReg.weightx = 10;
    txtPswReg = new JPasswordField("");
    panelFormReg3.add(txtPswReg,gbcReg);
    
    gbcReg.insets = new Insets(0,10,0,10);
    gbcReg.gridx = 0;
    gbcReg.gridy = 1;
    gbcReg.weightx = 1;
    lblConfPswReg = new JLabel("Conf Password");
    panelFormReg3.add(lblConfPswReg,gbcReg);

    gbcReg.insets = new Insets(0,0,0,10);
    gbcReg.gridx = 1;
    gbcReg.gridy = 1;
    gbcReg.weightx = 100;
    txtConfPswReg = new JPasswordField("");
    panelFormReg3.add(txtConfPswReg,gbcReg);
    
    panelRegistration.add(panelFormReg1);
    panelRegistration.add(panelFormReg2);
    panelRegistration.add(panelFormReg3);
    
    JPanel panelSignInBtn = new JPanel();
    panelSignInBtn.setLayout(new GridBagLayout());   
    gbcReg.insets = new Insets(0,20,0,20);
    gbcReg.gridwidth = GridBagConstraints.REMAINDER;
    gbcReg.fill = GridBagConstraints.HORIZONTAL;
    signIn = new JButton("Sign In");
    signIn.addActionListener(this);
    panelSignInBtn.add(signIn);
    
    panelRegistration.add(panelSignInBtn);



    //Add the tabbed pane to this panel.
    setLayout(new GridLayout());
    add(tabbedPane);
	
	try {
		socket=new Socket(Server,PORT);
		inp = socket.getInputStream();
	    in = new BufferedReader(new InputStreamReader(inp));
	    out = new PrintWriter(socket.getOutputStream());
	}catch(Exception z) {}
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==signIn){
			String temp;
			out.println("SIGNINL");
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
					out.println(txtPswReg.getText());
					out.flush();
					if(in.readLine().equals("SIGNOK")){
						new AppLibrarian(socket,txtUsernameLog.getText());
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
		if(e.getSource()==logIn){
			String temp;
			out.println("LOGINL");
			out.flush();
			try {
				temp = in.readLine();
				if(temp.equals("OK")){
					out.println(txtUsernameLog.getText());
					out.flush();
					out.println(txtPswLog.getText());
					out.flush();
					temp = in.readLine();
					if(temp.equals("1")){
						new AppLibrarian(socket,txtUsernameLog.getText());
						this.dispose();
					}else if(temp.equals("0")){
						System.out.println("Dati inseriti non corretti!");
					}else if(temp.equals("Error")){
						System.out.println(temp);
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

public static void main(String args[]){
	new LogLibrarian();
}
	
	
}
