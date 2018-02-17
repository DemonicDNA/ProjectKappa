/*
import Delta.Delta;
import entities.Gender;
import entities.Person;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.util.List;

@Test
public class DeltaSerializationTest {

    @Test
    void testSerialization(){

        Person person = new Person();
        person.setName("Guy");
        person.setGender(Gender.MALE);
        person.setAge(22);
        person.setPet(null);
        person.setUpdateTime(null);

        List<Person> family = FamilyFactory.createFamily();
        person.setFamilyMembers(family);


        Delta delta = new Delta();
        delta.addChange("age", Integer.class, 20);
        delta.addChange("updateTime", OffsetDateTime.class, OffsetDateTime.now());

        serialization.DeltaSerializer deltaSerializer = new serialization.DeltaSerializer();
        deltaSerializer.serialize(delta.getDeltaMap());

        //todo: add asserts
    }

}
*/
