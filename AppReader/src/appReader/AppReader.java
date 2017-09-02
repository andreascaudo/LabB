package appReader;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class AppReader extends JFrame implements ActionListener{
	JButton options,search,order;
	JLabel status,ncode,borrowedBook,orderedBook,nborrowedBook,norderedBook,lblauthor,lbltype,lbltitle;
	JTextArea area;
	JScrollPane scroll;
	JLabel blank1,blank2,blank3,blank4;
	JTextField author,type,title,code;
	JPanel top,bot;
	String Sstatus="Non Attivo";
	String Sactive="Attivo";
	boolean bstatus;
	private Integer bBook=0,oBook=0;
	Socket socket;
	private boolean running=true;
	private static String userID;
	InputStream inp = null;
    BufferedReader in = null;
    PrintWriter out = null;
 
	public AppReader(Socket socket,String user){
		try{
			this.socket=socket;
			
			inp = socket.getInputStream();
		    in = new BufferedReader(new InputStreamReader(inp));
		    out = new PrintWriter(socket.getOutputStream());
		}catch(Exception z){}	
		
		userID=user;
		
		setSize(700, 400);
		setLocation(450, 160);
		setTitle("AppReader");
		setResizable(false);
		setLayout(new BorderLayout());
		
		
		top=new JPanel();
		top.setLayout(new GridLayout(3,4,0,0));
		add(top,BorderLayout.NORTH);
		
		options=new JButton("options");
		options.addActionListener(this);
		
		search=new JButton("search");
		search.addActionListener(this);
		
		blank1=new JLabel();
		blank2=new JLabel();
		blank3=new JLabel();
		status=new JLabel(Sstatus);
	
		
		lbltitle=new JLabel("Titolo");
		lblauthor=new JLabel("Tipologia");
		lbltype=new JLabel("Autore");
		
		type=new JTextField();
		author=new JTextField();
		title=new JTextField();
		
		
		top.add(options);
		top.add(blank1);
		top.add(blank2);
		top.add(status);
		
		top.add(lbltitle);
		top.add(lblauthor);
		top.add(lbltype);
		top.add(blank3);
		
		top.add(title);
		top.add(type);
		top.add(author);
		top.add(search);
		
		
		area=new JTextArea();
		area.setEditable(false);
		scroll=new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll,BorderLayout.CENTER);
		
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
		updateSbook();
		if(bstatus==false) {
			JOptionPane.showMessageDialog(this, "Profilo non Attivo.","Inane warning",JOptionPane.WARNING_MESSAGE);
		
		}
		updateSbook();
		
		
	}
	private void updateSbook(){
		try {
		    out.println("SBOOK");
		    out.flush();
		    out.println(userID);
		    out.flush();
		    oBook=Integer.parseInt(in.readLine());
		   // System.out.println(oBook);
		    norderedBook.setText(oBook.toString());
		    bBook=Integer.parseInt(in.readLine());
		    //System.out.println(bBook);
		    nborrowedBook.setText(bBook.toString()); 
		    /*norderedBook.setText(in.readLine());
		    nborrowedBook.setText(in.readLine());
		    System.out.println(norderedBook.getText());
		    System.out.println(nborrowedBook.getText());*/
		}catch(Exception z) {}
	}
	private void autOk() throws IOException{
		String tmp;
		out.println("AUTENTICATE");
		out.flush();
		tmp=in.readLine();
		if(tmp.equals("TRUE")){
			
		}else if(tmp.equals("FALSE")){
			
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==options){
			new ReaderOptions();
		}
		if(e.getSource()==search){
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
				//System.out.println("Client->"+temp);
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
		}
		if(e.getSource()==order){
			//test --> delete this shit
			//oBook++;
			//bBook++;
			//nborrowedBook.setText(bBook.toString());
			//norderedBook.setText(oBook.toString());
			//System.out.println();
			if(bBook==5 || oBook==10){
				if(bBook==5) JOptionPane.showMessageDialog(this, "Numero massimo prenotazioni raggiunto.","Inane warning",JOptionPane.WARNING_MESSAGE);
				if(oBook==10) JOptionPane.showMessageDialog(this, "Numero massimo prestiti attivi raggiunto.","Inane warning",JOptionPane.WARNING_MESSAGE);	
			}else{
				//do order
			}
		}
		
	}

}
