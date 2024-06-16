package com.example.demo.repository.impl;

import com.example.demo.annotation.Column;
import com.example.demo.annotation.Table;
import com.example.demo.utils.DatabaseConnection;
import com.example.demo.entities.Employee;
import com.example.demo.repository.EmployeeRepository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Class<Employee> entityType;
    Connection conn = DatabaseConnection.getConnection();
    public EmployeeRepositoryImpl(Class<Employee> entityType) {
        this.entityType = entityType;
    }

    private String getTableName() {
        if (entityType.isAnnotationPresent(Table.class)) {
            Table table = entityType.getAnnotation(Table.class);
            return table.name();
        } else {
            return entityType.getSimpleName();
        }
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            return column.name();
        } else {
            return field.getName();
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee entity = entityType.getDeclaredConstructor().newInstance();
                    for (Field field : entityType.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            String columnName = getColumnName(field);
                            Object value;
                            if (field.getType().equals(Date.class)) {
                                value = rs.getTimestamp(columnName);
                            } else {
                                value = rs.getObject(columnName);
                            }
                            field.set(entity, value);
                        }
                    }
                    return Optional.of(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll(int pageNumber, int pageSize) {
        List<Employee> entities = new ArrayList<>();
        String sql = "SELECT * FROM employees where status = true LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (pageNumber - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee entity = entityType.getDeclaredConstructor().newInstance();
                    for (Field field : entityType.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            String columnName = getColumnName(field);
                            Object value;
                            if (field.getType().equals(Date.class)) {
                                value = rs.getTimestamp(columnName);
                            } else {
                                value = rs.getObject(columnName);
                            }
                            field.set(entity, value);
                        }
                    }
                    entities.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Optional<Employee> findByIdCard(String idCard) {
        String sql = "SELECT * FROM employees WHERE id_card = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, idCard);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee entity = entityType.getDeclaredConstructor().newInstance();
                    for (Field field : entityType.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            String columnName = getColumnName(field);
                            Object value;
                            if (field.getType().equals(Date.class)) {
                                value = rs.getTimestamp(columnName);
                            } else {
                                value = rs.getObject(columnName);
                            }
                            field.set(entity, value);
                        }
                    }
                    return Optional.of(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Employee create(Employee employee) {
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        for (Field field : employee.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                columns.add(getColumnName(field));
                placeholders.add("?");
            }
        }
        String sql = "INSERT INTO employees (" + columns.toString() + ") VALUES (" + placeholders.toString() + ")";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Field field : employee.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    ps.setObject(index++, field.get(employee));
                }
            }
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Long id,Employee employee) {
        StringJoiner setClause = new StringJoiner(", ");
        for (Field field : employee.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                if (!field.getName().equalsIgnoreCase("id")) {
                    setClause.add(getColumnName(field) + " = ?");
                }
            }
        }
        String sql = "UPDATE employees SET " + setClause.toString() + " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (Field field : employee.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    if (!field.getName().equalsIgnoreCase("id")) {
                        ps.setObject(index++, field.get(employee));
                    }
                }
            }
            ps.setObject(index, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    }

