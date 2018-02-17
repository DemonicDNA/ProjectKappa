package jsonproviders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class JacksonJsonProvider implements JsonRepresentationProvider {

    private final ObjectMapper objectMapper;

    @Inject
    public JacksonJsonProvider(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
