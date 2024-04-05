package com.ebook.dao;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJdbcDao<T> implements GenericDao<T> {

    public abstract void rentBook(int userId, int bookId, Date rentalDate) throws SQLException;

    public abstract void deleteBookRental(int bookId) throws SQLException;



    public abstract void updateReturnDate(int rentalsId, Date returnDate) throws SQLException;

    protected abstract String getTableName();
    protected abstract String getIdColumnName();
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    @Override
    public T getById(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEntity(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<T> getAll(Connection connection) throws SQLException {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                T entity = mapResultSetToEntity(resultSet);
                entities.add(entity);
            }
        }
        return entities;
    }

    @Override
    public void insert(Connection connection, T entity) throws SQLException {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder columnNames = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value", e);
            }

            if (value != null) {
                columnNames.append(columnName).append(",");
                values.append("?,");
            }
        }

        if (columnNames.length() > 0) {
            columnNames.deleteCharAt(columnNames.length() - 1);
            values.deleteCharAt(values.length() - 1);
        }

        String query = "INSERT INTO " + getTableName() + " (" + columnNames + ") VALUES (" + values + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field value", e);
                }

                if (value != null) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Connection connection, T entity, int id) throws SQLException {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder updateClause = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value", e);
            }

            if (value != null) {
                updateClause.append(columnName).append("=?,");
            }
        }

        if (updateClause.length() > 0) {
            updateClause.deleteCharAt(updateClause.length() - 1);
        }

        String query = "UPDATE " + getTableName() + " SET " + updateClause + " WHERE " + getIdColumnName() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field value", e);
                }

                if (value != null) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }
            preparedStatement.setInt(parameterIndex, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
