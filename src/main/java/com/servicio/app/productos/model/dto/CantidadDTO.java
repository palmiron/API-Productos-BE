package com.servicio.app.productos.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonPropertyOrder({"agrupador", "cantidadProductos"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CantidadDTO implements Serializable {

    private String agrupador;
    private long cantidadProductos;
}
