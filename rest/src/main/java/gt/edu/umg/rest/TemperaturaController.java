package gt.edu.umg.rest;

import gt.edu.umg.rest.Dtos.PeticionConversion;
import gt.edu.umg.rest.Dtos.RespuestaConversion;
import gt.edu.umg.rest.Dtos.RespuestaError;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Misma operacion que el servicio SOAP, expuesta como recurso HTTP.
 *
 * Diferencias clave frente a SOAP:
 *   - El recurso se identifica por la URL, no por el nombre de una operacion.
 *   - Se usan verbos HTTP (GET / POST) en lugar de una unica operacion POST.
 *   - El estado se comunica con codigos HTTP (200, 400, 404, 405...),
 *     no con un <soap:Fault>.
 */
@RestController
@RequestMapping("/api/temperatura")
public class TemperaturaController {

    /** Logica de negocio compartida por ambos endpoints */
    private RespuestaConversion convertir(double celsius) {
        double fahrenheit = (celsius * 9.0 / 5.0) + 32.0;
        fahrenheit = Math.round(fahrenheit * 100.0) / 100.0;
        System.out.printf("[REST] convertir(%.2f) -> %.2f%n", celsius, fahrenheit);
        return new RespuestaConversion(celsius, "Celsius", fahrenheit, "Fahrenheit");
    }

    /**
     * Estilo orientado a recursos: el valor viaja en la ruta.
     * Ejemplo: GET /api/temperatura/celsius-a-fahrenheit/25
     * Respuesta: 200 OK
     */
    @GetMapping("/celsius-a-fahrenheit/{celsius}")
        public ResponseEntity<RespuestaConversion> obtenerConversion(@PathVariable("celsius") double celsius) {
        return ResponseEntity.ok(convertir(celsius));
    }

    /**
     * Estilo con cuerpo JSON, mas parecido al "llamado a procedimiento" de SOAP.
     * Ejemplo: POST /api/temperatura/conversion  con  {"celsius": 25}
     * Respuesta: 200 OK  /  400 Bad Request si el cuerpo es invalido
     */
    @PostMapping("/conversion")
    public ResponseEntity<RespuestaConversion> crearConversion(
            @Valid @RequestBody PeticionConversion peticion) {
        return ResponseEntity.status(HttpStatus.OK).body(convertir(peticion.celsius()));
    }

    /** Cuerpo JSON invalido o campo faltante -> 400 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> manejarValidacion(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(e -> e.getDefaultMessage())
                .orElse("Cuerpo de la peticion invalido");
        return ResponseEntity.badRequest()
                .body(new RespuestaError(400, "Bad Request", detalle));
    }

    /** Ruta con un valor no numerico, por ejemplo /celsius-a-fahrenheit/abc -> 400 */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespuestaError> manejarTipo(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest()
                .body(new RespuestaError(400, "Bad Request",
                        "El valor '" + ex.getValue() + "' no es un numero valido"));
    }
}
