import com.google.gson.GsonBuilder;

import java.io.Serializable;

public interface Entity<ID> extends Serializable{
    ID getId();

    default public String gsonize(){
        GsonBuilder builder = new GsonBuilder();
        return builder.create().toJson(this);
    }
    //Object clone() throws CloneNotSupportedException;
}
