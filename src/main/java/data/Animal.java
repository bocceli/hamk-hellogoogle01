package data;

public class Animal {
    private int id;
    private String breed;
    private int weight;
    private int age;
    private String name;

    public Animal() {
        super();
    }

    public Animal(int id, String breed, int weight, int age, String name) {
        super();
        this.id = id;
        this.breed = breed;
        this.weight = weight;
        this.age = age;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", breed='" + breed + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
