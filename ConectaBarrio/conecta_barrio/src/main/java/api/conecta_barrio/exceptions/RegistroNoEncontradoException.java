package api.conecta_barrio.exceptions;

public class RegistroNoEncontradoException extends RuntimeException {
    public RegistroNoEncontradoException(Long id) {
        super("No hay registro con id: " + id);
    }

}
