import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;


public class Server {
	
	public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        System.out.println("Server Started");
        /*Calendar c=Calendar.getInstance();
    	System.out.println(c.get(Calendar.YEAR));
    	System.out.println(c.get(Calendar.MONTH)+1);
    	System.out.println(c.get(Calendar.DAY_OF_MONTH));*/
        Calendar c = Calendar.getInstance();
        Date date=new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        System.out.println(date);
        System.out.println(date.getTime());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new ServerThread(socket).start();
        }
    }
}
