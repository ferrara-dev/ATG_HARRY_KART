//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.01 at 09:57:53 PM CET 
//


package se.atg.service.harrykart.java.domain;

import lombok.*;
import javax.xml.bind.annotation.*;
import java.math.BigInteger;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "laneType", propOrder = {
    "value"
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class LaneType
{

    @XmlValue
    protected int value;
    @XmlAttribute(name = "number")
    protected int number;


}