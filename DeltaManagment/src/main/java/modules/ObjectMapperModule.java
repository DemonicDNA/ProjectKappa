package modules;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ObjectMapperModule extends AbstractModule {
    @Override
    protected void configure() {
    }
    @Provides
    ObjectMapper provideConfiguredObjectMapper(){
        return new ObjectMapper().registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
