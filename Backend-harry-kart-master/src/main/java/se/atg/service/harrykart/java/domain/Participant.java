package se.atg.service.harrykart.java.domain;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;

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
    private String name;
    @XmlElement(required = true)
    private int baseSpeed;


}
