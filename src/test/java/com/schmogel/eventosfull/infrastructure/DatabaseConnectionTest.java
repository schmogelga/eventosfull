package com.schmogel.eventosfull.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DatabaseConnectionTest {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void testDataSource() {
    assertNotNull(dataSource, "O DataSource deve ser configurado corretamente.");
  }

  @Test
  void testJdbcTemplate() {
    assertNotNull(jdbcTemplate, "O JdbcTemplate deve ser configurado corretamente.");
  }

  @Test
  void testDatabaseConnection() {
    String sql = "SELECT 1";
    Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
    assertNotNull(result, "A conexão com o banco de dados deve retornar um resultado.");
  }
}
