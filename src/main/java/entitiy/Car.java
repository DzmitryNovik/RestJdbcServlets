package entitiy;

import java.util.Objects;
import java.util.Set;

public class Car extends Entity {

    private String brand;

    private String model;

    private Owner owner;

    private Set<SparePart> spareParts;

    public Car() {
    }

    public Car(Long id, String brand, String model, Owner owner, Set<SparePart> spareParts) {
        super(id);
        this.brand = brand;
        this.model = model;
        this.owner = owner;
        this.spareParts = spareParts;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Set<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(Set<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(owner, car.owner) && Objects.equals(spareParts, car.spareParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brand, model, owner, spareParts);
    }
}
