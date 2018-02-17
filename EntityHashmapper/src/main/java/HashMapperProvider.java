import java.util.HashMap;
import java.util.Map;

public class HashMapperProvider {

    public Map<Object, Object> getObjectHashMap() {
        return objectHashMap;
    }

    private Map<Object, Object> objectHashMap = new HashMap<>();

}
