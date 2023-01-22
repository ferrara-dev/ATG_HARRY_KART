package se.atg.service.harrykart.java.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StartList
{

    @XmlElement(required = true, name = "participant")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Participant> participant;


    public List<Participant> getParticipant() {
        if(Objects.isNull(participant))
            this.participant = new ArrayList<>();

        return participant;
    }
}
