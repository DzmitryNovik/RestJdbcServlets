package mapper;

import dao.CarDao;
import dto.SparePartDto;
import entitiy.SparePart;

public class SparePartMapper {

    private CarDao carDao;

    public SparePartMapper(CarDao carDao) {
        this.carDao = carDao;
    }

    public SparePartDto toSparePartDto(SparePart sparePart) {
        SparePartDto sparePartDto = new SparePartDto();
        sparePartDto.setId(sparePart.getId());
        sparePartDto.setName(sparePart.getName());
        sparePartDto.setSerialNumber(sparePart.getSerialNumber());
        sparePartDto.setCars(sparePart.getCars());
        return sparePartDto;
    }

    public SparePart toSparePart(SparePartDto sparePartDto) {
        SparePart sparePart = new SparePart();
        sparePart.setId(sparePartDto.getId());
        sparePart.setName(sparePartDto.getName());
        sparePart.setSerialNumber(sparePartDto.getSerialNumber());
        sparePart.setCars(null);
        return sparePart;
    }
}
