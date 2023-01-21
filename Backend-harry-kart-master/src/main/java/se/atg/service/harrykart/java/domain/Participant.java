package se.atg.service.harrykart.java.domain;


import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "participantType",
        propOrder = {
        "lane",
        "name",
        "baseSpeed"
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Participant {
    @XmlElement(required = true,  name = "lane")
    protected int lane;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected int baseSpeed;


    /**
     * Gets the value of the lane property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public int getLane() {
        return lane;
    }

    /**
     * Sets the value of the lane property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setLane(int value) {
        this.lane = value;
    }


    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the baseSpeed property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public int getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * Sets the value of the baseSpeed property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setBaseSpeed(int value) {
        this.baseSpeed = value;
    }


}