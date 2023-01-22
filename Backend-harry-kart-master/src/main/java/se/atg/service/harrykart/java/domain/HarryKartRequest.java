package se.atg.service.harrykart.java.domain;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class HarryKartRequest implements Serializable {
    @XmlElement(required = true, name = "numberOfLoops")
    private int numberOfLoops;
    @XmlElement(required = true, name = "startList")
    private StartList startList;
    @XmlElement(required = true, name = "powerUps")
    private PowerUps powerUps;
}
