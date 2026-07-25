package gt.edu.umg.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 * Servicio SOAP implementado con el enfoque CODE FIRST:
 * se escribe la clase Java y el runtime de JAX-WS genera el WSDL
 * automaticamente en tiempo de ejecucion (endpoint + "?wsdl").
 *
 * Cada anotacion se refleja directamente en el contrato:
 *   @WebService(targetNamespace) -> namespace del WSDL
 *   @WebService(serviceName)     -> <service name="...">
 *   @WebService(portName)        -> <port name="...">
 *   @WebMethod(operationName)    -> <operation name="..."> en el <portType>
 *   @WebParam(name)              -> nombre del elemento dentro del <message> de entrada
 *   @WebResult(name)             -> nombre del elemento dentro del <message> de salida
 */
@WebService(
        name = "ConversorTemperatura",
        serviceName = "ConversorTemperaturaService",
        portName = "ConversorTemperaturaPort",
        targetNamespace = "http://soap.umg.edu.gt/"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class ConversorTemperaturaService {

    /**
     * Unica operacion del servicio: convierte grados Celsius a Fahrenheit.
     * Formula: F = (C * 9/5) + 32
     */
    @WebMethod(operationName = "convertirCelsiusAFahrenheit")
    @WebResult(name = "resultado")
    public ResultadoConversion convertirCelsiusAFahrenheit(
            @WebParam(name = "celsius") double celsius) {

        double fahrenheit = (celsius * 9.0 / 5.0) + 32.0;
        // Se redondea a 2 decimales para una salida limpia
        fahrenheit = Math.round(fahrenheit * 100.0) / 100.0;

        System.out.printf("[SOAP] convertirCelsiusAFahrenheit(%.2f) -> %.2f%n", celsius, fahrenheit);

        return new ResultadoConversion(celsius, "Celsius", fahrenheit, "Fahrenheit");
    }
}
