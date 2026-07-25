package gt.edu.umg.rest;

import jakarta.validation.constraints.NotNull;

/**
 * Objetos de transferencia del servicio REST.
 *
 * Se usan "records" de Java 17: Jackson los serializa a JSON automaticamente.
 * Notese el contraste con SOAP: aqui NO existe un contrato formal publicado,
 * la estructura del JSON es simplemente la forma de estas clases.
 */
public class Dtos {

    /** Cuerpo esperado en el POST /api/temperatura/conversion */
    public record PeticionConversion(
            @NotNull(message = "El campo 'celsius' es obligatorio")
            Double celsius
    ) {}

    /** Respuesta exitosa (HTTP 200) */
    public record RespuestaConversion(
            double valorOriginal,
            String escalaOrigen,
            double valorConvertido,
            String escalaDestino
    ) {}

    /** Respuesta de error (HTTP 400) */
    public record RespuestaError(
            int codigo,
            String error,
            String mensaje
    ) {}
}
