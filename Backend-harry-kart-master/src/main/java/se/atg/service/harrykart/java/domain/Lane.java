
package se.atg.service.harrykart.java.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Lane
{
    @JacksonXmlText
    private Integer value;

    @XmlAttribute(name = "number")
    private int number;


    public Integer getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = Integer.parseInt(value);
    }
    public void setValue(int value) {
        this.value = value;
    }
}
