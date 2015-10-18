package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepository implements IUserRepository {
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final IPersistenceService db;

    public UserRepository(IPersistenceService db){
        this.db = db;
    }

    public long getPasswordSaltedHash(String username) throws RowToModelParseException, AuthenticationException {
        try {
            logger.info("Fetching password salted hash for {}", username);
            String sql = "SELECT passwordHash FROM staff WHERE username=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, username);

            if(rs == null || rs.isEmpty()) {
                logger.debug("{} does not exist", username);
                throw new AuthenticationException();
            }
            return Long.parseLong(rs.get(0).get("passwordHash"));
        } catch (SQLException | NumberFormatException e) {
            logger.error("Error retrieving password salted hash for {}", username, e);
            throw new RowToModelParseException("Error retrieving password " +
                    "salted hash from database for username: " + username, e);
        }
    }

    public long getSalt(String username) throws RowToModelParseException, AuthenticationException {
        try {
            logger.info("Fetching salt for {}", username);
            String sql = "SELECT salt FROM staff WHERE username=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, username);

            if(rs == null || rs.isEmpty()) {
                logger.debug("{} does not exist", username);
                throw new AuthenticationException();
            }

            return Long.parseLong(rs.get(0).get("salt"));
        } catch (SQLException | NumberFormatException e) {
            logger.error("Error retrieving salt for {}", username, e);
            throw new RowToModelParseException("Error retrieving salt from " +
                    "database for username: " + username, e);
        }
    }

    public Staff getUserDetails(String username) throws RowToModelParseException, AuthenticationException {
        try {
            logger.info("Fetching details for {}", username);
            String sql = "SELECT firstName, lastName, department, position, " +
                "permissions FROM staff WHERE username=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, username);

            if(rs == null || rs.isEmpty()) {
                logger.debug("{} does not exist", username);
                throw new AuthenticationException();
            }

            Map<String, String> details = rs.get(0);

            Permission permission = Permission.values()[Integer.parseInt(details.get("permissions"))];

            return new Staff(username, details.get("firstName"), details.get("lastName"),
                             details.get("department"), details.get("position"), permission);
        } catch (SQLException e) {
            logger.error("Error retrieving details for {}", username, e);
            throw new RowToModelParseException("Error retrieving user details " +
                    "from database for username: " + username, e);
        }
    }

    public Staff getInvestigatingOfficer(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching investigating officer for case {}", caseNumber);
            String sql = "SELECT username FROM staff INNER JOIN(cases) " +
                "WHERE staff.username=cases.staffID AND cases.caseNumber=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, caseNumber);

            if(rs == null || rs.isEmpty()) {
                logger.debug("Investigating officer for case {} does not exist", caseNumber);
                return null;
            }

            String username = rs.get(0).get("username");
            return getUserDetails(username);
        } catch (SQLException | AuthenticationException e) {
            logger.error("Error retrieving investigating officer for case {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving investigating " +
                    "officer from database for case: " + caseNumber, e);
        }
    }

    public List<Staff> getInspectors() throws RowToModelParseException {
        try {
            logger.info("Fetching inspectors");
            String sql = "SELECT username FROM staff WHERE permissions=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, "1");

            if (rs == null || rs.isEmpty()) {
                logger.debug("No inspectors found");
                return null;
            }

            List<Staff> inspectors = new ArrayList<>();
            for (Map<String, String> inspector : rs) {
                inspectors.add(getUserDetails(inspector.get("username")));
            }
            return inspectors;
        } catch (SQLException | AuthenticationException e) {
            logger.error("Error retrieving inspectors");
            throw new RowToModelParseException("Error retrieving inspectors", e);
        }
    }

    public List<Staff> getStaff() throws RowToModelParseException {
        try {
            logger.info("Fetching staff");
            String sql = "SELECT username FROM staff WHERE username!=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, "root");

            if (rs == null || rs.isEmpty()) {
                logger.debug("No staff found");
                return null;
            }

            List<Staff> staff = new ArrayList<>();
            for (Map<String, String> inspector : rs) {
                staff.add(getUserDetails(inspector.get("username")));
            }
            return staff;
        } catch (SQLException | AuthenticationException e) {
            logger.error("Error retrieving staff");
            throw new RowToModelParseException("Error retrieving staff", e);
        }
    }

    public void insertUser(Staff user) throws RowToModelParseException {
         try {
             logger.info("Adding user {}", user);
             // Password set after user is added
             String sql = "INSERT INTO staff VALUES(?, ?, ?, ?, ?, -1, 0, ?);";
             String username = user.getUsername();
             String firstName = user.getFirstName();
             String lastName = user.getLastName();
             String department = user.getDepartment();
             String position = user.getPosition();
             int permission = -1;
             switch (user.getPermission()) {
                 case ADMIN :
                     permission = 0;
                     break;
                 case EDITOR :
                     permission = 1;
                     break;
                 default :
                     permission = 2;
                     break;
             }
 
             db.executeUpdate(sql, username, firstName, lastName, department,
                     position, String.valueOf(permission));
         } catch (SQLException e) {
             logger.error("Error inserting user {}", user, e);
             throw new RowToModelParseException("Error inserting user", e);
         }
    }

    public void updateUser(Staff user) throws RowToModelParseException {
        try {
            logger.info("Updating user {}", user.toString());
            String sql = "UPDATE staff SET firstName=?, lastName=?, department=?, " +
                "position=?, permissions=? WHERE username=?;";
             String username = user.getUsername();
             String firstName = user.getFirstName();
             String lastName = user.getLastName();
             String department = user.getDepartment();
             String position = user.getPosition();
             int permission = -1;
             switch (user.getPermission()) {
                 case ADMIN :
                     permission = 0;
                     break;
                 case EDITOR :
                     permission = 1;
                     break;
                 default :
                     permission = 2;
                     break;
             }

            db.executeUpdate(sql, firstName, lastName, department, position,
                    String.valueOf(permission), username);
        } catch (SQLException e) {
            logger.error("Unable to update user {}", user.toString(), e);
            throw new RowToModelParseException("Error updating user", e);
        }
    }

    public void deleteUser(String username) throws RowToModelParseException {
        try {
            logger.info("Deleting user {}", username);
            String sql = "DELETE FROM staff WHERE username=?;";

            db.executeUpdate(sql, username);
        } catch (SQLException e) {
            logger.error("Error deleting user {}", username, e);
            throw new RowToModelParseException("Error deleting user", e);
        }
    }

    public void setPassword(String username, int hashPassword) throws RowToModelParseException {
        try {
            logger.info("Setting {}'s password", username);
            SecureRandom rand = new SecureRandom();
            long salt = rand.nextLong();
            long saltedHashPassword = salt + hashPassword;
            String sql = "UPDATE staff SET passwordHash=?, salt=? WHERE username=?;";
            db.executeUpdate(sql, String.valueOf(saltedHashPassword),
                    String.valueOf(salt), username);
        } catch (SQLException e) {
            logger.error("Error setting password for {}", username, e);
            throw new RowToModelParseException("Error setting password", e);
        }
    }
}
