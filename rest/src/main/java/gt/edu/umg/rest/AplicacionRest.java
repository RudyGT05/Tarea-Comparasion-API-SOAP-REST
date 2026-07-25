package gt.edu.umg.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del servicio REST.
 * Spring Boot levanta un Tomcat embebido en el puerto 8080.
 */
@SpringBootApplication
public class AplicacionRest {

    public static void main(String[] args) {
        SpringApplication.run(AplicacionRest.class, args);
        System.out.println("=================================================");
        System.out.println(" Servicio REST disponible en http://localhost:8080");
        System.out.println(" GET  /api/temperatura/celsius-a-fahrenheit/{celsius}");
        System.out.println(" POST /api/temperatura/conversion");
        System.out.println("=================================================");
    }
}
