package com.example.demo.repository.impl;

import com.example.demo.annotation.Column;
import com.example.demo.entities.Benefits;
import com.example.demo.entities.Employee;
import com.example.demo.repository.BenefitsRepository;
import com.example.demo.utils.DatabaseConnection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

public class BenefitsRepositoryImpl implements BenefitsRepository {
    private final Class<Benefits> entityType;
    Connection conn = DatabaseConnection.getConnection();
    public BenefitsRepositoryImpl(Class<Benefits> entityType) {
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
    public Optional<Benefits> findById(Long id) {
        String sql = "SELECT * FROM benefits WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Benefits entity = entityType.getDeclaredConstructor().newInstance();
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
    public Optional<Benefits> findByEmployeeId(Long id) {
        String sql = "SELECT * FROM benefits WHERE employee_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Benefits entity = entityType.getDeclaredConstructor().newInstance();
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
    public Benefits create(Benefits benefits) {
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        for (Field field : benefits.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                columns.add(getColumnName(field));
                placeholders.add("?");
            }
        }
        String sql = "INSERT INTO benefits (" + columns.toString() + ") VALUES (" + placeholders.toString() + ")";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Field field : benefits.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    ps.setObject(index++, field.get(benefits));
                }
            }
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        benefits.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return benefits;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Long id, Benefits benefits) {
        StringJoiner setClause = new StringJoiner(", ");
        for (Field field : benefits.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                if (!field.getName().equalsIgnoreCase("id")) {
                    setClause.add(getColumnName(field) + " = ?");
                }
            }
        }
        String sql = "UPDATE benefits SET " + setClause.toString() + " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (Field field : benefits.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    if (!field.getName().equalsIgnoreCase("id")) {
                        ps.setObject(index++, field.get(benefits));
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
        String sql = "DELETE FROM benefits WHERE id = ?";
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

