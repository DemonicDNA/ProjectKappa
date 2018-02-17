package entities;

public class Dog implements Animal {

    private String name;
    private Integer numOfLegs;


    public void bark(){
        System.out.println("bark bark");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOfLegs() {
        return numOfLegs;
    }

    public void setNumOfLegs(Integer numOfLegs) {
        this.numOfLegs = numOfLegs;
    }
}
