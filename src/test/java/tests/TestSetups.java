package tests;

import dataBaseConnect.ConnectionDB;
import org.junit.jupiter.api.*;
import utils.Log;

public class TestSetups {

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        Log.info("------- Started test: " + testInfo.getDisplayName() + " -------");
        Assertions.assertNotNull(ConnectionDB.connectToDB());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        ConnectionDB.closeConnection();
        Log.info("------- Finished test: " + testInfo.getDisplayName() + " -------");
    }
}