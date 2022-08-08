package com.servicio.app.productos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonPropertyOrder({"id", "sku", "nombre", "precio", "familia", "marca", "presentacion", "fechaAlta"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO implements Serializable {

    private String id;
    @JsonProperty("sku")
    private String codigo;
    private String nombre;
    private Double precio;
    private String familia;
    private String marca;
    private String presentacion;
    private String fechaAlta;
}
