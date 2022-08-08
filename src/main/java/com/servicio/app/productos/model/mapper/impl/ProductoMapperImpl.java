package com.servicio.app.productos.model.mapper.impl;

import com.servicio.app.productos.model.ProductoFamilia;
import com.servicio.app.productos.model.dto.ProductoDTO;
import com.servicio.app.productos.model.entity.Producto;
import com.servicio.app.productos.model.mapper.ProductoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("productoMapper")
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDTO toDTO(Producto p) {
        return ProductoDTO.builder().
                id(p.getId()).
                codigo(p.getCodigo()).
                nombre(p.getNombre()).
                precio(p.getPrecio()).
                familia(p.getFamilia().toString()).
                marca(p.getMarca()).
                presentacion(p.getPresentacion()).
                fechaAlta(p.getFechaAlta()).build();
    }

    @Override
    public Producto toEntity(ProductoDTO dto) {
        return Producto.builder().
                codigo(dto.getCodigo()).
                nombre(dto.getNombre()).
                precio(dto.getPrecio()).
                familia(ProductoFamilia.valueOf(dto.getFamilia().toUpperCase())).
                marca(dto.getMarca()).
                presentacion(dto.getPresentacion()).
                fechaAlta(dto.getFechaAlta()).build();
    }

    @Override
    public List<ProductoDTO> toDTOList(List<Producto> productoList) {
        List<ProductoDTO> dtoList = new ArrayList<>();

        for (Producto p : productoList) {
            dtoList.add(this.toDTO(p));
        }

        return dtoList;
    }
}
