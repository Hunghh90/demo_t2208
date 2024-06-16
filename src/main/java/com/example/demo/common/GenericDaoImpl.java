package com.example.demo.common;



import com.example.demo.annotation.Column;
import com.example.demo.annotation.Table;
import com.example.demo.utils.DatabaseConnection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class GenericDaoImpl<T, ID> implements GenericDao<T,ID>{
    private final Class<T> entityType;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.entityType = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Class is not parameterized with generic type");
        }
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
    public Optional<T> findById(ID id) {
        String tableName = getTableName();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    T entity = entityType.getDeclaredConstructor().newInstance();
                    return Optional.of(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAllPaginated(int pageNumber, int pageSize) {
        List<T> entities = new ArrayList<>();
        String tableName = getTableName();
        String sql = "SELECT * FROM " + tableName + " LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (pageNumber - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    T entity = entityType.getDeclaredConstructor().newInstance();
                    for (Field field : entityType.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            field.set(entity, rs.getObject(getColumnName(field)));
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
    public List<T> findBy(String fieldName, Object value) {
        List<T> entities = new ArrayList<>();
        String tableName = getTableName();
        String columnName = null;
        for (Field field : entityType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class) && field.getName().equals(fieldName)) {
                columnName = getColumnName(field);
                break;
            }
        }
        if (columnName == null) {
            throw new IllegalArgumentException("Field " + fieldName + " not found or not annotated with @Column");
        }

        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    T entity = entityType.getDeclaredConstructor().newInstance();
                    for (Field field : entityType.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            field.set(entity, rs.getObject(getColumnName(field)));
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
    public void create(T entity) {
        String tableName = getTableName();
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        for (Field field : entityType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                columns.add(getColumnName(field));
                placeholders.add("?");
            }
        }
        String sql = "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + placeholders.toString() + ")";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (Field field : entityType.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    ps.setObject(index++, field.get(entity));
                }
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity, ID id) {
        String tableName = getTableName();
        StringJoiner setClause = new StringJoiner(", ");
        for (Field field : entityType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                setClause.add(getColumnName(field) + " = ?");
            }
        }
        String sql = "UPDATE " + tableName + " SET " + setClause.toString() + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (Field field : entityType.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    ps.setObject(index++, field.get(entity));
                }
            }
            ps.setObject(index, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ID id) {
        String tableName = getTableName();
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
