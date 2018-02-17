package deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeltaDeserializer implements Deserializer<String, Map<String,Object>>{

    private final ObjectMapper objectMapper;


    @Inject
    DeltaDeserializer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> deserialize(String serializedDelta) {
        Map<String, Object> fieldNameToObjectMap = new HashMap<>();
        try {
            Map<String, Map.Entry<String, Class<?>>> propertyToJsonMap =
                    objectMapper.readValue(serializedDelta, new TypeReference<Map<String, Map.Entry<String, Class<?>>>>() {});

            propertyToJsonMap.forEach((propertyName, jsonRepToClassEntry) -> {
                try {
                    fieldNameToObjectMap.put(propertyName, objectMapper.readValue(jsonRepToClassEntry.getKey(), jsonRepToClassEntry.getValue()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fieldNameToObjectMap;
    }
}
