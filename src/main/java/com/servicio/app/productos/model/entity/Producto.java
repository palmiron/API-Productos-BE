package com.servicio.app.productos.model.entity;

import com.servicio.app.productos.model.ProductoFamilia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "producto")
public class Producto implements Serializable {

    @Id
    private String id;
    private String codigo;
    private String nombre;
    private Double precio;
    private ProductoFamilia familia;
    private String marca;
    private String presentacion;
    private String fechaAlta;

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", familia=" + familia +
                ", marca='" + marca + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", fechaAlta='" + fechaAlta + '\'' +
                '}';
    }
}
