package se.atg.service.harrykart.java.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atg.service.harrykart.java.domain.HarryKartRequest;
import se.atg.service.harrykart.java.domain.PlayHarryKartResponse;
import se.atg.service.harrykart.java.service.RankingService;


@RestController
@RequestMapping("/java/api")
@RequiredArgsConstructor
public class HarryKartController {

    @Autowired
    private RankingService rankingService;

    @PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayHarryKartResponse> playHarryKart(@RequestBody HarryKartRequest request) {
        return ResponseEntity.ok(rankingService.computeRanking(request));
    }

}
