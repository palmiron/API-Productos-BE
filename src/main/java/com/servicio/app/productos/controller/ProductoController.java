package com.servicio.app.productos.controller;

import com.servicio.app.productos.commons.ProductoConstants;
import com.servicio.app.productos.commons.ProductoPaths;
import com.servicio.app.productos.model.dto.AgrupadoDTO;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.dto.ProductoDTO;
import com.servicio.app.productos.service.ProductoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductoPaths.PATH_PRODUCTO_BASE)
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @ApiOperation(value = "Listar los Productos"
            , notes = "Devuelve todos los Productos cuando no hay filtros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = ProductoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
            @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_LISTAR,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductoDTO> listarProductos(@RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_NOMBRE, required = false) String nombre,
                                             @RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_FAMILIA, required = false) String familia,
                                             @RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_MARCA, required = false) String marca) {
        return this.productoService.listarProductos(Optional.ofNullable(nombre), Optional.ofNullable(familia), Optional.ofNullable(marca));
    }

    @ApiOperation(value = "Obtener Producto"
            , notes = "Se devuelve el Producto si existe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente", response = ProductoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request.Esta vez cambiamos el tipo de dato de la respuesta (String)", response = String.class),
            @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_OBTENER + "/{" + ProductoConstants.REQUEST_PRODUCTO_ID + "}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductoDTO obtenerProducto(@PathVariable(ProductoConstants.REQUEST_PRODUCTO_ID) String productoId) {
        return this.productoService.obtenerProducto(productoId).orElseThrow(() -> new NotFoundException("El producto no existe"));
    }

    @ApiOperation(value = "Alta de Producto"
            , notes = "Se crea el Producto si no existe")
    @PostMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_ALTA,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void altaProducto(@RequestBody ProductoDTO producto) {
        this.productoService.altaProducto(producto);
    }

    @ApiOperation(value = "Edición de Producto"
            , notes = "Se edita el Producto si existe")
    @PutMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_EDICION,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void edicionProducto(@RequestBody ProductoDTO producto) {
        this.productoService.altaProducto(producto);
    }

    @ApiOperation(value = "Baja de Producto"
            , notes = "Se elimina el Producto si existe")
    @DeleteMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_BAJA + "/{" + ProductoConstants.REQUEST_PRODUCTO_ID + "}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void bajaProducto(@PathVariable(ProductoConstants.REQUEST_PRODUCTO_ID) String id) {
        this.productoService.bajaProducto(id);
    }

    @ApiOperation(value = "Obtener Productos agrupados por rango de precios"
            , notes = "Se devuelve los Productos agrupados dentro del rango de precios")
    @GetMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_RANGO_PRECIOS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AgrupadoDTO> listarProductosAgrupadosRangoPrecios(@RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_PRECIO_MIN) String minPrecio,
                                                                  @RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_PRECIO_MAX) String maxPrecio,
                                                                  @RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_AGRUPADOR, required = false) String agrupador) {
        return this.productoService.listarProductosAgrupadosRangoPrecios(Double.parseDouble(minPrecio), Double.parseDouble(maxPrecio), Optional.ofNullable(agrupador));
    }

    @ApiOperation(value = "Obtener Productos por rango de fechas"
            , notes = "Se devuelve los Productos dentro del rango de fechas")
    @GetMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_RANGO_FECHAS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductoDTO> listarProductosRangoFechas(@RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_FECHA_MIN) String minFecha,
                                                        @RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_FECHA_MAX) String maxFecha) {
        return this.productoService.listarProductosRangoFechas(minFecha, maxFecha);
    }

    @ApiOperation(value = "Obtener cantidad de Productos"
            , notes = "Se devuelve la cantidad de Productos que existen")
    @GetMapping(path = ProductoPaths.PATH_PRODUCTO_BASE_CANTIDAD,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CantidadDTO> listarCantidadProductos(@RequestParam(value = ProductoConstants.REQUEST_PRODUCTO_AGRUPADOR, required = false) String agrupador) {
        return this.productoService.listarCantidadProductos(Optional.ofNullable(agrupador));
    }
}
