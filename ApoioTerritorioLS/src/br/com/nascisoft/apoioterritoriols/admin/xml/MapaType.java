//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.23 at 10:56:56 AM BRST 
//


package br.com.nascisoft.apoioterritoriols.admin.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MapaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MapaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="identificadorRegiao" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="letra" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MapaType", propOrder = {
    "identificador",
    "identificadorRegiao",
    "numero",
    "letra"
})
public class MapaType {

    protected long identificador;
    protected long identificadorRegiao;
    protected int numero;
    @XmlElement(required = true)
    protected String letra;

    /**
     * Gets the value of the identificador property.
     * 
     */
    public long getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     */
    public void setIdentificador(long value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the identificadorRegiao property.
     * 
     */
    public long getIdentificadorRegiao() {
        return identificadorRegiao;
    }

    /**
     * Sets the value of the identificadorRegiao property.
     * 
     */
    public void setIdentificadorRegiao(long value) {
        this.identificadorRegiao = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Gets the value of the letra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Sets the value of the letra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetra(String value) {
        this.letra = value;
    }

}
