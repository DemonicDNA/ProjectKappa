package delta;

import java.util.Collections;
import java.util.Map;

public class Delta {

    private Map<String, Object> propertyToValueMap;

    public Map<String, Object> getPropertyToValueMap() {
        return Collections.unmodifiableMap(propertyToValueMap);
    }
    public Delta(Map<String, Object> propertyToValueMap) {
        this.propertyToValueMap = propertyToValueMap;
    }
}
