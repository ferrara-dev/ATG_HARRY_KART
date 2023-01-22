package se.atg.service.harrykart.java.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PowerUps
{
    @XmlElement(required = true, name = "loop")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Loop> loop;

}
