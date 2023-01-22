package se.atg.service.harrykart.java.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import se.atg.service.harrykart.java.domain.HarryKartRequest;
import se.atg.service.harrykart.java.domain.ParticipantRanking;
import se.atg.service.harrykart.java.domain.PlayHarryKartResponse;
import se.atg.service.harrykart.java.domain.exception.ValidationException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
public class RankingServiceTest {

    private static final String INPUT = "input_0.xml";
    private static final String INPUT1 = "input_1.xml";
    private static final String INPUT2 = "input_2.xml";

    private static final String INPUT3 = "input_3.xml"; // input with shared ranking
    private static final String INPUT4 = "input_4.xml"; // input with negative speed, one removed
    private static final String INPUT5 = "input_5.xml"; // input with negative speed, one removed

    private static final String INPUT6 = "input_6.xml"; // input with negative speed, one removed

    private RankingService rankingService;

    @BeforeAll
    void setUp() {
        rankingService = new RankingService();
    }


    @DisplayName("Returning top 3 ranking")
    @ParameterizedTest
    @ValueSource(strings = {INPUT1, INPUT2})
    public void shouldReturnTopThreeRanking(String input) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + input);
        XmlMapper xmlMapper = new XmlMapper();

        HarryKartRequest request = xmlMapper.readValue(file, HarryKartRequest.class);

        PlayHarryKartResponse playHarryKartResponse = rankingService.computeRanking(request);

        List<ParticipantRanking> rankingList =  playHarryKartResponse.getRanking();

        assertEquals(3, rankingList.size());

        ParticipantRanking ranking1 = rankingList.get(0);
        ParticipantRanking ranking2 = rankingList.get(1);
        ParticipantRanking ranking3 = rankingList.get(2);

        assertTrue(     ranking1.getFinalTime().doubleValue() <  ranking2.getFinalTime().doubleValue() );
        assertTrue(     ranking2.getFinalTime().doubleValue() <  ranking3.getFinalTime().doubleValue() );

        assertEquals(ranking1.getPosition(),1);
        assertEquals(ranking2.getPosition(),2);
        assertEquals(ranking3.getPosition(),3);

    }



    @DisplayName("Returning top 3 ranking with shared ranking")
    @ParameterizedTest
    @ValueSource(strings = {INPUT3})
    public void shouldReturnTopThreeRankingWithSharedRanking(String input) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + input);
        XmlMapper xmlMapper = new XmlMapper();

        HarryKartRequest request = xmlMapper.readValue(file, HarryKartRequest.class);

        PlayHarryKartResponse playHarryKartResponse = rankingService.computeRanking(request);

        List<ParticipantRanking> rankingList =  playHarryKartResponse.getRanking();

        assertEquals(3, rankingList.size());

        ParticipantRanking ranking1 = rankingList.get(0);
        ParticipantRanking ranking2 = rankingList.get(1);
        ParticipantRanking ranking3 = rankingList.get(2);

        assertEquals(ranking1.getFinalTime().doubleValue(), ranking2.getFinalTime().doubleValue());
        assertTrue(     ranking2.getFinalTime().doubleValue() <  ranking3.getFinalTime().doubleValue() );
        assertEquals(ranking1.getPosition(),1);
        assertEquals(ranking2.getPosition(),1);
        assertEquals(ranking3.getPosition(),2);

    }


    @DisplayName("Should throw validation exception")
    @ParameterizedTest
    @ValueSource(strings = {INPUT4,INPUT5,INPUT6,INPUT})
    public void shouldThrowValidationException(String input) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + input);
        XmlMapper xmlMapper = new XmlMapper();

        HarryKartRequest request = xmlMapper.readValue(file, HarryKartRequest.class);

        var exceptionThrown = assertThrows(ValidationException.class, () -> rankingService.computeRanking(request));
    }
}
