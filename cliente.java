import java.io.*;
import java.net.*;

public class cliente {
    public static void main(String[] args) {
        String servidorDireccion = "localhost"; 
        int puerto = 1234;

        try (Socket socket = new Socket(servidorDireccion, puerto);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String pregunta;
            while ((pregunta = in.readLine()) != null) {
                if (pregunta.equals("FIN")) {
                    break;
                }
                System.out.println(pregunta);
                String respuesta = stdIn.readLine();
                out.println(respuesta);
                String resultado = in.readLine();  
                System.out.println(resultado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
