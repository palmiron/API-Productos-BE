package com.servicio.app.productos.service.impl;

import com.servicio.app.productos.service.ProductoServiceHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("productoServiceHelper")
public class ProductoServiceHelperImpl implements ProductoServiceHelper {

    @Override
    public boolean validarRango(Object min, Object max) {
        if (min instanceof Double) {
            return (Double) min < (Double) max;
        } else if (min instanceof LocalDate) {
            return ((LocalDate) min).isBefore((LocalDate) max);
        } else return false;
    }
}
