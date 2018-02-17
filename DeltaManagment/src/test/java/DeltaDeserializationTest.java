import com.google.inject.Guice;
import deserialization.DeltaDeserializer;
import entities.Gender;
import entities.Person;
import modules.JsonParsingModule;
import org.apache.commons.beanutils.BeanUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import serialization.DeltaSerializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeltaDeserializationTest {


    private DeltaSerializer deltaSerializer = Guice.createInjector(new JsonParsingModule()).getInstance(DeltaSerializer.class);
    private DeltaDeserializer deltaDeserializer = Guice.createInjector(new JsonParsingModule()).getInstance(DeltaDeserializer.class);


    @Test
    void testDeserialization() throws InvocationTargetException, IllegalAccessException, IOException {
        Person person = new Person();
        person.setName("Guy");
        person.setGender(Gender.MALE);
        person.setAge(22);
        person.setPet(null);
        person.setUpdateTime(null);
        List<Person> family = FamilyFactory.createFamily();
        person.setFamilyMembers(family);

        Map<String, Object> deltaMap = new HashMap<>();
        deltaMap.put("age", 30);
        OffsetDateTime testTime = OffsetDateTime.now();
        deltaMap.put("updateTime", testTime);
        deltaMap.put("gender", Gender.FEMALE);

        String mapToSend = deltaSerializer.serialize(deltaMap);
        Map<String, Object> deserializeMap;

        deserializeMap = deltaDeserializer.deserialize(mapToSend);
        Assert.assertEquals(deserializeMap.get("updateTime"), testTime);
        Assert.assertEquals(deserializeMap.get("age"), 30);
        Assert.assertEquals(deserializeMap.get("gender"), Gender.FEMALE);

        BeanUtils.populate(person, deserializeMap);
        Assert.assertTrue(person.getAge() == 30);
        Assert.assertEquals(person.getUpdateTime(), testTime);
        Assert.assertEquals(person.getGender(), Gender.FEMALE);
    }
}
