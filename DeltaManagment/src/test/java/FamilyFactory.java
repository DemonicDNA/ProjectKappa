import entities.Gender;
import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class FamilyFactory {
    public static List<Person> createFamily() {
        Person meytal = new Person();
        meytal.setName("Meytal");
        meytal.setAge(8);
        meytal.setGender(Gender.FEMALE);
        meytal.setPet(null);

        List<Person> family = new ArrayList<>();
        family.add(meytal);
        return family;
    }
}
