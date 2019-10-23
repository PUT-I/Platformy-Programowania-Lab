package com.put.lab3.coffee;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class CoffeeDao {

    private final NamedParameterJdbcTemplate jdbc;

    public CoffeeDao() throws SQLException {
        final DataSource dataSource = DbUtilities.getDataSource(
                "jdbc:mysql://localhost:3306/coffee",
                "root",
                "test123"
        );
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Returns a coffee with given name.
     *
     * @param name coffee name
     * @return coffee object or null
     */
    public Coffee get(String name) {
        String sql = "SELECT SUP_ID, PRICE, SALES, TOTAL FROM COFFEES "
                + "WHERE COF_NAME = :cof_name";
        MapSqlParameterSource params = new MapSqlParameterSource("cof_name", name);
        return jdbc.query(sql, params, rs -> {
            rs.next();
            return new Coffee(
                    name,
                    rs.getInt(1),
                    rs.getBigDecimal(2),
                    rs.getInt(3),
                    rs.getInt(4)
            );
        });

    }

    /**
     * Returns a list of all coffees.
     *
     * @return list of all coffees
     */
    public List<Coffee> getAll() {
        String sql = "SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES";
        try {
            return jdbc.query(sql, (rs, i) -> new Coffee(
                    rs.getString(1),
                    rs.getInt(2),
                    rs.getBigDecimal(3),
                    rs.getInt(4),
                    rs.getInt(5)
            ));
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    private Map<String, Object> coffeeToParameters(Coffee c) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("price", c.getPrice());
        parameters.put("sales", c.getSales());
        parameters.put("total", c.getTotal());
        parameters.put("cof_name", c.getName());
        parameters.put("sup_id", c.getSupplierId());
        return parameters;
    }

    public void update(Coffee c) {
        String sql = "UPDATE COFFEES "
                + "SET PRICE = :price, SALES = :sales, TOTAL = :total "
                + "WHERE COF_NAME = :cof_name AND SUP_ID = :sup_id";
        Map<String, Object> parameters = coffeeToParameters(c);
        jdbc.update(sql, parameters);
    }

    public void delete(String name, int supplierId) {
        final String sql = "DELETE from COFFEES " +
                "WHERE COF_NAME = :cof_name AND SUP_ID = :sup_id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cof_name", name);
        parameters.put("sup_id", supplierId);
        jdbc.update(sql, parameters);
    }

    public void create(Coffee c) {
        final String sql = "INSERT INTO COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL)" +
                "VALUES(:cof_name, :sup_id, :price, :sales, :total)";
        Map<String, Object> parameters = coffeeToParameters(c);
        jdbc.update(sql, parameters);
    }

}
