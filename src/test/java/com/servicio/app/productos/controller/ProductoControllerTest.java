package com.servicio.app.productos.controller;

import com.servicio.app.productos.model.ProductoFamilia;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.dto.ProductoDTO;
import com.servicio.app.productos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductoControllerTest {

    private ProductoController controller;
    private final ProductoService productoService = mock(ProductoService.class);
    private static ProductoDTO productoDTO;
    private static List<ProductoDTO> productoDTOList = new ArrayList<>();
    private static String id;
    private static String agrupador;
    private static List<CantidadDTO> cantidadDTOList = new ArrayList<>();

    @BeforeEach
    public void init() {
        this.controller = new ProductoController(productoService);
        productoDTO = ProductoDTO.builder().codigo("12345").nombre("Quilmes Clasica 1.5L").
                familia(ProductoFamilia.CERVEZAS.toString()).marca("Quilmes").
                presentacion("Botella").precio(150.00).fechaAlta("2022-07-28").build();
        productoDTOList.add(productoDTO);
        id = "1";
        agrupador = "familia";
        cantidadDTOList.add(CantidadDTO.builder().agrupador(agrupador).cantidadProductos(productoDTOList.size()).build());
    }

    @Test
    public void testListarProductos() {
        when(this.productoService.listarProductos(Optional.ofNullable(productoDTO.getNombre()), Optional.ofNullable(productoDTO.getFamilia()), Optional.ofNullable(productoDTO.getMarca()))).
                thenReturn(productoDTOList);
        this.controller.listarProductos(productoDTO.getNombre(), productoDTO.getFamilia(), productoDTO.getMarca());
        verify(this.productoService, atLeastOnce()).listarProductos(Optional.ofNullable(productoDTO.getNombre()), Optional.ofNullable(productoDTO.getFamilia()), Optional.ofNullable(productoDTO.getMarca()));
    }

    @Test
    public void testObtenerProducto() {
        when(this.productoService.obtenerProducto(id)).thenReturn(Optional.ofNullable(productoDTO));
        this.controller.obtenerProducto(id);
        verify(this.productoService, atLeastOnce()).obtenerProducto(id);
    }

    @Test
    public void testAltaProducto() {
        doNothing().when(this.productoService).altaProducto(productoDTO);
        this.controller.altaProducto(productoDTO);
        verify(this.productoService, atLeastOnce()).altaProducto(productoDTO);
    }

    @Test
    public void testEdicionProducto() {
        doNothing().when(this.productoService).altaProducto(productoDTO);
        this.controller.edicionProducto(productoDTO);
        verify(this.productoService, atLeastOnce()).altaProducto(productoDTO);
    }

    @Test
    public void testBajaProducto() {
        doNothing().when(this.productoService).bajaProducto(id);
        this.controller.bajaProducto(id);
        verify(this.productoService, atLeastOnce()).bajaProducto(id);
    }

    @Test
    public void testObtenerCantidadProductos() {
        when(this.productoService.listarCantidadProductos(Optional.ofNullable(agrupador))).thenReturn(cantidadDTOList);
        this.controller.listarCantidadProductos(agrupador);
        verify(this.productoService, atLeastOnce()).listarCantidadProductos(Optional.ofNullable(agrupador));
    }
}
