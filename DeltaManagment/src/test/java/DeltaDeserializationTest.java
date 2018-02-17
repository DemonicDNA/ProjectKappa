import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Guice;
import delta.Delta;
import deserialization.DeltaDeserializer;
import entities.Gender;
import entities.Person;
import entities.PersonFeature;
import modules.ObjectMapperModule;
import org.apache.commons.beanutils.BeanUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import serialization.DeltaSerializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.OffsetDateTime;
import java.util.*;

public class DeltaDeserializationTest {

    private ObjectMapper mapper = Guice.createInjector(new ObjectMapperModule()).getInstance(ObjectMapper.class);

    @Test
    void testDeserialization() throws InvocationTargetException, IllegalAccessException, IOException {

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Delta.class, new DeltaSerializer(Delta.class));
        simpleModule.addDeserializer(Delta.class, new DeltaDeserializer(mapper));
        mapper.registerModule(simpleModule);

        Person person = new Person();
        person.setName("Guy");
        person.setGender(Gender.MALE);
        person.setAge(22);
        person.setPet(null);
        person.setUpdateTime(null);
        List<Person> family = FamilyFactory.createFamily();
        person.setFamilyMembers(family);
        person.setFeatures(EnumSet.allOf(PersonFeature.class));

        Map<String, Object> deltaMap = new HashMap<>();
        deltaMap.put("age", 30);
        OffsetDateTime testTime = OffsetDateTime.now();
        deltaMap.put("updateTime", testTime);
        deltaMap.put("gender", Gender.FEMALE);
        List<Person> family2 = new ArrayList<>();
        family2.add(new Person());
        deltaMap.put("family", family2);
        deltaMap.put("features", EnumSet.of(PersonFeature.FAT));

        Delta delta = new Delta(deltaMap);
        Delta deserializedDelta = mapper.readValue(mapper.writeValueAsString(delta), Delta.class);
        Map<String,?> deserializeMap = deserializedDelta.getPropertyToValueMap();

        Assert.assertEquals(deserializeMap.get("updateTime"), testTime);
        Assert.assertEquals(deserializeMap.get("age"), 30);
        Assert.assertEquals(deserializeMap.get("gender"), Gender.FEMALE);
        //todo: enable after adding support for collections
        //Assert.assertTrue(new Gson().toJson(deserializeMap.get("family")).equals(new Gson().toJson(family2)));

        //todo: enable after adding support for enumset
        //Assert.assertEquals(deserializeMap.get("features"), EnumSet.of(PersonFeature.FAT));

        BeanUtils.populate(person, deserializeMap);
        Assert.assertTrue(person.getAge() == 30);
        Assert.assertEquals(person.getUpdateTime(), testTime);
        Assert.assertEquals(person.getGender(), Gender.FEMALE);
        //todo: enable after adding support for collections
        //Assert.assertTrue(new Gson().toJson(person.getFamilyMembers()).equals(new Gson().toJson(family2)));

        //todo: enable after adding support for enumset
        //Assert.assertEquals(person.getFeatures(), EnumSet.of(PersonFeature.FAT));


    }
}
