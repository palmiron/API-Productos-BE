package com.servicio.app.productos.commons;

public class ProductoPaths {

    public static final String PATH_PRODUCTO_BASE = "/api/v1/productos";

    public static final String PATH_PRODUCTO_BASE_LISTAR = "/listar";
    public static final String PATH_PRODUCTO_BASE_OBTENER = "/obtener";

    public static final String PATH_PRODUCTO_BASE_ALTA = "/alta";
    public static final String PATH_PRODUCTO_BASE_EDICION = "/edicion";
    public static final String PATH_PRODUCTO_BASE_BAJA = "/baja";

    public static final String PATH_PRODUCTO_BASE_CANTIDAD = PATH_PRODUCTO_BASE_OBTENER + "/cantidad";

    public static final String PATH_PRODUCTO_BASE_RANGO = "/rango";
    public static final String PATH_PRODUCTO_BASE_RANGO_PRECIOS = PATH_PRODUCTO_BASE_LISTAR + PATH_PRODUCTO_BASE_RANGO + "/precios";
    public static final String PATH_PRODUCTO_BASE_RANGO_FECHAS = PATH_PRODUCTO_BASE_LISTAR + PATH_PRODUCTO_BASE_RANGO + "/fechas";
}
