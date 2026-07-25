package gt.edu.umg.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Objeto de respuesta del servicio SOAP.
 *
 * JAXB convierte esta clase Java en un <complexType> dentro de la seccion
 * <types> del WSDL. Requisitos de JAXB:
 *   - constructor publico sin argumentos
 *   - getters/setters para cada propiedad que se quiera serializar
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoConversion", propOrder = {
        "valorOriginal", "escalaOrigen", "valorConvertido", "escalaDestino"
})
public class ResultadoConversion {

    private double valorOriginal;
    private String escalaOrigen;
    private double valorConvertido;
    private String escalaDestino;

    public ResultadoConversion() {
        // Constructor requerido por JAXB
    }

    public ResultadoConversion(double valorOriginal, String escalaOrigen,
                               double valorConvertido, String escalaDestino) {
        this.valorOriginal = valorOriginal;
        this.escalaOrigen = escalaOrigen;
        this.valorConvertido = valorConvertido;
        this.escalaDestino = escalaDestino;
    }

    public double getValorOriginal() { return valorOriginal; }
    public void setValorOriginal(double valorOriginal) { this.valorOriginal = valorOriginal; }

    public String getEscalaOrigen() { return escalaOrigen; }
    public void setEscalaOrigen(String escalaOrigen) { this.escalaOrigen = escalaOrigen; }

    public double getValorConvertido() { return valorConvertido; }
    public void setValorConvertido(double valorConvertido) { this.valorConvertido = valorConvertido; }

    public String getEscalaDestino() { return escalaDestino; }
    public void setEscalaDestino(String escalaDestino) { this.escalaDestino = escalaDestino; }
}
