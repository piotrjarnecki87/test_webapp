package com.ebook.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    void add(T entity);
    void update(T entity);
    void delete(int id);
    T getById(int id);
    List<T> getAll();

    T getById(Connection connection, int id) throws SQLException;

    List<T> getAll(Connection connection) throws SQLException;

    void insert(Connection connection, T entity) throws SQLException;

    void update(Connection connection, T entity, int id) throws SQLException;

    void delete(Connection connection, int id) throws SQLException;
}
