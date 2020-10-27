package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config.ApiError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDateTime;

public class SuperControllerTest {
    @Autowired
    protected MockMvc mvc;
    ObjectMapper objectMapper;
    /**
     * Initializes the mock of web service resources.
     */
    @BeforeEach
    protected void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerSubtypes(Data.class);
        objectMapper.registerSubtypes(LocalDateTime.class);
    }

    /**
     * Used to transform objects into json.
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Used to map objects sent by the service.
     * @param json object json
     * @param clazz class to be transformed into object
     * @param <T>
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(json, clazz);
    }


    /**
     * Check api error attr
     * @param error
     * @param expectedException
     */
    public void checkAttrApiError(ApiError error, String expectedException){
        if(error != null){
            Assertions.assertEquals(expectedException, error.getMessage());
        } else {
            Assertions.fail("Object is null!");
        }
    }

    /**
     * Check api error attr OR
     * @param error
     * @param expectedException
     */
    public void checkAttrApiErrorOr(ApiError error, String [] expectedException){
        if(error != null){
            Assertions.assertEquals(expectedException, error.getMessage());
        } else {
            Assertions.fail("Object is null!");
        }
    }
}
