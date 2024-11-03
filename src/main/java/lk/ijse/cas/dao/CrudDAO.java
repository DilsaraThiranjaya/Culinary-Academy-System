package lk.ijse.cas.dao;


import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public boolean save(T entity) throws SQLException, ClassNotFoundException;

    public boolean isAvailable(T entity) throws SQLException, ClassNotFoundException;

    public String getNextId() throws SQLException, ClassNotFoundException;

    public boolean update(T entity) throws SQLException, ClassNotFoundException;

    public boolean remove(T entity) throws SQLException, ClassNotFoundException;

    public T searchById(T entity) throws SQLException, ClassNotFoundException;

    public boolean isExist(T entity) throws SQLException, ClassNotFoundException;

    public List<T> getAll() throws SQLException, ClassNotFoundException;
}
