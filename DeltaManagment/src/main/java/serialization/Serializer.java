package serialization;

import java.util.Map;

public interface Serializer<IncomingType, OutgoingType> {
    public OutgoingType serialize(IncomingType propertyToObjectMap);

    }
