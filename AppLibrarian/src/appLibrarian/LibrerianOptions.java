package appLibrarian;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LibrerianOptions extends JFrame implements ActionListener{
	JButton loanBook,historyLoanBook,totalLoanBook,totalReservedBook,rankingBook,totalExpiredLoan,delateBook,registerUser,addBook;
	Socket socket;
	String userID;
	
	public LibrerianOptions(Socket socket, String userID) {

		this.userID=userID;
		this.socket=socket;
		
		loanBook=new JButton("Elenco Prestiti Utente");
		loanBook.addActionListener(this);
		add(loanBook);
		historyLoanBook=new JButton("Elenco Storico Utente");
		historyLoanBook.addActionListener(this);
		add(historyLoanBook);
		totalLoanBook=new JButton("Libri in prestito");
		totalLoanBook.addActionListener(this);
		add(totalLoanBook);
		totalReservedBook=new JButton("Libri prenotati");
		totalReservedBook.addActionListener(this);
		add(totalReservedBook);
		rankingBook=new JButton("Classifica Libri");
		rankingBook.addActionListener(this);
		add(rankingBook);
		totalExpiredLoan = new JButton("Elenco Prestiti Sconfinati");
		totalExpiredLoan.addActionListener(this);
		add(totalExpiredLoan);

		delateBook=new JButton("Elimina libro");
		delateBook.addActionListener(this);
		add(delateBook);
		registerUser = new JButton("Registra utente");
		registerUser.addActionListener(this);
		add(delateBook);

		addBook=new JButton("Aggiungi libro");
		addBook.addActionListener(this);
		add(addBook);
		
		setSize(350, 250);
		setLocation(450, 160);
		setTitle("Options");
		setResizable(false);
		setLayout(new GridLayout(4,2,5,5));
	
		
		setVisible(true);

	}
	
	

	
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==loanBook){
			
		}
		if(a.getSource()==historyLoanBook){
			
		}
		if(a.getSource()==totalLoanBook){
			
		}
		
	}
}