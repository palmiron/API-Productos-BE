package com.servicio.app.productos.model.mapper;

import com.servicio.app.productos.model.dto.ProductoDTO;
import com.servicio.app.productos.model.entity.Producto;

import java.util.List;

public interface ProductoMapper {

    ProductoDTO toDTO(Producto p);

    Producto toEntity(ProductoDTO dto);

    List<ProductoDTO> toDTOList(List<Producto> productoList);
}
