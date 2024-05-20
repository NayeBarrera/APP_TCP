import java.io.*;
import java.net.*;
import java.util.List;

public class hilocliente extends Thread {
    private Socket socket;
    private List<pregunta> preguntas;
    private BufferedReader in;
    private PrintWriter out;

    public hilocliente(Socket socket, List<pregunta> preguntas) {
        this.socket = socket;
        this.preguntas = preguntas;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            for (pregunta pregunta : preguntas) {
                out.println("PREGUNTA:" + pregunta.getPregunta());
                out.flush();

                String respuestaCliente = in.readLine();

                if (pregunta.evaluarRespuesta(respuestaCliente)) {
                    out.println("RESPUESTA:Correcta");
                } else {
                    out.println("RESPUESTA:Incorrecta");
                }

                out.flush();
            }

            out.println("FIN DEL JUEGO");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}