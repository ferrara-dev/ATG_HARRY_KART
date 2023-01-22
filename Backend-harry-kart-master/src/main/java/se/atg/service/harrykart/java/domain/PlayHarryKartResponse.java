package se.atg.service.harrykart.java.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlayHarryKartResponse {
    private List<ParticipantRanking> ranking;
}
