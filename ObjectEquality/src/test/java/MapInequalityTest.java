import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

@Test
public class MapInequalityTest {

    @Test
    void testInEqualityByPropertyUtilsOfMapsValuesInObject(){

        ObjectEqualityCheck objectEqualityCheck = new EqualityByPropertyUtils();
        Person person1 = new Person();
        Person person2 = new Person();

        //maps
        Map<String, Person> familyMap1 = new HashMap<>();
        Person mom = new Person();
        mom.setName("mom");
        familyMap1.put("mom", mom);

        Map<String, Person> familyMap2 = new HashMap<>();
        Person mom2 = new Person();
        mom2.setName("mom12345");
        familyMap2.put("mom", mom2);

        person1.setFamilyMap(familyMap1);
        person2.setFamilyMap(familyMap2);

        long startTime = System.currentTimeMillis();
        boolean equality = objectEqualityCheck.checkEquality(person1, person2);
        System.out.println("equality took: " + (System.currentTimeMillis() - startTime) + "millis");

        Assert.assertTrue(!equality);
    }

    @Test
    void testInEqualityByPropertyUtilsOfMapsKeysInObject(){

        ObjectEqualityCheck objectEqualityCheck = new EqualityByPropertyUtils();
        Person person1 = new Person();
        Person person2 = new Person();
        //maps
        Map<String, Person> familyMap1 = new HashMap<>();
        Person mom = new Person();
        mom.setName("mom");
        familyMap1.put("mom", mom);

        Map<String, Person> familyMap2 = new HashMap<>();
        Person mom2 = new Person();
        mom2.setName("mom");
        familyMap2.put("mom123", mom2);

        person1.setFamilyMap(familyMap1);
        person2.setFamilyMap(familyMap2);

        boolean equality = objectEqualityCheck.checkEquality(person1, person2);

        Assert.assertTrue(!equality);
    }

    @Test
    void testInEqualityByPropertyUtilsOfMapsValues(){

        ObjectEqualityCheck objectEqualityCheck = new EqualityByPropertyUtils();

        //maps
        Map<String, Person> familyMap1 = new HashMap<>();
        Person mom = new Person();
        mom.setName("mom");
        familyMap1.put("mom", mom);

        Map<String, Person> familyMap2 = new HashMap<>();
        Person mom2 = new Person();
        mom2.setName("mom12345");
        familyMap2.put("mom", mom2);


        long startTime = System.currentTimeMillis();
        boolean equality = objectEqualityCheck.checkEquality(familyMap1, familyMap2);
        System.out.println("equality took: " + (System.currentTimeMillis() - startTime) + "millis");

        Assert.assertTrue(!equality);
    }

    @Test
    void testInEqualityByPropertyUtilsOfMapsKeys(){

        ObjectEqualityCheck objectEqualityCheck = new EqualityByPropertyUtils();

        //maps
        Map<String, Person> familyMap1 = new HashMap<>();
        Person mom = new Person();
        mom.setName("mom");
        familyMap1.put("mom", mom);

        Map<String, Person> familyMap2 = new HashMap<>();
        Person mom2 = new Person();
        mom2.setName("mom");
        familyMap2.put("mom123", mom2);


        long startTime = System.currentTimeMillis();
        boolean equality = objectEqualityCheck.checkEquality(familyMap1, familyMap2);
        System.out.println("equality took: " + (System.currentTimeMillis() - startTime) + "millis");

        Assert.assertTrue(!equality);
    }

}
