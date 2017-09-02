package appLibrarian;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LibrerianOptions extends JFrame implements ActionListener{
	JButton loanBook,historyLoanBook,totalLoanBook,totalReservedBook,rankingBook,totalExpiredLoan;
	
	public LibrerianOptions() {
		
		
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
		
		setSize(350, 250);
		setLocation(450, 160);
		setTitle("Options");
		setResizable(false);
		setLayout(new GridLayout(3,2,5,5));
	
		
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