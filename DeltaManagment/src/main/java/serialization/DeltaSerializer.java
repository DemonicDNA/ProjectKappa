package serialization;

import jsonproviders.JsonRepresentationProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class DeltaSerializer implements Serializer<Map<String, ?>,String> {

    private final JsonRepresentationProvider jsonRepresentationProvider;

    @Inject
    DeltaSerializer(JsonRepresentationProvider jsonRepresentationProvider){
        this.jsonRepresentationProvider = jsonRepresentationProvider;
    }

    public String serialize(Map<String, ?> propertyToObjectMap) {
        Map<String, Map.Entry<String, Class<?>>> fieldNameToJsonRep = new HashMap<>();
        propertyToObjectMap.keySet().forEach(fieldName -> {
            Object updatedValue = propertyToObjectMap.get(fieldName);
            fieldNameToJsonRep.put(fieldName,
                    new AbstractMap.SimpleEntry<>(jsonRepresentationProvider.toJson(updatedValue),updatedValue.getClass()));
        });

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(fieldNameToJsonRep);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
