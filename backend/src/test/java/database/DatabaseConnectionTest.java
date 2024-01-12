package database;

import com.dal.cs.backend.database.DatabaseConnection;
import com.dal.cs.backend.database.IDatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DatabaseConnectionTest {
    private IDatabaseConnection databaseConnection;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        databaseConnection = new DatabaseConnection();
        connection = databaseConnection.getDatabaseConnection();
    }

    @AfterEach
    public void TestTearDown() {
        databaseConnection.closeDatabaseConnection();
    }


    @Test
    public void checkCreateDatabaseConnection() throws Exception {
        Assertions.assertTrue(connection.isValid(60));
    }

    @Test
    public void checkCloseDatabaseConnection() throws Exception {
        databaseConnection.closeDatabaseConnection();
        Assertions.assertTrue(connection.isClosed());
    }

}
