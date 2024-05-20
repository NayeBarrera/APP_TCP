public class pregunta {
    private String pregunta;
    private String respuesta;

    public pregunta(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public boolean evaluarRespuesta(String respuestaCliente) {
        return respuestaCliente.equalsIgnoreCase(respuesta);
    }
}