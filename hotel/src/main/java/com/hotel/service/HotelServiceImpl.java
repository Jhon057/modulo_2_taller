package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.dto.mapper.HotelMapper;
import com.hotel.dto.request.HotelRequestDto;
import com.hotel.dto.response.HotelResponseDto;
import com.hotel.dto.response.HotelResponseHabitacionDto;
import com.hotel.exception.HotelException;
import com.hotel.exception.HotelNoEncontradoException;
import com.hotel.exception.HotelRegistroException;
import com.hotel.model.Hotel;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HotelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HabitacionRepository habitacionRepository;

    @Override
    public HotelResponseDto crear(HotelRequestDto request) {

        if (hotelRepository.existsByCiudadIgnoreCaseAndNombreIgnoreCase(request.getCiudad(), request.getNombre()))
            throw new HotelRegistroException(request.getCiudad(), request.getNombre());

        Hotel hotel = hotelMapper.aEntidad(request);
        return hotelMapper.aRespuesta(hotelRepository.save(hotel));
    }

    @Override
    public List<HotelResponseDto> listarTodos() {
        return hotelRepository.findAll().stream().map(hotelMapper::aRespuesta).toList();
    }

    @Override
    public HotelResponseDto buscarPorId(Long id) {
        return hotelRepository.findById(id).map(hotelMapper::aRespuesta)
                .orElseThrow(() -> new HotelNoEncontradoException(id));
    }

    @Override
    public List<HotelResponseDto> buscarPorCiudad(String ciudad) {
        return hotelRepository.findByCiudad(ciudad).stream().map(hotelMapper::aRespuesta).toList();
    }

    @Override
    public HotelResponseDto actualizar(Long id, HotelRequestDto request) {
        return hotelRepository.findById(id).map(h -> {
            h.setNombre(request.getNombre());
            h.setCiudad(request.getCiudad());
            h.setDireccion(request.getDireccion());
            h.setCategoria(request.getCategoria());
            h.setTelefono(request.getTelefono());
            hotelRepository.save(h);
            return hotelMapper.aRespuesta(h);

        }).orElseThrow(() -> new HotelNoEncontradoException(id));

    }

    public void eliminar(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNoEncontradoException(id));

        if (habitacionRepository.existsByHotelId(id)) {
            throw new HotelException(
                    "El hotel tiene habitaciones asignadas, debe eliminarlas primero");
        }

        hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelResponseDto> habitacionDisponible(Long cantidadHabitaciones) {
        return hotelRepository.habitacionDisponible(cantidadHabitaciones).stream().map(hotelMapper::aRespuesta)
                .toList();
    }

    @Override
    public HotelResponseHabitacionDto listarHotelHabitaciones(Long idHotel) {

        Hotel hotel = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new HotelNoEncontradoException(idHotel));

        return hotelMapper.aRespuestaHabitacion(hotel);
    }

}
