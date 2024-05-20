import java.io.*;
import java.net.*;
import java.util.*;

public class servidor {
    private static final int PUERTO = 1234;
    private static List<Pregunta> preguntas;

    public static void main(String[] args) throws IOException {
        preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Cuál es el océano más grande del mundo?", "Océano Pacífico"));
        preguntas.add(new Pregunta("¿Cuánto es 5 + 3?", "8"));
        preguntas.add(new Pregunta("¿Cuál es la capital de Ecuador?", "Quito"));
        preguntas.add(new Pregunta("¿Cuál es la raíz cuadrada de 49?", "7"));
        preguntas.add(new Pregunta("¿Cuál es el idioma oficial de Ecuador?", "Español"));

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado");
                new HiloCliente(socket, preguntas).start();
            }
        }
    }

    static class HiloCliente extends Thread {
        private Socket socket;
        private List<Pregunta> preguntas;

        public HiloCliente(Socket socket, List<Pregunta> preguntas) {
            this.socket = socket;
            this.preguntas = preguntas;
        }

        public void run() {
            try (
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
            ) {
                for (Pregunta pregunta : preguntas) {
                    writer.println(pregunta.getPregunta());
                    String respuesta = reader.readLine();
                    if (respuesta.equalsIgnoreCase(pregunta.getRespuesta())) {
                        writer.println("Correcto");
                    } else {
                        writer.println("Incorrecto. Respuesta correcta: " + pregunta.getRespuesta());
                    }
                }
                writer.println("FIN");
            } catch (IOException e) {
                System.err.println("Error en el hilo del cliente: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
            }
        }
    }

    static class Pregunta {
        private String pregunta;
        private String respuesta;

        public Pregunta(String pregunta, String respuesta) {
            this.pregunta = pregunta;
            this.respuesta = respuesta;
        }

        public String getPregunta() {
            return pregunta;
        }

        public String getRespuesta() {
            return respuesta;
        }
    }
}
