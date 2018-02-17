import java.time.OffsetDateTime;
import java.util.*;

public class Person {

    private Integer age;
    private OffsetDateTime updateTime;
    private OffsetDateTime creationTime;
    private UUID uuid;
    private String name;
    private String[] nicknames;
    private Map<String, Person> familyMap;
    private Gender gender;
    private EnumSet<PersonFeatures> features;
    private Optional<Person> partner;


    public void setStringCollection(Collection<String> stringCollection) {
        this.stringCollection = stringCollection;
    }

    private Collection<String> stringCollection;

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(OffsetDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Collection<String> getStringCollection() {
        return stringCollection;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String[] getNicknames() {
        return nicknames;
    }

    public void setNicknames(String[] nicknames) {
        this.nicknames = nicknames;
    }

    public Map<String, Person> getFamilyMap() {
        return familyMap;
    }

    public void setFamilyMap(Map<String, Person> familyMap) {
        this.familyMap = familyMap;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFeatures(EnumSet<PersonFeatures> features) {
        this.features = features;
    }

    public EnumSet<PersonFeatures> getFeatures() {
        return features;
    }

    public Optional<Person> getPartner() {
        return partner;
    }

    public void setPartner(Optional<Person> partner) {
        this.partner = partner;
    }
}
