import com.google.gson.Gson;

public class EqualityByJson implements ObjectEqualityCheck{
    @Override
    public boolean checkEquality(Object object1, Object object2) {
        String object1AsJsonString =  new Gson().toJson(object1);
        String object2AsJsonString =  new Gson().toJson(object2);
        return object1AsJsonString.equals(object2AsJsonString);
    }
}
