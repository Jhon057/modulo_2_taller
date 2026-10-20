package com.hotel.config.startup;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.enums.EstadoReserva;
import com.hotel.model.Reserva;
import com.hotel.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservaStartupRunner implements ApplicationRunner {

    private final ReservaRepository reservaRepository;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {

        List<Reserva> reservas = reservaRepository
                .findByEstadoReservaAndFechaSalidaLessThanEqual(
                        EstadoReserva.CONFIRMADA,
                        LocalDate.now(ZoneId.systemDefault()));

        for (Reserva reserva : reservas) {

            reserva.setEstadoReserva(EstadoReserva.COMPLETADA);
            Long reservaId = reserva.getId();
            log.info("Reservas actualizadas al iniciar la aplicación." + reservaId);
        }

        reservaRepository.saveAll(reservas);

    }
}
