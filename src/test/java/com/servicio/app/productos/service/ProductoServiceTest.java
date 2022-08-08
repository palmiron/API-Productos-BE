package com.servicio.app.productos.service;

import com.servicio.app.productos.dao.ProductoDAO;
import com.servicio.app.productos.model.ProductoFamilia;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.dto.ProductoDTO;
import com.servicio.app.productos.model.entity.Producto;
import com.servicio.app.productos.model.mapper.ProductoMapper;
import com.servicio.app.productos.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    private ProductoService service;
    private final ProductoServiceHelper productoServiceHelper = mock(ProductoServiceHelper.class);
    private final ProductoDAO productoDAO = mock(ProductoDAO.class);
    private final ProductoMapper productoMapper = mock(ProductoMapper.class);
    private static ProductoDTO productoDTO;
    private static Producto producto;
    private static List<Producto> productoList = new ArrayList<>();
    private static String id;
    private static String agrupador;
    private static List<CantidadDTO> cantidadDTOList = new ArrayList<>();

    @BeforeEach
    public void init() {
        this.service = new ProductoServiceImpl(productoServiceHelper, productoDAO, productoMapper);
        productoDTO = ProductoDTO.builder().codigo("12345").nombre("Quilmes Clasica 1.5L").
                familia(ProductoFamilia.CERVEZAS.toString()).marca("Quilmes").
                presentacion("Botella").precio(150.00).fechaAlta("2022-07-28").build();
        producto = Producto.builder().codigo("12345").nombre("Quilmes Clasica 1.5L").
                familia(ProductoFamilia.CERVEZAS).marca("Quilmes").
                presentacion("Botella").precio(150.00).fechaAlta("2022-07-28").build();
        productoList.add(producto);
        id = "1";
        agrupador = "familia";
        cantidadDTOList.add(CantidadDTO.builder().agrupador(agrupador).cantidadProductos(productoList.size()).build());
    }

    @Test
    public void testListarProductos() {
        when(this.productoDAO.findAll()).thenReturn(productoList);
        this.service.listarProductos(Optional.ofNullable(productoDTO.getNombre()), Optional.ofNullable(productoDTO.getFamilia()), Optional.ofNullable(productoDTO.getMarca()));
        verify(this.productoDAO, atLeastOnce()).findAll();
    }

    @Test
    public void testObtenerProducto() {
        when(this.productoDAO.findById(id)).thenReturn(Optional.ofNullable(producto));
        this.service.obtenerProducto(id);
        verify(this.productoDAO, atLeastOnce()).findById(id);
    }

    @Test
    public void testAltaProducto() {
        when(this.productoDAO.save(producto)).thenReturn(producto);
        this.service.altaProducto(productoDTO);
        verify(this.productoDAO, atLeastOnce()).save(producto);
    }

    @Test
    public void testBajaProducto() {
        doNothing().when(this.productoDAO).deleteById(id);
        this.service.bajaProducto(id);
        verify(this.productoDAO, atLeastOnce()).deleteById(id);
    }

    @Test
    public void testObtenerCantidadProductos() {
        when(this.productoDAO.listarCantidadProductos(Optional.ofNullable(agrupador))).thenReturn(cantidadDTOList);
        this.service.listarCantidadProductos(Optional.ofNullable(agrupador));
        verify(this.productoDAO, atLeastOnce()).listarCantidadProductos(Optional.ofNullable(agrupador));
    }
}
