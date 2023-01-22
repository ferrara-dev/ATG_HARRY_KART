package se.atg.service.harrykart.java.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import se.atg.service.harrykart.java.domain.PlayHarryKartResponse;
import se.atg.service.harrykart.java.domain.exception.ApiErrorModel;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
public class HarryKartControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String INPUT_0 = "input_0.xml";
    private static final String INPUT_1 = "input_1.xml";

    @Test
    public void shouldReturnValidationErrorMessage() throws Exception {
        File file = ResourceUtils.getFile("classpath:" + INPUT_0);
        MvcResult mvcResult = mockMvc.perform(post("/java/api/play")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(Files.toByteArray(file)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        ApiErrorModel responseBody = objectMapper.readValue(response, ApiErrorModel.class);
        Assertions.assertEquals(responseBody.getDetails(), "VALIDATION_EXCEPTION");
    }

    @Test
    public void shouldReturnTop3RankingMessage() throws Exception {
        File file = ResourceUtils.getFile("classpath:" + INPUT_1);
        MvcResult mvcResult = mockMvc.perform(post("/java/api/play")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(Files.toByteArray(file)))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotNull(response);
        ObjectMapper objectMapper = new ObjectMapper();
        PlayHarryKartResponse responseBody = objectMapper.readValue(response, PlayHarryKartResponse.class);
        Assertions.assertNotNull(responseBody);
    }
}
