package com.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefono;

    @Column(name = "telefono_emergencia", nullable = false)
    private String telefonoEmergencia;

    private String direccion;

    private String pais;

    private String nacionalidad;

    @OneToOne(mappedBy = "perfilContacto", fetch = FetchType.LAZY)
    @JsonIgnore
    private Huesped huesped;

}
