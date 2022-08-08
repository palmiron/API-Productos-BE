package com.servicio.app.productos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"agrupador", "productosSku", "precioPromedio", "gananciaTotal"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgrupadoDTO implements Serializable {

    private String agrupador;
    @JsonProperty("productosSku")
    private List<String> productosCodigo;
    private double precioPromedio;
    private double gananciaTotal;
}
