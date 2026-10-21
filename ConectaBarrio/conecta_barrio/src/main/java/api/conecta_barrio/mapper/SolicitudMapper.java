package api.conecta_barrio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import api.conecta_barrio.dto.request.SolicitudRequestDto;
import api.conecta_barrio.dto.response.SolicitudResponseDto;
import api.conecta_barrio.models.Solicitud;

@Mapper(componentModel = "spring", uses = {LiderMapper.class})
public interface SolicitudMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "prioridad", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "lider.id", source = "idLider")
    Solicitud aEntidad(SolicitudRequestDto dto);

    SolicitudResponseDto aRespuesta(Solicitud entidad);

}
