package com.servicio.app.productos;

import com.servicio.app.productos.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@EnableEurekaClient
@Import(SwaggerConfiguration.class)
@EnableAutoConfiguration
@SpringBootApplication
public class ServicioAppProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioAppProductosApplication.class, args);
    }
}
