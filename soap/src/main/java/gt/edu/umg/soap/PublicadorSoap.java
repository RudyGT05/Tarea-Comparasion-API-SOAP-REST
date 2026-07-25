package gt.edu.umg.soap;

import jakarta.xml.ws.Endpoint;

/**
 * Levanta el servicio SOAP en un servidor HTTP embebido (el que trae el JDK).
 * No se necesita Tomcat ni ningun servidor de aplicaciones externo.
 */
public class PublicadorSoap {

    public static final String URL_ENDPOINT = "http://localhost:8081/ws/conversor";

    public static void main(String[] args) {
        Endpoint.publish(URL_ENDPOINT, new ConversorTemperaturaService());

        System.out.println("=================================================");
        System.out.println(" Servicio SOAP publicado correctamente");
        System.out.println(" Endpoint : " + URL_ENDPOINT);
        System.out.println(" WSDL     : " + URL_ENDPOINT + "?wsdl");
        System.out.println(" XSD      : " + URL_ENDPOINT + "?xsd=1");
        System.out.println(" (Ctrl + C para detener)");
        System.out.println("=================================================");
    }
}
