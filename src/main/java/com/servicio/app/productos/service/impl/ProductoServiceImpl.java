package com.servicio.app.productos.service.impl;

import com.servicio.app.productos.dao.ProductoDAO;
import com.servicio.app.productos.model.dto.AgrupadoDTO;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.dto.ProductoDTO;
import com.servicio.app.productos.model.entity.Producto;
import com.servicio.app.productos.model.mapper.ProductoMapper;
import com.servicio.app.productos.service.ProductoService;
import com.servicio.app.productos.service.ProductoServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("productoService")
public class ProductoServiceImpl implements ProductoService {

    private final ProductoServiceHelper productoServiceHelper;
    private final ProductoDAO productoDAO;
    private final ProductoMapper productoMapper;

    @Autowired
    public ProductoServiceImpl(ProductoServiceHelper productoServiceHelper, ProductoDAO productoDAO, ProductoMapper productoMapper) {
        this.productoServiceHelper = productoServiceHelper;
        this.productoDAO = productoDAO;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDTO> listarProductos(Optional<String> nombre, Optional<String> familia, Optional<String> marca) {
        List<Producto> productoList = this.productoDAO.listarProductos(nombre, familia, marca);

        return this.productoMapper.toDTOList(productoList);
    }

    @Override
    public Optional<ProductoDTO> obtenerProducto(String productoId) {
        Optional<Producto> p = this.productoDAO.findById(productoId);
        ProductoDTO dto = p.isPresent() ? this.productoMapper.toDTO(p.get()) : null;

        return Optional.ofNullable(dto);
    }

    @Override
    public void altaProducto(ProductoDTO producto) {
        Producto p = this.productoMapper.toEntity(producto);
        this.productoDAO.save(p);
    }

    @Override
    public void bajaProducto(String productoId) {
        this.productoDAO.deleteById(productoId);
    }

    @Override
    public List<AgrupadoDTO> listarProductosAgrupadosRangoPrecios(double minPrecio, double maxPrecio, Optional<String> agrupador) {
        if (this.productoServiceHelper.validarRango(minPrecio, maxPrecio)) {
            return this.productoDAO.listarProductosAgrupadosRangoPrecios(minPrecio, maxPrecio, agrupador);
        } else {
            throw new IllegalStateException("El valor mínimo NO puede ser mayor al máximo");
        }
    }

    @Override
    public List<ProductoDTO> listarProductosRangoFechas(String minFecha, String maxFecha) {
        if (this.productoServiceHelper.validarRango(LocalDate.parse(minFecha), LocalDate.parse(maxFecha))) {
            List<Producto> productoList = this.productoDAO.listarProductosRangoFechas(minFecha, maxFecha);

            return this.productoMapper.toDTOList(productoList);
        } else {
            throw new IllegalStateException("La fecha mínima NO puede ser mayor a la máxima");
        }
    }

    @Override
    public List<CantidadDTO> listarCantidadProductos(Optional<String> agrupador) {
        return this.productoDAO.listarCantidadProductos(agrupador);
    }
}
