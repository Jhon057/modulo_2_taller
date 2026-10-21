package api.conecta_barrio.exceptions;

import api.conecta_barrio.enums.EstadoSolicitud;

public class SolicitudCambiarEstadoException extends RuntimeException {
    public SolicitudCambiarEstadoException(EstadoSolicitud estado) {
        super("No se puede cambiar el estado de la solicitud. " + estado);
    }

}
