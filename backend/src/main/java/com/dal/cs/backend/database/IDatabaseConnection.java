package com.dal.cs.backend.database;


import java.sql.Connection;

public interface IDatabaseConnection {

    /**
     * Creates database connection using parameters defined in database.properties
     *
     * @return SQL connection to database
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    Connection getDatabaseConnection();

    /**
     * Closes SQL connection to database
     *
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    void closeDatabaseConnection();
}
