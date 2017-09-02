
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
    private final String user = "postgres";
    private final String password = "an1996";
    

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
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
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
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
}