package dao;

import entitiy.Owner;

import java.sql.SQLException;
import java.util.List;

public interface OwnerDao {

    void create(Owner owner) throws SQLException;

    Owner readById(Long id) throws SQLException;

    List<Owner> readAll() throws SQLException;

    void update(Owner owner) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
