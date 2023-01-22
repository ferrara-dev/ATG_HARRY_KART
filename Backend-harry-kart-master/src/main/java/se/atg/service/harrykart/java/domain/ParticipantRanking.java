package se.atg.service.harrykart.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ParticipantRanking {
    private int position;
    private String horse;
    @JsonIgnore
    private BigDecimal finalTime;
}
