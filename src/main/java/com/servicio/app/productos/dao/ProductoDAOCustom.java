package com.servicio.app.productos.dao;

import com.servicio.app.productos.model.dto.AgrupadoDTO;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoDAOCustom {

    List<Producto> listarProductos(Optional<String> nombre, Optional<String> familia, Optional<String> marca);

    List<AgrupadoDTO> listarProductosAgrupadosRangoPrecios(double minPrecio, double maxPrecio, Optional<String> agrupador);

    List<Producto> listarProductosRangoFechas(String minFecha, String maxFecha);

    List<CantidadDTO> listarCantidadProductos(Optional<String> agrupador);
}
