package appLibrarian;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class AppLibrarian extends JFrame implements ActionListener{
	JButton options,search,order,register,librarianOption;
	JLabel status,ncode,codUserID,orderedBook,nborrowedBook,norderedBook,lblauthor,lbltipe,lbltitle;
	JTextArea area;
	JScrollPane scroll;
	JLabel blank1,blank2,blank3,blank4;
	JTextField author,type,title,code,userBook;
	JPanel top,bot;
	private int error=0;
	String Sstatus="Non Attivo";
	String Sactive="Attivo";
	boolean bstatus=false;
	private Integer bBook=0,oBook=0;
	Socket socket;

	private static String userID;

	InputStream inp = null;
    BufferedReader in = null;
    PrintWriter out = null;
    JButton aut;
    JTextField autCode;
    JFrame f;
    JLabel l;
	
	
	private boolean running=true;
	
	public AppLibrarian(Socket socket,String user){
		try{
			this.socket=socket;
			
			inp = socket.getInputStream();
		    in = new BufferedReader(new InputStreamReader(inp));
		    out = new PrintWriter(socket.getOutputStream());
		}catch(Exception z){}	
		
		userID=user;
		
		f=new JFrame();
		l=new JLabel("Inserisci codice verifica");
		aut=new JButton("Verifica");
		autCode=new JTextField();
		aut.addActionListener(this);	
		
		
		setSize(700, 400);
		setLocation(450, 160);
		setTitle("AppReader");
		setResizable(false);
		setLayout(new BorderLayout());
		this.socket=socket;
		
		top=new JPanel();
		top.setLayout(new GridLayout(3,4,0,0));
		add(top,BorderLayout.NORTH);
		
		options=new JButton("options");
		options.addActionListener(this);
		
		register=new JButton("register");
		register.addActionListener(this);
		
		librarianOption=new JButton("Librarian Option");
		librarianOption.addActionListener(this);
		
		search=new JButton("search");
		search.addActionListener(this);
		
		blank1=new JLabel();
		blank2=new JLabel();
		blank3=new JLabel();
		status=new JLabel(Sstatus,SwingConstants.CENTER);
	
		
		lbltitle=new JLabel("Titolo");
		lblauthor=new JLabel("Tipologia");
		lbltipe=new JLabel("Autore");
		
		type=new JTextField();
		author=new JTextField();
		title=new JTextField();
		
		
		top.add(options);
		top.add(register);
		top.add(librarianOption);
		top.add(status);
		
		top.add(lbltitle);
		top.add(lblauthor);
		top.add(lbltipe);
		top.add(blank3);
		
		top.add(title);
		top.add(type);
		top.add(author);
		top.add(search);
		
		
		area=new JTextArea();
		area.setEditable(false);
		scroll=new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(area,BorderLayout.CENTER);
		
		bot=new JPanel();
		bot.setLayout(new GridLayout(2,4,0,0));
		
		ncode=new JLabel("Codice Libro :");
		codUserID=new JLabel("UserID:");
		blank4=new JLabel();
		
		code=new JTextField();
		userBook=new JTextField();
		order=new JButton("ordina");
		order.addActionListener(this);
		
		add(bot,BorderLayout.SOUTH);
		
		bot.add(ncode);
		bot.add(code);
		bot.add(blank4);
		bot.add(codUserID);
		bot.add(userBook);
		bot.add(order);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		autOk();
		
	}
	
	private void autOk(){
		try {
		String tmp;
		out.println("AUTENTICATE");
		out.flush();
		out.println(userID);
		out.flush();
		System.out.println("autenticate");
			tmp=in.readLine();
		if(tmp.equals("TRUE")){
			bstatus=true;
			status.setText(Sactive);
		}else if(tmp.equals("FALSE")){
			bstatus=false;
			status.setText(Sstatus);
			JOptionPane.showMessageDialog(this, "Profilo non Attivo.","Inane warning",JOptionPane.WARNING_MESSAGE);
			//JFrame f=new JFrame();
			//JLabel l=new JLabel("Inserisci codice verifica");
			//JButton aut=new JButton("Verifica");
			//aut.addActionListener(this);
			f.setSize(250, 150);
			f.setLocation(550, 160);
			f.setTitle("Autenticate");
			f.setResizable(false);
			f.setLayout(new GridLayout(3,1));
			f.add(l);
			f.add(autCode);
			f.add(aut);
			f.setVisible(true);
			
			//f.setDefaultCloseOperation(EXIT_ON_CLOSE);

		} 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==aut){
			String temp;
			System.out.println(autCode.getText());
			out.println("AUTENTICATECODE");
			out.flush();
			out.println(userID);
			out.flush();
			try {
				temp=in.readLine();
				if(temp.equals(autCode.getText())){
					out.println("SETSTATUS");
					out.flush();
					out.println(userID);
					out.flush();
					autOk();
					f.dispose();
				}else{
					error++;
					autCode.setText("");
					JOptionPane.showMessageDialog(this, error+"° tentativo fallito","Inane warning",JOptionPane.WARNING_MESSAGE);
					if(error==5){
						JOptionPane.showMessageDialog(this,"Numero massimo di errori raggiunto,"
								+ "il Profivo verrà eliminato","Inane warning",JOptionPane.WARNING_MESSAGE);
						out.println("DELETEUSER");
						out.flush();
						out.println(userID);
						out.flush();
						out.println("QUIT");
						out.flush();
						dispose();
						f.dispose();
					
					}
					
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource()==options){
			new LibrerianBaseOptions(socket,userID);
		}
		if(e.getSource()==librarianOption){
			new LibrerianOptions(socket,userID);
		}
		if(e.getSource()==search){
			if(bstatus){	
				area.setText("");
				String temp;
				int items=0;
				out.println("BKLIST");
				out.flush();
				out.println(title.getText());
				out.flush();
				out.println(type.getText());
				out.flush();
				out.println(author.getText());
				out.flush();
				System.out.println("tipo"+type.getText());
				
				try {
					temp=in.readLine();
					System.out.println("Client->"+temp);
					while(!temp.equals("END")){
						//System.out.println("Client->"+temp);
						if(items%2==0 && items!=0)area.append("\n\n");
							area.append(" "+temp+"\n");
							items++;
							temp=in.readLine();
						
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(this, "Profilo non Attivo.","Inane warning",JOptionPane.WARNING_MESSAGE);
			}
		}
		if(e.getSource()==order){
			int bBook=0,oBook=0;
			if(code.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Nessun codice inserito","Inane warning",JOptionPane.WARNING_MESSAGE);
				
			}else if(userBook.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Nessun UserID inserito","Inane warning",JOptionPane.WARNING_MESSAGE);
			}else{
				if(checkAut(userBook.getText())){
					try {
					    out.println("SBOOK");
					    out.flush();
					    out.println(userBook.getText());
					    out.flush();
					    oBook=Integer.parseInt(in.readLine());
					    bBook=Integer.parseInt(in.readLine());
					}catch(Exception z) {}
					if(bBook==5 || oBook==10){ //Ricevi numero di libri di utente
							if(bBook==5) JOptionPane.showMessageDialog(this, "Numero massimo prenotazioni raggiunto.","Inane warning",JOptionPane.WARNING_MESSAGE);
							if(oBook==10) JOptionPane.showMessageDialog(this, "Numero massimo prestiti attivi raggiunto.","Inane warning",JOptionPane.WARNING_MESSAGE);	
						}else {
							out.println("ORDER");
							out.flush();
							
							out.println(code.getText());
							out.flush();
							
							out.println(userBook.getText());
							out.flush();
							
							try {
								if(in.readLine().equals("OK")){
									JOptionPane.showMessageDialog(this, "Prenotazione effettuata","OK",JOptionPane.INFORMATION_MESSAGE);
								}else{
									JOptionPane.showMessageDialog(this, "Codice libro errato!","Inane warning",JOptionPane.WARNING_MESSAGE);
								}
							} catch (HeadlessException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							code.setText("");
							userBook.setText("");
						}
					
				}else{
					JOptionPane.showMessageDialog(this, "Profilo non attivo o non esistente","Inane warning",JOptionPane.WARNING_MESSAGE);
				}
				
			}	
		}
		if(e.getSource()==register){
			new CreateUser(socket, userID);
		}
	}

	
	private boolean checkAut(String userID){
		String tmp;
		out.println("AUTENTICATE");
		out.flush();
		out.println(userID);
		out.flush();
		System.out.println("autenticate");
		try {
			tmp=in.readLine();
		
		if(tmp.equals("TRUE")){
			return true;
		}else if(tmp.equals("FALSE")){
			return false;
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


		return false;
	}

}
