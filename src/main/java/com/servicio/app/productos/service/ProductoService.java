package com.servicio.app.productos.service;

import com.servicio.app.productos.model.dto.AgrupadoDTO;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.dto.ProductoDTO;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<ProductoDTO> listarProductos(Optional<String> nombre, Optional<String> familia, Optional<String> marca);

    Optional<ProductoDTO> obtenerProducto(String productoId);

    void altaProducto(ProductoDTO producto);

    void bajaProducto(String productoId);

    List<AgrupadoDTO> listarProductosAgrupadosRangoPrecios(double minPrecio, double maxPrecio, Optional<String> agrupador);

    List<ProductoDTO> listarProductosRangoFechas(String minFecha, String maxFecha);

    List<CantidadDTO> listarCantidadProductos(Optional<String> agrupador);
}
