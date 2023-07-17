package dto;

import entitiy.Owner;
import entitiy.SparePart;

import java.util.Set;

public class CarDto {

    private Long id;

    private String brand;

    private String model;

    private Owner owner;

    private Set<SparePart> spareParts;

    public CarDto() {
    }

    public CarDto(Long id, String brand, String model, Owner owner, Set<SparePart> spareParts) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.owner = owner;
        this.spareParts = spareParts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
