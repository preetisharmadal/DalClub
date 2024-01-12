package com.dal.cs.backend.baseUtils.dataLayer;

import com.dal.cs.backend.database.IDatabaseConnection;

import java.sql.Connection;


public abstract class BaseDataLayer implements IBaseDataLayer{

    protected final Connection connection;
    private final IDatabaseConnection iDatabaseConnection;

    public BaseDataLayer(IDatabaseConnection iDatabaseConnection) {
        this.iDatabaseConnection = iDatabaseConnection;
        this.connection = this.iDatabaseConnection.getDatabaseConnection();
    }

    protected String getProcedureCallString(String procedureName, int numberOfParameters) {
        String procedureParameter = "?,".repeat(numberOfParameters);
        if (!procedureParameter.equals("")) {
            procedureParameter = procedureParameter.substring(0, 2 * numberOfParameters - 1);
        }
        return String.format("{CALL %s(%s)}", procedureName, procedureParameter);
    }
}
