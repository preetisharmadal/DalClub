package com.dal.cs.backend.member.DataLayer;

import com.dal.cs.backend.baseUtils.Enum.EnumUtils;
import com.dal.cs.backend.baseUtils.dataLayer.BaseDataLayer;
import com.dal.cs.backend.database.IDatabaseConnection;
import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;
import com.dal.cs.backend.member.ObjectBuilder.MemberBuilder;
import com.dal.cs.backend.member.ServiceLayer.MemberServiceLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberDataLayer extends BaseDataLayer implements IMemberDataLayer {
    private static final Logger logger = LogManager.getLogger(MemberServiceLayer.class);


    @Autowired
    public MemberDataLayer(IDatabaseConnection iDatabaseConnection) {
        super(iDatabaseConnection);
    }

    public static MemberDataLayer getInstance(IDatabaseConnection iDatabaseConnection) {
        return new MemberDataLayer(iDatabaseConnection);
    }

    /**
     * This method will take the user input for user registration
     * new club requests
     *
     * @param member member object
     * @return true if user registered successfully
     */

    public boolean createNewMember(Member member) {
        try {
            String procedure = getProcedureCallString("MemberSaveNewMember", 9);
            CallableStatement cs = connection.prepareCall(procedure);
            cs.setString(1, member.getEmailId());
            cs.setString(2, member.getFirstName());
            cs.setString(3, member.getLastName());
            cs.setString(4, member.getMemberType().toString());
            cs.setString(5, member.getProgram());
            cs.setInt(6, member.getTerm());
            cs.setString(7, member.getMobile());
            cs.setDate(8, Date.valueOf(member.getDob()));
            cs.setString(9, member.getPassword());
            cs.execute();

        } catch (SQLException e) {
            logger.info("Creation filed new member" + e.getMessage());
            return false;
        }
        logger.info("Exiting createNewMember() in MemberDataLayer");
        return true;
    }


    @Override
    public Member getMember(String emailId) {
        logger.info("[Member][Data] Get member");
        String callProcedure = getProcedureCallString("MemberGetMemberDetails", 1);
        try {
            CallableStatement callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, emailId);
            logger.info("[Member][Data] Executed procedure to get member details ");
            boolean procedureCallStatus = callableStatement.execute();
            logger.info("[Member][Data] Procedure call status " + procedureCallStatus);

            if (procedureCallStatus) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    logger.info("[Member][Data] Found member");
                    MemberType memberType = EnumUtils.fromString(MemberType.class, resultSet.getString("userType"));
                    Member member = new MemberBuilder()
                            .setEmailId(emailId)
                            .setFirstName(resultSet.getString("firstName"))
                            .setLastName(resultSet.getString("lastName"))
                            .setMemberType(memberType)
                            .setProgram(resultSet.getString("program"))
                            .setTerm(resultSet.getInt("term"))
                            .setMobile(resultSet.getString("mobileNumber"))
                            .setDob(resultSet.getDate("DOB").toLocalDate())
                            .setPassword(resultSet.getString("password"))
                            .createMember();
                    return member;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.warn("[Member][Data] Procedure call to get member failed");
        return null;
    }

    @Override
    public boolean deleteMember(String emailId) {
        logger.info("[Member][Data] Delete member");
        String callProcedure = getProcedureCallString("MemberDeleteMember", 1);
        try {
            CallableStatement callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, emailId);
            logger.info("[Member][Data] Executed procedure to delete member details ");
            int rowsAffected = callableStatement.executeUpdate();
            logger.info("[Member][Data] Rows affected " + rowsAffected);

            if (rowsAffected == 0) {
                logger.warn("[Member][Data] Didn't delete any member rows");
                return false;
            } else {
                logger.info("[Member][Data] Successfully deleted rows");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
