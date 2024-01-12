package com.dal.cs.backend.Club.DataLayer;

import java.sql.SQLException;

public interface IClubSecondDataLayer
{
    public String getLatestRequestId() throws SQLException;
    public String getLatestClubId() throws SQLException;
    public String getLatestJoinClubRequestId() throws  SQLException;
}
