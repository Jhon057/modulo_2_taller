package api.conecta_barrio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import api.conecta_barrio.dto.request.LiderRequestDto;
import api.conecta_barrio.dto.response.LiderResponseDto;
import api.conecta_barrio.models.Lider;

@Mapper(componentModel = "spring")
public interface LiderMapper {

    @Mapping(target = "id", ignore = true)
    Lider aEntidad(LiderRequestDto dto);

    LiderResponseDto aRespuesta(Lider entidad);

}
