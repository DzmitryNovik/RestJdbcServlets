package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.SparePartDao;
import dto.SparePartDto;
import entitiy.SparePart;
import mapper.SparePartMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SparePartService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private SparePartDao sparePartDao;
    private SparePartMapper sparePartMapper;

    public SparePartService(SparePartDao sparePartDao, SparePartMapper sparePartMapper) {
        this.sparePartDao = sparePartDao;
        this.sparePartMapper = sparePartMapper;
    }

    public Optional<String> handleGetRequest(String parameter) throws SQLException {
        if (parameter == null) {
            List<SparePartDto> owners = sparePartDao.readAll().stream().map(owner -> {
                return sparePartMapper.toSparePartDto(owner);
                //return null;
            }).collect(Collectors.toList());
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(owners));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            Long id = Long.parseLong(parameter);
            SparePart sparePart = sparePartDao.readById(id);
            SparePartDto sparePartDto = sparePartMapper.toSparePartDto(sparePart);
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(sparePartDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void handlePostRequest(HttpServletRequest req) throws SQLException {
        SparePartDto sparePartDto = getSparePartDtoFromRequestBody(req);
        sparePartDao.create(sparePartMapper.toSparePart(sparePartDto));
    }

    public void handlePutRequest(HttpServletRequest req)  throws SQLException {
        SparePartDto sparePartDto = getSparePartDtoFromRequestBody(req);
        sparePartDao.update(sparePartMapper.toSparePart(sparePartDto));
    }

    public void handleDeleteRequest(HttpServletRequest req) throws SQLException {
        Long id = Long.parseLong(req.getParameter("id"));
        sparePartDao.deleteById(id);
    }

    private SparePartDto getSparePartDtoFromRequestBody(HttpServletRequest req) {
        String reqBody = null;
        try {
            reqBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(reqBody, SparePartDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
