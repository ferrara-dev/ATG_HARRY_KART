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
import org.xml.sax.SAXException;
import se.atg.service.harrykart.java.domain.exception.HarryKartException;
import se.atg.service.harrykart.java.domain.HarryKartRequest;
import se.atg.service.harrykart.java.domain.PlayHarryKartResponse;
import se.atg.service.harrykart.java.domain.exception.ValidationException;
import se.atg.service.harrykart.java.service.RankingService;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/java/api")
@RequiredArgsConstructor
public class HarryKartController {
    private static final Logger logger = LoggerFactory.getLogger(HarryKartController.class);

    @Autowired
    private RankingService rankingService;

    @PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayHarryKartResponse> playHarryKart(@RequestBody String XMLRequest) {

        if (!validateXMLSchema(XMLRequest))
            throw new ValidationException("Failed to validate XML request body");

        Optional<HarryKartRequest> req =  parseRequest(XMLRequest);
        if(req.isEmpty()){
            throw new HarryKartException("Could not parse request body", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        HarryKartRequest request = parseRequest(XMLRequest).get();
        return ResponseEntity.ok(rankingService.computeRanking(request));
    }


    public static Optional<HarryKartRequest> parseRequest(String xmlContent) {
        try {
            JAXBContext jContext = JAXBContext.newInstance(HarryKartRequest.class);
            Unmarshaller jaxbUnmarshaller = jContext.createUnmarshaller();
            XMLStreamReader reader = XMLInputFactory.newInstance()
                    .createXMLStreamReader(new StringReader(xmlContent));
            return Optional.of(jaxbUnmarshaller.unmarshal(reader, HarryKartRequest.class).getValue());
        } catch (Exception e) {
            logger.error("could not parse xml request body : " + e.getMessage());
            return Optional.empty();
        }
    }


    public static boolean validateXMLSchema(String xmlContent) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File file = ResourceUtils.getFile("classpath:input.xsd");

            Schema schema = factory.newSchema(file);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8))));
            return true;
        } catch (IOException | SAXException e) {
            logger.error("Failed to validate XML against XSD schema : " + e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }
}
