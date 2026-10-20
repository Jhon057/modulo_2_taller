package com.hotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.report.ReporteFinanciero;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteFinanciero reporteFinanciero;

    @GetMapping("/total")
    public ResponseEntity<String> calcularIngresoTotal() {
        return ResponseEntity.ok(reporteFinanciero.calcularIngresoTotal());
    }

}
