package se.atg.service.harrykart.java.domain;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class HarryKartRequest implements Serializable {
    @XmlElement(required = true, name = "numberOfLoops")
    protected int numberOfLoops;
    @XmlElement(required = true, name = "startList")
    protected StartList startList;
    @XmlElement(required = true, name = "powerUps")
    protected PowerUps powerUps;
}
