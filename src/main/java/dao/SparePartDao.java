package dao;

import entitiy.Owner;
import entitiy.SparePart;

import java.sql.SQLException;
import java.util.List;

public interface SparePartDao {

    void create(SparePart sparePart) throws SQLException;

    SparePart readById(Long id) throws SQLException;

    List<SparePart> readAll() throws SQLException;

    void update(SparePart sparePart) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
