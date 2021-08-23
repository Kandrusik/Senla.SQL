package tests;

import dataBaseConnect.ConnectionDB;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing the connection to the world DataBase and sending requests")
public class TestDatabase extends TestSetups {

    @Test
    @Order(1)
    @DisplayName("Creating a table in the database")
    public void testCreateTable() {
        String query = "CREATE TABLE car_showroom ("
                + "ID int(6) NOT NULL,"
                + "BRAND VARCHAR(45) NOT NULL,"
                + "MODEL VARCHAR(45) NOT NULL,"
                + "YEAR int(4) NOT NULL,"
                + "PRICE int(6) NOT NULL,"
                + "PRIMARY KEY (id))";
        ConnectionDB.createTable(query);
    }

    @Test
    @Order(2)
    @DisplayName("Sending INSERT query")
    public void testInsertRequest() {
        String query = "INSERT INTO car_showroom (ID, BRAND, MODEL, YEAR, PRICE) VALUES (7, 'NISSAN', 'PATROL', '2015', '17000')";
        ConnectionDB.insertIntoTable(query);

        String selectQuery = "SELECT * FROM car_showroom";
        ResultSet rs = ConnectionDB.selectFromTable(selectQuery);
        assertAll("Should return inserted data",
                () -> assertEquals("7", rs.getString("ID")),
                () -> assertEquals("NISSAN", rs.getString("BRAND")),
                () -> assertEquals("PATROL", rs.getString("MODEL")),
                () -> assertEquals("2015", rs.getString("YEAR")),
                () -> assertEquals("17000", rs.getString("PRICE")));
    }

    @Test
    @Order(3)
    @DisplayName("Sending UPDATE query")
    public void testUpdateRequest() throws SQLException {
        String query = "UPDATE car_showroom SET YEAR = '2013' WHERE ID='7'";
        ConnectionDB.updateInTable(query);
        String selectQuery = "SELECT YEAR FROM car_showroom WHERE ID=7";
        ResultSet rs = ConnectionDB.selectFromTable(selectQuery);
        String expectedTown = "2013";
        String actualTown = rs.getString("YEAR");
        assertEquals(expectedTown, actualTown, "Actual year is '" + actualTown + "'. Expected - '" + expectedTown + "'.");
    }

    @Test
    @Order(4)
    @DisplayName("Sending SELECT query. Checking the address")
    public void testSelectRequest_checkAddress() throws SQLException {
        String query = "SELECT * FROM car_showroom WHERE ID=7";
        ResultSet rs = ConnectionDB.selectFromTable(query);
        String expectedAddress = "PATROL";
        String actualAddress = rs.getString("MODEL");
        assertEquals(expectedAddress, actualAddress, "Actual address is '" + actualAddress + "'. Expected - '" + expectedAddress + "'.");
    }

    @Test
    @Order(5)
    @DisplayName("Sending DELETE request")
    public void testDeleteRequest() {
        String query = "DELETE FROM car_showroom WHERE ID=7";
        ConnectionDB.deleteFromTable(query);
    }

    @Test
    @Order(6)
    @DisplayName("Checking delete the table from the DataBase")
    public void test_dropTable() {
        ConnectionDB.dropTable("car_showroom");
    }

    @Test
    @Order(7)
    @DisplayName("Sending SELECT JOIN query. Checking the belonging of the city of the country")
    public void testSelectWithJoinRequest() throws SQLException {
        String query = "SELECT country.name, countrylanguage.language FROM country LEFT JOIN countrylanguage ON country.Code = countrylanguage.CountryCode WHERE Code='ESP'";
        ResultSet rs = ConnectionDB.selectFromTable(query);
        String expectedCountry = "Spain";
        String actualCountry = rs.getString("name");
        assertEquals(expectedCountry, actualCountry, "Actual country is '" + actualCountry + "'. Expected - '" + expectedCountry + "'.");
    }
}