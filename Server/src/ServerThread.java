
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ServerThread extends Thread {
    protected Socket socket;
    private final String url = "jdbc:postgresql://localhost/LabB";
    private final String user = "admin";
    private final String password = "admin";
    

    String line;

    InputStream inp = null;
    BufferedReader brinp = null;
    PrintWriter sout = null;

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            sout = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        
        connect();
    }

    public void run() {
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    if(line.equals("LOGINU") || line.equals("LOGINL")){
                    	login(line);
                    }
                    if(line.equals("SIGNINU") || line.equals("SIGNINL")){
                    	signin(line);
                    }
                    if(line.equals("SBOOK")){
                    	nOrder();
                    }
                    if(line.equals("BKLIST")){
                    	booksList();
                    }
                    if(line.equals("AUTENTICATE")){
                    	autenticate();
                    }
                    if(line.equals("AUTENTICATECODE")){
                    	autenticateCode();
                    }
                    if(line.equals("SETSTATUS")){
                    	setStatus();
                    }
                    if(line.equals("DELETEUSER")){
                    	deleteUser();
                    }
                    if(line.equals("ORDER")){
                    	bookOrder();
                    }
                    if(line.equals("GETPSW")){
                    	getPsw();
                    }
                    if(line.equals("SETPSW")){
                    	setPsw();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    private void getPsw() throws IOException{
    	String tmp;
    	
    	tmp=brinp.readLine();
    	String query="SELECT \"User\".\"Password\""
    			+ "FROM public.\"User\""
    			+ "WHERE \"User\".\"CodFiscale\"='"+tmp+"'";
    	
    	try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                
                
                sout.println(rs.getString(1));
                sout.flush();
    	}catch(Exception w){
    		
    	}
    	
    }
    private void setPsw() throws IOException{
    	String user,psw;
    	user=brinp.readLine();
    	psw=brinp.readLine();
    	String query="UPDATE public.\"User\""
    			+ " SET \"Password\"='"+psw+"'"
    			+ " WHERE \"User\".\"CodFiscale\"='"+user+"'";
    	System.out.println(query);
    	try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
       
                
    	} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void deleteUser() throws IOException{
    	String tmp;
    	
    	tmp=brinp.readLine();
    	String query="DELETE FROM public.\"User\""
    			+ "WHERE \"User\".\"CodFiscale\"='"+tmp+"'";
    	System.out.println(query);
    	try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
       
                
    	} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void setStatus()throws IOException{
    	String tmp;
    	
    	tmp=brinp.readLine();
    	String query="UPDATE public.\"User\""
    			+ " SET \"Autenticato\"=true"
    			+ " WHERE \"User\".\"CodFiscale\"='"+tmp+"'";
    	System.out.println(query);
    	try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
       
                
    	} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void autenticate() throws IOException{
    	String tmp;
    	boolean aut;
    	tmp=brinp.readLine();
    	String query="SELECT \"User\".\"Autenticato\""
    			+ "FROM public.\"User\""
    			+ "WHERE \"User\".\"CodFiscale\"='"+tmp+"'";
    	
    	try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                aut=rs.getBoolean(1);
                //sout.println(rs.getString(1));
                //sout.flush();
                if(aut==true){
                	sout.println("TRUE");
                	sout.flush();
                }else{
                	sout.println("FALSE");
                	sout.flush();
                }
    	} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    	
    }
    private void autenticateCode() throws IOException{
    	String tmp;
    	
    	tmp=brinp.readLine();
    	String query="SELECT \"User\".\"CodAut\""
    			+ "FROM public.\"User\""
    			+ "WHERE \"User\".\"CodFiscale\"='"+tmp+"'";
    	
    	try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                
                //sout.println(rs.getString(1));
                //sout.flush();
                
                sout.println(rs.getString(1));
                sout.flush();
                
    	} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    	
    }
    private void bookOrder() throws IOException{
    	String book,user;
    	book=brinp.readLine();
    	user=brinp.readLine();
    	
    }
    private void nOrder() throws IOException{
		String tmp=brinp.readLine();
		
		String query="SELECT count(*)"
				+ "FROM public.\"User\" u join public.\"Prenotazione\" p on u.\"CodFiscale\" = p.\"IDUser\""
				+ "WHERE u.\"CodFiscale\"='"+tmp+"' ";
		
		String query2 ="SELECT count(*)"
				+ "FROM public.\"User\" u join public.\"Prestito\" p on u.\"CodFiscale\" = p.\"IDUser\""
						+ "WHERE u.\"CodFiscale\"='"+tmp+ "' and p.\"DataConsegna\" is null";
		//System.out.println(query);
		try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                sout.println(rs.getString(1));
                sout.flush();   
                
                ResultSet rs1 = stmt.executeQuery(query2);
                rs1.next();
                sout.println(rs1.getString(1));
                sout.flush();   
                System.out.println(rs.getString(1));
                System.out.println(rs1);
                
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		//System.out.println("error");
	}
    private void booksList() throws IOException{
		String title,type,author;
		String temp="";
		title=brinp.readLine();
		type=brinp.readLine();
		author=brinp.readLine();
		System.out.println(title+type+author);
		String query="SELECT *"
				+ "FROM public.\"Libro\" l join public.\"Tipologia\" ti on l.\"Tipologia\" = ti.\"ID\""
				+ "WHERE l.\"ISBN\" is not null "; 
				//(l.\"Titolo\" like '"+title+"%' or l.\"Titolo\" like '_"+title+"_')" and ti.\"Tipologia\" like '"+type+"%' and (l.\"Autore\"[1] ='"+author+"' or l.\"Autore\"[2] ='"+author+"')";
		if(!title.equals("") && title!=null){
			query=query + "and (l.\"Titolo\" like '"+title+"%' or l.\"Titolo\" like '_"+title+"_')";
		}
		if(!type.equals("") && type!=null){
			query=query + "and ti.\"Tipologia\" like '"+type+"%'";
		}
		if(!author.equals("") && author!=null){
			query=query + "and (l.\"Autore\"[1] ='"+author+"' or l.\"Autore\"[2] ='"+author+"')";
		}
		System.out.println(query);
		try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
				//System.out.println(rs.getString(1));
                while(rs.next()){
                	for(int i=1;i<=9;i++){
	                	temp=temp+rs.getString(i)+" ";
                		//System.out.print(rs.getString(2));
	                	
	                }
                	//System.out.println(temp);
                	
                	sout.println(temp);
                	sout.flush();
                	temp="";
                }
                sout.println("END");
                sout.flush();
                
		} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
	}
    private void login(String line) {
    	String name,psw;
		sout.println("OK");
		sout.flush();
		try {
			name = brinp.readLine();
			psw = brinp.readLine();
			String query = "SELECT COUNT(*)"
					+ " FROM public.\"User\""
					+ " WHERE \"User\".\"CodFiscale\"='"+name+"' and \"User\".\"Password\"='"+psw+"'";
			if(line.equals("LOGINL")){
				query = query + " and \"User\".\"Inquadramento\"='Bibliotecario'";
			}else{
				query = query + " and \"User\".\"Inquadramento\"!='Bibliotecario'";
			}
			sout.println(queryLogin(query));
			sout.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    private void signin(String type){
    	String name, surname, userID, phone, email, psw, inq, sez;
		sout.println("OK");
		sout.flush();
		try {
			name = brinp.readLine();
			surname = brinp.readLine();
			userID = brinp.readLine();
			phone = brinp.readLine();
			email = brinp.readLine();
			psw = brinp.readLine();
			if(type.equals("SIGNINU")){
				inq = brinp.readLine();
				if(inq.equals("Studente")){
					sez = brinp.readLine();
					inq = inq + sez;
				}
			}else{
				inq = "Bibliotecario";
			}
			if(check(userID, email).equals("0")){
				if(register(name,surname,userID,phone,email,psw,inq)){
					sout.println("SIGNOK");
					sout.flush();
				}
			}else{
				System.out.println("Codice fiscale o email già utilizzate!");
				sout.println("FAIL");
				sout.flush();
			}
			String query = "";
			querySignin(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private boolean register(String name, String surname, String userID,
			String phone, String email, String psw, String inq) {
		String SQL = "Insert into public.\"User\"(\"CodFiscale\",\"Nome\",\"Cognome\",\"Telefono\",\"Inquadramento\",\"Email\",\"Autenticato\",\"Password\",\"CodAut\")"
                + "VALUES(?,?,?,?,?,?,?,?,?)";
 
        long id = 0;
 
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
 
            pstmt.setString(1, userID);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setString(4, phone);
            pstmt.setString(5, inq);
            pstmt.setString(6, email);
            pstmt.setBoolean(7, false);
            pstmt.setString(8, psw);
            Random r = new Random();
            pstmt.setInt(9, r.nextInt(9999-1000)+1000);
            
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
            	return true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
	}

	private String queryLogin(String query) {
		try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                return rs.getString(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		return "Error";
	}

	private void querySignin(String query) {
		try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                System.out.println(rs.getString(1));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
	private String check(String userID, String email) {
		String query = "SELECT COUNT(*)"
				+ " FROM public.\"User\""
				+ " WHERE \"User\".\"CodFiscale\"='"+userID+"' or \"User\".\"Email\"='"+email+"'";
		try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)){
                rs.next();
                return rs.getString(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		return "Error";
	}

	
	public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully. ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
}