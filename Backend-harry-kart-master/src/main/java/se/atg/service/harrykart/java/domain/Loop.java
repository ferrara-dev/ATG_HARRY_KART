package se.atg.service.harrykart.java.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Loop
{
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Lane> lane;

    @XmlAttribute(name = "number")
    private int number;


    public List<Lane> getLane() {
        if (lane == null) {
            lane = new ArrayList<Lane>();
        }
        return this.lane;
    }
}
