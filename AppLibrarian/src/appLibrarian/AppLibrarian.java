package appLibrarian;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	JLabel status,ncode,borrowedBook,orderedBook,nborrowedBook,norderedBook,lblauthor,lbltipe,lbltitle;
	JTextArea area;
	JScrollPane scroll;
	JLabel blank1,blank2,blank3,blank4;
	JTextField author,tipe,title,code;
	JPanel top,bot;
	String Sstatus="Non Attivo";
	String Sactive="Attivo";
	boolean bstatus=false;
	private Integer bBook=0,oBook=0;
	Socket socket;
	private boolean running=true;
	
	public AppLibrarian(Socket s){
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
		
		tipe=new JTextField();
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
		top.add(author);
		top.add(tipe);
		top.add(search);
		
		
		area=new JTextArea();
		area.setEditable(false);
		scroll=new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(area,BorderLayout.CENTER);
		
		bot=new JPanel();
		bot.setLayout(new GridLayout(2,4,0,0));
		
		ncode=new JLabel("Codice Libro :");
		borrowedBook=new JLabel("Libri in prestito:");
		nborrowedBook=new JLabel();
		orderedBook=new JLabel("Libri prenotati:");
		norderedBook=new JLabel();
		blank4=new JLabel();
		
		code=new JTextField();
		order=new JButton("ordina");
		order.addActionListener(this);
		
		add(bot,BorderLayout.SOUTH);
		
		bot.add(ncode);
		bot.add(code);
		bot.add(blank4);
		bot.add(order);
		bot.add(borrowedBook);
		bot.add(nborrowedBook);
		bot.add(orderedBook);
		bot.add(norderedBook);
		setVisible(true);
		if(bstatus==false) {
			JOptionPane.showMessageDialog(this, "Profilo non Attivo.","Inane warning",JOptionPane.WARNING_MESSAGE);
		
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==options){
			new ReaderOptions();
		}
		if(e.getSource()==librarianOption){
			new LibrerianOptions();
		}
		if(e.getSource()==search){
			
		}
		if(e.getSource()==order){
			//test --> delete this shit
			oBook++;
			bBook++;
			nborrowedBook.setText(bBook.toString());
			norderedBook.setText(oBook.toString());
			 System.out.println();
		}
		
	}

}
