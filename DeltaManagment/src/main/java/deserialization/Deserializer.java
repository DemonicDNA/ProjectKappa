package deserialization;

import java.util.Map;

public interface Deserializer<IncomingType, OutgoingType> {
    public OutgoingType deserialize (IncomingType incomingData);
    }
