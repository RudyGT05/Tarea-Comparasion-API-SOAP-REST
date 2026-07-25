package gt.edu.umg.soap.cliente;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Cliente SOAP escrito a mano (sin librerias externas).
 *
 * Se arma el sobre SOAP (Envelope + Body) como texto XML y se envia por HTTP POST.
 * Esto deja ver de forma explicita lo que SOAP exige y REST no:
 * un sobre XML, un Content-Type de tipo text/xml y una cabecera SOAPAction.
 *
 * Ejecutar con:  mvn -pl soap exec:java -Dexec.mainClass=gt.edu.umg.soap.cliente.ClienteSoap
 * (el servicio debe estar corriendo antes)
 */
public class ClienteSoap {

    private static final String ENDPOINT = "http://localhost:8081/ws/conversor";

    public static void main(String[] args) throws IOException, InterruptedException {

        double celsius = args.length > 0 ? Double.parseDouble(args[0]) : 25.0;

        String sobreSoap = """
                <?xml version="1.0" encoding="UTF-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:con="http://soap.umg.edu.gt/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <con:convertirCelsiusAFahrenheit>
                         <celsius>%s</celsius>
                      </con:convertirCelsiusAFahrenheit>
                   </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(celsius);

        HttpRequest peticion = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "\"\"")
                .POST(HttpRequest.BodyPublishers.ofString(sobreSoap, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> respuesta = HttpClient.newHttpClient()
                .send(peticion, HttpResponse.BodyHandlers.ofString());

        System.out.println("---------- PETICION ENVIADA ----------");
        System.out.println(sobreSoap);
        System.out.println("---------- RESPUESTA (HTTP " + respuesta.statusCode() + ") ----------");
        System.out.println(respuesta.body());
    }
}
