package com.github.gradusovartem.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Класс реализует слой Dao для доступа к базе данных
 */
public class OperationDaoDB implements Dao {
    ObjectMapper objectMapper = SingleObjectMapper.getInstance();

    /**
     * Метод реализует подключение к базе данных и получение объекта по id
     * @param id - параметр Integer
     * @return возвращает объект класса Operation или null
     */
    @Override
    public Operation get(int id) {
        try (Connection conn = new JDBC().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM operations WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            JSONObject json = null;

            if (rs.next()) {
                json = convert(rs);
            }
            System.out.println(json.toString());
            Operation operation = objectMapper.readValue(json.toString(), Operation.class);
            return operation;
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            return null;
        }

        return null;
    }

    /**
     * Метод реализует подключение к базе данных и получение всех данных, которые находятся в базе данных
     * @return возвращает коллекцию элементов Operation или null
     */
    @Override
    public Collection getAll() {
        try (Connection conn = new JDBC().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM operations");
            ResultSet rs = stmt.executeQuery();
            JSONArray jsonArray = new JSONArray();

            while (rs.next()) {
                JSONObject json = convert(rs);
                jsonArray.put(json);
            }
            System.out.println(jsonArray.toString());
            Operation[] operation = objectMapper.readValue(jsonArray.toString(), Operation[].class);
            return Arrays.asList(operation);
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Метод реализует подключение к базе данных и добавление нового объекта в базу данных
     * @param t - параметр Operation
     * @return возвращает булево значение
     */
    @Override
    public boolean add(Operation t) {
        try (Connection conn = new JDBC().getConnection()) {
            String sql = "INSERT INTO Operations(id, comment, dt_operation, oper_1, oper_2, operation, result) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, t.getId());
                pstmt.setString(2, t.getComment());
                pstmt.setString(3, String.valueOf(t.getDt_operation()));
                pstmt.setInt(4, t.getOper_1());
                pstmt.setInt(5, t.getOper_2());
                pstmt.setString(6, t.getOperation());
                pstmt.setInt(7, t.getResult());
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод реализует подключение к базе данных и обновление объекта по id
     * @param id - параметр Integer
     * @param comment - параметр Integer
     * @return возвращает булево значение
     */
    @Override
    public boolean update(int id, String comment) {
        try (Connection conn = new JDBC().getConnection()) {
            String sql = "UPDATE operations SET comment = ? WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, comment);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод реализует подключение к базе данных и удаление объекта по id
     * @param id - параметр Integer
     * @return возвращает будево значение
     */
    @Override
    public boolean delete(int id) {
        try (Connection conn = new JDBC().getConnection()) {
            String sql = "DELETE FROM operations WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод преобразует полученные данные в формат JSON
     * @param rs - ResultSet полученный из БД
     * @return возвращает объект класса JSONObject
     * @throws SQLException
     * @throws JSONException
     */
    public static JSONObject convert(ResultSet rs) throws SQLException, JSONException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();
        JSONObject obj = new JSONObject();

        for (int i = 1; i < numColumns + 1; i++) {
            String column_name = rsmd.getColumnName(i);

            if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                obj.put(column_name, rs.getInt(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR || rsmd.getColumnType(i) == java.sql.Types.CHAR) {
                obj.put(column_name, rs.getString(column_name));
            } else {
                obj.put(column_name, rs.getObject(column_name));
            }
        }
        return obj;
    }
}
