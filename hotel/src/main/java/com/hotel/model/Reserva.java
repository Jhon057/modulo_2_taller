package com.hotel.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hotel.enums.EstadoReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estadoReserva = EstadoReserva.PENDIENTE;

    @Column(name = "total_calculado", nullable = false)
    private BigDecimal totalCalculado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "huesped_id", nullable = false)
    private Huesped huesped;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reserva_servicio", joinColumns = @JoinColumn(name = "reserva_id"), inverseJoinColumns = @JoinColumn(name = "servicio_id"))
    private List<Servicio> servicios = new ArrayList<>();

}
