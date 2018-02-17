package deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import delta.Delta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeltaDeserializer extends JsonDeserializer<Delta> {

    private final ObjectMapper objectMapper;
    @Inject
    public DeltaDeserializer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @Override
    public Delta deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final TreeNode deltaAsTree = jsonParser.readValueAsTree();
        Map<String, Object> propertyValueMap = new HashMap<>();
        deltaAsTree.fieldNames().forEachRemaining(s -> {
            try {
                if(!s.contains("Class")) {
                    propertyValueMap.put(s, objectMapper.readValue(deltaAsTree.get(s).toString(),
                            objectMapper.readValue(deltaAsTree.get(s + "Class").toString(), JavaType.class)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return new Delta(propertyValueMap);
    }
}
