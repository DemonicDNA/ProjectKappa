package serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import delta.Delta;

import java.io.IOException;

public class DeltaSerializer extends StdSerializer<Delta>  {

    public DeltaSerializer(Class<Delta> t) {
        super(t);
    }

    @Override
    public void serialize(Delta delta, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        delta.getPropertyToValueMap().forEach((s, o) -> {
            try {
                jsonGenerator.writeFieldName(s);
                jsonGenerator.writeObject(o);
                jsonGenerator.writeFieldName(s + "Class");
                jsonGenerator.writeObject(serializerProvider.constructType(o.getClass()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonGenerator.writeEndObject();
    }
}
