package entitiy;

import java.util.Objects;
import java.util.Set;

public class SparePart extends Entity {

    private String name;

    private String serialNumber;

    private Set<Car> cars;

    public SparePart() {
    }

    public SparePart(Long id, String name, String serialNumber, Set<Car> cars) {
        super(id);
        this.name = name;
        this.serialNumber = serialNumber;
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SparePart sparePart = (SparePart) o;
        return Objects.equals(name, sparePart.name) && Objects.equals(serialNumber, sparePart.serialNumber) && Objects.equals(cars, sparePart.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, serialNumber, cars);
    }
}
