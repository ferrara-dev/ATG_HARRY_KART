package se.atg.service.harrykart.java.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.atg.service.harrykart.java.domain.HarryKartRequest;
import se.atg.service.harrykart.java.domain.PlayHarryKartResponse;
import se.atg.service.harrykart.java.domain.exception.ValidationException;
import se.atg.service.harrykart.java.service.RankingService;


@RestController
@RequestMapping("/java/api")
@RequiredArgsConstructor
public class HarryKartController {
    private static final Logger logger = LoggerFactory.getLogger(HarryKartController.class);

    @Autowired
    private RankingService rankingService;

    @PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayHarryKartResponse> playHarryKart(@RequestBody HarryKartRequest request) {
        return ResponseEntity.ok(rankingService.computeRanking(request));
    }



    public void validateRequestBody(HarryKartRequest requestBody) {
        if(requestBody.getNumberOfLoops() <= 0){
            throw new ValidationException("Number of loops must be larger than 0");
        }
    }
}
