import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.OffsetDateTime;
import java.util.*;

@Test
public class TestEquality {

    @Test
    void testEqualityByPropertyUtils(){

        ObjectEqualityCheck objectEqualityCheck = new EqualityByPropertyUtils();
        UUID equalUUID = UUID.randomUUID();
        OffsetDateTime dateTime = OffsetDateTime.now();
        Person person1 = new Person();
        person1.setAge(1);
        person1.setCreationTime(dateTime);
        person1.setUpdateTime(dateTime);
        person1.setUuid(equalUUID);
        person1.setName("Hazan");
        String[] nicks = new String[2];
        nicks[0] = "guy";
        nicks[1] = "hazan";
        person1.setNicknames(nicks);

        Person person2 = new Person();
        person2.setAge(1);
        person2.setCreationTime(dateTime);
        person2.setUpdateTime(dateTime);
        person2.setUuid(equalUUID);
        person2.setName("Hazan");
        String[] nicks2 = new String[2];
        nicks2[0] = "guy";
        nicks2[1] = "hazan";
        person2.setNicknames(nicks2);

        //collection
        Collection<String> favFoodNameCollection = new ArrayList<>();
        favFoodNameCollection.add("mango");
        person1.setStringCollection(favFoodNameCollection);

        Collection<String> favFoodNameCollection2 = new ArrayList<>();
        favFoodNameCollection2.add("mango");
        person2.setStringCollection(favFoodNameCollection2);


        //maps
        Map<String, Person> familyMap = new HashMap<>();
        Person mom = new Person();
        mom.setName("mom");
        familyMap.put("mom", mom);
        person1.setFamilyMap(familyMap);

        Map<String, Person> familyMap2 = new HashMap<>();
        Person mom2 = new Person();
        mom2.setName("mom");
        familyMap2.put("mom", mom2);
        person2.setFamilyMap(familyMap2);

        //Enum
        person1.setGender(Gender.MALE);
        person2.setGender(Gender.MALE);

        //EnumSet
        person1.setFeatures(EnumSet.of(PersonFeatures.BEARDED,PersonFeatures.FAT));
        person2.setFeatures(EnumSet.of(PersonFeatures.BEARDED,PersonFeatures.FAT));

        //optional
        Person partner1 = new Person();
        partner1.setName("Bla");
        Person partner2 = new Person();
        partner2.setName("Bla");

        person1.setPartner(Optional.of(partner1));
        person2.setPartner(Optional.of(partner2));

        long startTime = System.currentTimeMillis();
        boolean result = objectEqualityCheck.checkEquality(person1, person2);
        System.out.println("equality took: " + (System.currentTimeMillis() - startTime) + "millis");

        Assert.assertTrue(result);
    }

    @Test
    void testEqualityByJson(){


        ObjectEqualityCheck objectEqualityCheck = new EqualityByJson();
        UUID equalUUID = UUID.randomUUID();
        OffsetDateTime dateTime = OffsetDateTime.now();
        Person person1 = new Person();
        person1.setAge(1);
        person1.setCreationTime(dateTime);
        person1.setUpdateTime(dateTime);
        person1.setUuid(equalUUID);

        Person person2 = new Person();
        person2.setAge(1);
        person2.setCreationTime(dateTime);
        person2.setUpdateTime(dateTime);
        person2.setUuid(equalUUID);

        long startTime = System.currentTimeMillis();
        boolean result = objectEqualityCheck.checkEquality(person1, person2);
        System.out.println("equality took: " + (System.currentTimeMillis() - startTime) + "millis");

        Assert.assertTrue(result);

    }

}
