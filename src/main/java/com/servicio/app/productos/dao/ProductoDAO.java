package com.servicio.app.productos.dao;

import com.servicio.app.productos.model.entity.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoDAO extends MongoRepository<Producto, String>, ProductoDAOCustom {

}
