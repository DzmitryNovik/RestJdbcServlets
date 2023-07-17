package entitiy;

import java.util.List;
import java.util.Objects;

public class Owner extends Entity {

    private String firstName;

    private String lastName;

    private List<Car> cars;

    public Owner() {
    }

    public Owner(Long id, String firstName, String lastName, List<Car> cars) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cars = cars;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return Objects.equals(firstName, owner.firstName) && Objects.equals(lastName, owner.lastName) && Objects.equals(cars, owner.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, cars);
    }
}
