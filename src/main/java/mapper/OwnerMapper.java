package mapper;

import dao.CarDao;
import dto.OwnerDto;
import entitiy.Owner;

public class OwnerMapper {

    private CarDao carDao;

    public OwnerMapper(CarDao carDao) {
        this.carDao = carDao;
    }

    public OwnerDto toOwnerDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setFirstName(owner.getFirstName());
        ownerDto.setLastName(owner.getLastName());
        ownerDto.setCars(owner.getCars());
        return ownerDto;
    }

    public Owner toOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setFirstName(ownerDto.getFirstName());
        owner.setLastName(ownerDto.getLastName());
        owner.setCars(null);
        return owner;
    }
}
