package com.servicio.app.productos.dao.impl;

import com.servicio.app.productos.dao.ProductoDAOCustom;
import com.servicio.app.productos.model.dto.AgrupadoDTO;
import com.servicio.app.productos.model.dto.CantidadDTO;
import com.servicio.app.productos.model.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProductoDAOImpl implements ProductoDAOCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductoDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Producto> listarProductos(Optional<String> nombre, Optional<String> familia, Optional<String> marca) {
        if (!nombre.isPresent() && !familia.isPresent() && !marca.isPresent()) {
            return mongoTemplate.findAll(Producto.class);
        }

        MatchOperation matchOperation = getMatchProductos(nombre, familia, marca);

        return mongoTemplate.aggregate(Aggregation.newAggregation(
                matchOperation
        ), Producto.class, Producto.class).getMappedResults();
    }

    private MatchOperation getMatchProductos(Optional<String> nombre, Optional<String> familia, Optional<String> marca) {
        Criteria criteria;

        if (familia.isPresent()) {
            try {
                String f = familia.get().toUpperCase();

                if (marca.isPresent()) {
                    String m = marca.get();

                    criteria = (nombre.map(s -> where("familia").is(f).andOperator(where("marca").is(m)).
                            andOperator(where("nombre").is(s))).orElseGet(() -> where("familia").is(f)));
                } else {
                    criteria = (nombre.map(s -> where("familia").is(f).andOperator(where("nombre").is(s))).
                            orElseGet(() -> where("familia").is(f)));
                }
            } catch (Exception e) {
                throw new IllegalStateException("Familia de producto inválida", e);
            }
        } else if (marca.isPresent()) {
            try {
                String m = marca.get();

                criteria = (nombre.map(s -> where("marca").is(m).andOperator(where("nombre").is(s))).
                        orElseGet(() -> where("marca").is(m)));
            } catch (Exception e) {
                throw new IllegalStateException("Marca de producto inválida", e);
            }
        } else {
            String n = nombre.get();

            criteria = where("nombre").is(n);
        }

        return match(criteria);
    }

    @Override
    public List<AgrupadoDTO> listarProductosAgrupadosRangoPrecios(double minPrecio, double maxPrecio, Optional<String> agrupador) {
        MatchOperation matchOperation = getMatchRangoPrecios(minPrecio, maxPrecio);
        GroupOperation groupOperation = getGroupRangoPrecios(agrupador.orElse("familia"));
        ProjectionOperation projectionOperation = getProjectRangoPrecios();

        return mongoTemplate.aggregate(Aggregation.newAggregation(
                matchOperation,
                groupOperation,
                projectionOperation
        ), Producto.class, AgrupadoDTO.class).getMappedResults();
    }

    private MatchOperation getMatchRangoPrecios(double minPrecio, double maxPrecio) {
        Criteria precioCriteria = where("precio").gte(minPrecio).andOperator(where("precio").lte(maxPrecio));

        return match(precioCriteria);
    }

    private GroupOperation getGroupRangoPrecios(String agrupador) {
        return group(agrupador)
                .last(agrupador).as("agrupador")
                .addToSet("codigo").as("productosCodigo")
                .avg("precio").as("precioPromedio")
                .sum("precio").as("gananciaTotal");
    }

    private ProjectionOperation getProjectRangoPrecios() {
        return project("productosCodigo", "precioPromedio", "gananciaTotal")
                .and("agrupador").previousOperation();
    }

    @Override
    public List<Producto> listarProductosRangoFechas(String minFecha, String maxFecha) {
        MatchOperation matchOperation = getMatchRangoFechas(minFecha, maxFecha);

        return mongoTemplate.aggregate(Aggregation.newAggregation(
                matchOperation
        ), Producto.class, Producto.class).getMappedResults();
    }

    private MatchOperation getMatchRangoFechas(String minFecha, String maxFecha) {
        Criteria fechaCriteria = where("fechaAlta").gte(minFecha).andOperator(where("fechaAlta").lte(maxFecha));

        return match(fechaCriteria);
    }

    @Override
    public List<CantidadDTO> listarCantidadProductos(Optional<String> agrupador) {
        if (agrupador.isPresent()) {
            GroupOperation groupOperation = getGroupCantidad(agrupador.get());
            ProjectionOperation projectionOperation = getProjectCantidad();

            return mongoTemplate.aggregate(Aggregation.newAggregation(
                    groupOperation,
                    projectionOperation
            ), Producto.class, CantidadDTO.class).getMappedResults();
        } else {
            long cantidad = mongoTemplate.count(new Query(), Producto.class);
            CantidadDTO cantidadDTO = CantidadDTO.builder().agrupador("total").cantidadProductos(cantidad).build();
            List<CantidadDTO> cantidadDTOList = new ArrayList<>();
            cantidadDTOList.add(cantidadDTO);

            return cantidadDTOList;
        }
    }

    private GroupOperation getGroupCantidad(String agrupador) {
        return group(agrupador)
                .last(agrupador).as("agrupador")
                .count().as("cantidadProductos");
    }

    private ProjectionOperation getProjectCantidad() {
        return project("cantidadProductos").and("agrupador").previousOperation();
    }
}
