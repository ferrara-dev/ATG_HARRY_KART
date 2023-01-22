package se.atg.service.harrykart.java.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
public class RankingServiceTest {

    private static final String INPUT = "input_0.xml";
    private static final String INPUT1 = "input_1.xml";
    private static final String INPUT2 = "input_2.xml";

    private static final String INPUT3 = "input_3.xml";

    private static final String INPUT_WITH_TWO_PARTICIPANTS = "src/test/resources/data/input_with_two_participants.xml";
    private static final String INPUT_WITH_TWO_WINNERS = "src/test/resources/data/input_with_two_winners.xml";

    private RankingService rankingService;

    @BeforeAll
    void setUp() {
        rankingService = new RankingService();
    }


    @DisplayName("Returning top 3 ranking")
    @ParameterizedTest
    @ValueSource(strings = {INPUT,INPUT3})
    public void shouldReturnTopThreeRanking(String input) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + input);
        String xml = Files.readString(file.toPath());
    }


}
