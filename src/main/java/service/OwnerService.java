package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OwnerDao;
import dto.OwnerDto;
import entitiy.Owner;
import mapper.OwnerMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OwnerService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private OwnerDao ownerDao;
    private OwnerMapper ownerMapper;

    public OwnerService(OwnerDao ownerDao, OwnerMapper ownerMapper) {
        this.ownerDao = ownerDao;
        this.ownerMapper = ownerMapper;
    }

    public Optional<String> handleGetRequest(String parameter) throws SQLException {
        if (parameter == null) {
            List<OwnerDto> owners = ownerDao.readAll().stream().map(owner -> {
                return ownerMapper.toOwnerDto(owner);
            }).collect(Collectors.toList());
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(owners));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            Long id = Long.parseLong(parameter);
            Owner owner = ownerDao.readById(id);
            OwnerDto ownerDto = ownerMapper.toOwnerDto(owner);
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(ownerDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void handlePostRequest(HttpServletRequest req) throws SQLException {
        OwnerDto ownerDto = getOwnerDtoFromRequestBody(req);
        ownerDao.create(ownerMapper.toOwner(ownerDto));
    }

    public void handlePutRequest(HttpServletRequest req) throws SQLException {
        OwnerDto ownerDto = getOwnerDtoFromRequestBody(req);
        ownerDao.update(ownerMapper.toOwner(ownerDto));
    }

    public void handleDeleteRequest(HttpServletRequest req) throws  SQLException {
        Long ownerId = Long.parseLong(req.getParameter("id"));
        ownerDao.deleteById(ownerId);

    }

    private OwnerDto getOwnerDtoFromRequestBody(HttpServletRequest req) {
        String reqBody = null;
        try {
            reqBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(reqBody, OwnerDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
