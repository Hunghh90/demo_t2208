package com.example.demo.repository.impl;

import com.example.demo.annotation.Column;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.utils.DatabaseConnection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final Class<Department> entityType;
    Connection conn = DatabaseConnection.getConnection();
    public DepartmentRepositoryImpl(Class<Department> entityType) {
        this.entityType = entityType;
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
    public Optional<Department> findById(Long id) {
        String sql = "SELECT * FROM departments WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department entity = entityType.getDeclaredConstructor().newInstance();
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
    public boolean update(Long id, Department department) {
        StringJoiner setClause = new StringJoiner(", ");
        for (Field field : department.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                if (!field.getName().equalsIgnoreCase("id")) {
                    setClause.add(getColumnName(field) + " = ?");
                }
            }
        }
        String sql = "UPDATE departments SET " + setClause.toString() + " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (Field field : department.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    if (!field.getName().equalsIgnoreCase("id")) {
                        ps.setObject(index++, field.get(department));
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
}
