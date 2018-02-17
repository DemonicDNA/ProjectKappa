import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityManager;

import java.io.*;

import static org.mockito.Mockito.*;

public class HashmapperModule extends AbstractModule implements Serializable {

    private HashMapperProvider hashMapperProvider = new HashMapperProvider();

    protected void configure() {

    }

    @Provides
    EntityManager generateEntityHashmapper() {
        EntityManager entityHashmapper = mock(EntityManager.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Entity entity = invocationOnMock.getArgument(0);
               // Entity clonedEntity = (Entity)entity.clone();
                hashMapperProvider.getObjectHashMap().put(entity.getId(), entity);
                return null;
            }
        }).when(entityHashmapper).persist(any(Entity.class));

        doAnswer(invocationOnMock -> {
            Entity entity = invocationOnMock.getArgument(0);
            Entity clonedEntity;
            return hashMapperProvider.getObjectHashMap().put(entity.getId(), entity);
        }).when(entityHashmapper).merge(any());

        when(entityHashmapper.find(any(),any())).thenAnswer(invocationOnMock ->
                hashMapperProvider.getObjectHashMap().get(invocationOnMock.getArgument(1)));


        return entityHashmapper;
    }

    @Provides
    HashMapperProvider getHashmapperProvider(){return hashMapperProvider;}
}

