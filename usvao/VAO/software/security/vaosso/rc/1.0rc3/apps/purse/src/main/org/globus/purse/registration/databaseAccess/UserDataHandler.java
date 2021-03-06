/*
This file is licensed under the terms of the Globus Toolkit Public
License, found at http://www.globus.org/toolkit/download/license.html.
*/
package org.globus.purse.registration.databaseAccess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.purse.exceptions.DatabaseAccessException;
import org.globus.purse.exceptions.RegistrationException;
import org.globus.purse.exceptions.UserRegistrationException;
import org.globus.purse.registration.UserData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Class to store/get UserData
 */
public class UserDataHandler {
    
    static Log logger =
	LogFactory.getLog(UserData.class.getName());

    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 
     * Stores the user data into the table. UserData object must have a token.
     *
     * @param userData User data object representing the data to be stored.
     * @exception DatabaseAccessException If any error occurs.
     */
    public static void storeData(UserData userData)
            throws DatabaseAccessException {

        logger.debug("Store data");

        // Only user dn can be null at this point.
        String query = "insert into " + DatabaseConstants.USER_TABLE_NAME
                + "( " + DatabaseConstants.USER_COL_TOKEN + ", "
                + DatabaseConstants.USER_COL_FIRST_NAME + ", "
                + DatabaseConstants.USER_COL_LAST_NAME + ", "
                + DatabaseConstants.USER_COL_CONTACT_PERSON + ", "
                + DatabaseConstants.USER_COL_STMT_WORK + ", "
                + DatabaseConstants.USER_COL_USER_NAME + ", "
                + DatabaseConstants.USER_COL_PASSWORD_SHA1 + ", "
                + DatabaseConstants.USER_COL_INSTITUTION + ", "
                + DatabaseConstants.USER_COL_PROJECT_NAME + ", "
                + DatabaseConstants.USER_COL_EMAIL + ", "
                + DatabaseConstants.USER_COL_PHONE + ", "
                + DatabaseConstants.USER_COL_COUNTRY + ", "
                + DatabaseConstants.USER_COL_STATUS + ","
                + DatabaseConstants.USER_COL_RA_ID + ","
                + DatabaseConstants.USER_COL_DN + ","
                + DatabaseConstants.USER_COL_CREATION + ","
                + DatabaseConstants.USER_COL_LAST_ACCESS + ","
                + DatabaseConstants.USER_COL_PORTAL_CONFIRM_URL + ","
                + DatabaseConstants.USER_COL_PORTAL_NAME + ","
                + DatabaseConstants.USER_COL_NUM_LOGINS
                + ") values ('" + DatabaseManager.sanitize(userData.getToken()) + "','"
                + DatabaseManager.sanitize(userData.getFirstName()) + "','" + DatabaseManager.sanitize(userData.getLastName()) + "','"
                + DatabaseManager.sanitize(userData.getContactPerson()) + "','" + DatabaseManager.sanitize(userData.getStmtOfWork())
                + "','" + DatabaseManager.sanitize(userData.getUserName()) + "','"
                //+ "','" + DatabaseManager.sanitize(userData.getUserName()) + "', encode('"
                // + DatabaseManager.sanitize(userData.getPassword()) + "','" + DatabaseManager.getPassphrase()
                //+ "'), sha1('" + DatabaseManager.sanitize(userData.getPassword())
                + userData.getPasswordSha1()
                + "', '" + DatabaseManager.sanitize(userData.getInstitution()) + "','"
                + DatabaseManager.sanitize(userData.getProjectName()) + "','" + DatabaseManager.sanitize(userData.getEmailAddress())
                + "','" + DatabaseManager.sanitize(userData.getPhoneNumber())
                + "','" + DatabaseManager.sanitize(userData.getCountry())
                + "'," + userData.getStatus()
                + "," + userData.getRaId() + ",";
        String dn = userData.getUserDN();
        if (dn != null) {
            query = query + "'" + dn + "',";
        } else {
            query = query + "null,";
        }

        Date creation = userData.getCreationTime();
        if (creation != null) {
            query = query + "'" + DATE_TIME_FORMATTER.format(creation) + "',";
        } else {
            query = query + "null,";
        }

        Date lastLogin = userData.getLastLogin();
        if (lastLogin != null) {
            query = query + "'" + lastLogin.toString() + "',";
        } else {
            query = query + "null,";
        }

        query = query + "'" + userData.getPortalConfirmUrl() + "',";
        query = query + "'" + userData.getPortalName() + "',";

        query = query + userData.getNumberOfLogins() + ")";

        DatabaseManager.runUpdateQuery(query);

        UserData retrieved = getDataForUsername(userData.getUserName());
        Vector<Integer> roles = userData.getUserRoles();
        if (roles != null) {
            for (int role : roles)
                RoleDataHandler.addUserRole(retrieved.getUserId(), role);
        }
    }

    public static void setStatus(String token, int statusId) 
	throws DatabaseAccessException {
	
	logger.debug("setStatus for " + token + " as " + statusId);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    + DatabaseConstants.USER_COL_STATUS + "=" + statusId + " where " 
	    + DatabaseConstants.USER_COL_TOKEN + "='" + DatabaseManager.sanitize(token.trim()) + "'";
	DatabaseManager.runUpdateQuery(query);
    }

    public static void setStatusForUsername(String userName, int statusId) 
	throws DatabaseAccessException {
	
	logger.debug("setStatus for user name" + userName + " as " + statusId);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    + DatabaseConstants.USER_COL_STATUS + "=" + statusId + " where " 
	    + DatabaseConstants.USER_COL_USER_NAME + "='" + DatabaseManager.sanitize(userName) + "'";
	DatabaseManager.runUpdateQuery(query);
    }

    public static void setStatus(int userId, int statusId) 
	throws DatabaseAccessException {
	
	logger.debug("setStatus for " + userId + " as " + statusId);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    + DatabaseConstants.USER_COL_STATUS + "=" + statusId + " where " 
	    + DatabaseConstants.USER_COL_ID + "=" + userId;
	DatabaseManager.runUpdateQuery(query);
    }

    public static void setUserDNForUsername(String userName, String dn) 
	throws DatabaseAccessException {
	
	logger.debug("setDn for " + userName + " as " + dn);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    + DatabaseConstants.USER_COL_DN + "='" + DatabaseManager.sanitize(dn) + "' where "
	    + DatabaseConstants.USER_COL_USER_NAME + "='" + DatabaseManager.sanitize(userName) + "'";
	DatabaseManager.runUpdateQuery(query);
    }

    public static void setUserDN(String token, String dn) 
	throws DatabaseAccessException {
	
	logger.debug("setDn for " + token + " as " + dn);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    + DatabaseConstants.USER_COL_DN + "='" + dn + "' where " 
	    + DatabaseConstants.USER_COL_TOKEN + "='" + DatabaseManager.sanitize(token) + "'";
	DatabaseManager.runUpdateQuery(query);
    }

    public static void setUserDN(int userId, String dn) 
	throws DatabaseAccessException {
	
	logger.debug("setDn for " + userId + " as " + dn);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    + DatabaseConstants.USER_COL_DN + "='" + DatabaseManager.sanitize(dn) + "' where "
	    + DatabaseConstants.USER_COL_ID + "=" + userId;
	DatabaseManager.runUpdateQuery(query);
    }

    /** Be sure to test the password first with {@link UserData#setPassword} */
    public static void setUserPassword(String userName, String password)
	throws DatabaseAccessException {

    logger.debug("setPassword for " + userName);
	String query = "update " + DatabaseConstants.USER_TABLE_NAME + " set "
	    // + DatabaseConstants.USER_COL_PASSWORD + "=encode('" + DatabaseManager.sanitize(password)
	    // + "','" + DatabaseManager.getPassphrase() + "'), "
	    + DatabaseConstants.USER_COL_PASSWORD_SHA1 + "=sha1('" + DatabaseManager.sanitize(password)
	    + "') where " 
	    + DatabaseConstants.USER_COL_USER_NAME + "='" + DatabaseManager.sanitize(userName) + "'";
	DatabaseManager.runUpdateQuery(query);
    }

    /**
     * Returns the user data for the given userId
     *
     * @param userId
     *        Token for which user data is required.
     * @return <code>UserData</code>
     *         User data for the userId
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static UserData getData(int userId) throws DatabaseAccessException {

	logger.debug("getData " + userId);
	String query = "select * from " + DatabaseConstants.USER_TABLE_NAME 
	    + " where " + DatabaseConstants.USER_COL_ID + "=" + userId;
	return getUserDataForQuery(query);
    }

    /**
     * Returns the user data for the given token.
     *
     * @param token
     *        Token for which user data is required.
     * @return <code>UserData</code>
     *         User data for the token
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static UserData getData(String token) 
	throws DatabaseAccessException {

	logger.debug("getData " + token.trim());
	String query = "select * from " + DatabaseConstants.USER_TABLE_NAME 
	    + " where " + DatabaseConstants.USER_COL_TOKEN + "='" 
            + DatabaseManager.sanitize(token.trim()) + "'";
	return getUserDataForQuery(query);
    }

    /**
     * Returns userId given user name
     *
     * @param userName
     *        user name for which token is required.
     * @return int
     *        user id for which user data is required .
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static int getUserId(String userName)
	throws RegistrationException {
	UserData userData = getDataForUsername(userName);
	if (userData == null) {
	    throw new RegistrationException("User name " + userName 
					    + " does not exist");
	}
	return userData.getUserId();
    }

    /**
     * Returns the user data for the given user name (used to log into portal)
     *
     * @param userName
     *        userName for which user data is required.
     * @return <code>UserData</code>
     *         User data for the user name
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static UserData getDataForUsername(String userName)
	throws DatabaseAccessException {
    logger.debug("getData for user name " + userName);
	String query = "select * from " + DatabaseConstants.USER_TABLE_NAME 
	    + " where " + DatabaseConstants.USER_COL_USER_NAME + "='" 
	    + DatabaseManager.sanitize(userName.trim()) + "'";
	return getUserDataForQuery(query);
    }

    /**
     * Returns the user data for the given user email address
     *
     * @param email
     *        address for which user data is required.
     * @return <code>UserData</code>
     *         User data for the user name
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static UserData getDataForEmail(String email)
	throws DatabaseAccessException {
    logger.debug("getData for user email " + email);
	String query = "select * from " + DatabaseConstants.USER_TABLE_NAME
	    + " where " + DatabaseConstants.USER_COL_EMAIL + "='"
	    + DatabaseManager.sanitize(email.trim()) + "'";
	return getUserDataForQuery(query);
    }

    /**
     * Deletes user data from the table for given token
     * @param userId
     *        user id for which user data needs to be deleted.
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static void deleteData(int userId) throws DatabaseAccessException {

	logger.debug("Delete data " + userId);
	String query = "delete from " + DatabaseConstants.USER_TABLE_NAME 
	    + " where " + DatabaseConstants.USER_COL_ID + "=" + userId;
	DatabaseManager.runUpdateQuery(query);
    }

    /**
     *
     * Deletes user data from the table for given user name.
     * @param userName
     *        userName for which user data needs to be deleted.
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static void deleteForUsername(String userName) 
	throws DatabaseAccessException {

	logger.debug("delete for username " + userName);
	String query = "delete from " + DatabaseConstants.USER_TABLE_NAME 
	    + " where " + DatabaseConstants.USER_COL_USER_NAME + "='" 
	    + DatabaseManager.sanitize(userName.trim()) + "'";
	DatabaseManager.runUpdateQuery(query);
    }
    
    /**
     * Deletes all user data that that has the given status id
     *
     * @param statusId
     *        Status id 
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static void deleteUsers(int statusId) 
	throws DatabaseAccessException {
	
	String query = "select " + DatabaseConstants.USER_COL_ID + " from "
	    + DatabaseConstants.USER_TABLE_NAME + " where " 
	    + DatabaseConstants.USER_COL_STATUS + "=" + statusId;

	Connection connection = DatabaseManager.getDBConnection();
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query);
	    if (resultSet!=null) {
		while (resultSet.next()) {
		    int userId  = 
			resultSet.getInt(DatabaseConstants.USER_COL_ID);
		    RoleDataHandler.removeAllUserRoles(userId);
		    deleteData(userId);
		}
	    }
	}
	catch (SQLException exp) {
	    logger.error("Error verifying presence of user\n " + query, exp);
	    throw new DatabaseAccessException(DatabaseManager.connectionURL, 
					      DatabaseManager.userName, 
					      "Error verifying precence of "
					      + "user. " + exp.getMessage(), 
					      exp);
	}
	finally {
	    // returning connection irrespective of whether stmt
	    // and result set are closed or not.
	    DatabaseManager.returnDBConnection(connection);
	    try {
		if (resultSet != null)
		    resultSet.close();
		if (statement != null)
		    statement.close();
	    }
	    catch (SQLException ignored) { }
    }

    }

    /**
     * Checks if userName alrady exists in database.
     *
     * @param userName
     *        userName for which teh check needs to be made
     * @exception <code>DatabaseAccessException</code>
     *            If any error occurs.
     */
    public static boolean userNameExists(String userName)
    	throws DatabaseAccessException {
	logger.debug("User name to test " + userName);
	String query = "select * from " + DatabaseConstants.USER_TABLE_NAME 
	    + " where " + DatabaseConstants.USER_COL_USER_NAME + "='" 
	    + userName.trim() + "'";
	Connection connection = DatabaseManager.getDBConnection();
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query);
        return (resultSet != null) && (resultSet.next());
    }
	catch (SQLException exp) {
	    logger.error("Error verifying presence of user\n " + query, exp);
	    throw new DatabaseAccessException(DatabaseManager.connectionURL, 
					      DatabaseManager.userName, 
					      "Error verifying presence of "
					      + "user. " + exp.getMessage(), 
					      exp);
	}
	finally {
	    // returning connection irrespective of whether stmt
	    // and result set are closed or not.
	    DatabaseManager.returnDBConnection(connection);
	    try {
		if (resultSet != null)
		    resultSet.close();
		if (statement != null)
		    statement.close();
	    }
	    catch (SQLException ignored) { }
    }
	
    }

    private static UserData getUserDataForQuery(String query)
	throws DatabaseAccessException {
	
	logger.debug("Query is " + query);
	Connection connection = DatabaseManager.getDBConnection();
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query);
	    return constructUserData(resultSet);
	}
	catch (SQLException exp) {
	    logger.error("Error retrieving user data\n " + query, exp);
	    throw new DatabaseAccessException(DatabaseManager.connectionURL, 
					      DatabaseManager.userName, 
					      "Error retrieving user data "
					      + exp.getMessage(), exp);
	}
	finally {
	    // returning connection irrespective of whether stmt
	    // and result set are closed or not.
	    DatabaseManager.returnDBConnection(connection);
	    try {
		if (resultSet != null)
		    resultSet.close();
		if (statement != null)
		    statement.close();
	    }
	    catch (SQLException ignored) { }
    }
    }

    private static UserData constructUserData(ResultSet resultSet)
	throws DatabaseAccessException {
	
	logger.debug("Construct result set");
	UserData userData = null;
	try {
	    if ((resultSet!=null) && (resultSet.next())) {
		// String password = 		    
		//     resultSet.getString(DatabaseConstants.USER_COL_PASSWORD);
		// String decodedPass = 
		//     decodePassword(password, DatabaseManager.getPassphrase());
		int userId = resultSet.getInt(DatabaseConstants.USER_COL_ID);
		    
		Vector<Integer> roles = RoleDataHandler.getUserRolesId(userId);
		userData = 
		    new UserData(
                    userId,
                    resultSet.getString(DatabaseConstants.USER_COL_FIRST_NAME),
                    resultSet.getString(DatabaseConstants.USER_COL_LAST_NAME),
                    resultSet.getString(DatabaseConstants.USER_COL_CONTACT_PERSON),
                    resultSet.getString(DatabaseConstants.USER_COL_STMT_WORK),
                    resultSet.getString(DatabaseConstants.USER_COL_USER_NAME),
                    resultSet.getString(DatabaseConstants.USER_COL_PASSWORD_SHA1),
                    // decodedPass,
                    resultSet.getString(DatabaseConstants.USER_COL_INSTITUTION),
                    resultSet.getString(DatabaseConstants.USER_COL_PROJECT_NAME),
                    resultSet.getString(DatabaseConstants.USER_COL_EMAIL),
                    resultSet.getString(DatabaseConstants.USER_COL_PHONE),
                    resultSet.getString(DatabaseConstants.USER_COL_COUNTRY),
                    resultSet.getString(DatabaseConstants.USER_COL_TOKEN),
                    resultSet.getString(DatabaseConstants.USER_COL_PORTAL_CONFIRM_URL),
                    resultSet.getString(DatabaseConstants.USER_COL_PORTAL_NAME),
                    resultSet.getInt(DatabaseConstants.USER_COL_STATUS),
                    resultSet.getInt(DatabaseConstants.USER_COL_RA_ID),
                    resultSet.getString(DatabaseConstants.USER_COL_DN),
                    null,
                    resultSet.getInt(DatabaseConstants.USER_COL_NUM_LOGINS),
                    resultSet.getDate(DatabaseConstants.USER_COL_CREATION),
                    resultSet.getDate(DatabaseConstants.USER_COL_LAST_ACCESS));
            userData.addRoles(roles);
	    }
	} catch (SQLException sqlExcep) {
	    logger.error("Cannot return database connection to pool", 
                         sqlExcep);
	    throw new DatabaseAccessException(DatabaseManager.connectionURL, 
					      DatabaseManager.userName, 
					      "Error returning db connection"
					      + " to pool. " 
					      + sqlExcep.getMessage(), 
					      sqlExcep);
	} catch (UserRegistrationException exp) {
	    String err = "Error constructing user data after retrieval";
	    logger.error(err);
	    throw new DatabaseAccessException(err, exp);
	}
	return userData;
    }

    public static boolean passwordMatches(UserData data, String passPhrase) 
	throws DatabaseAccessException {
	return passwordSha1(passPhrase).equals(data.getPasswordSha1());
    }
	
    public static String passwordSha1(String passPhrase)
	throws DatabaseAccessException {
	
	String escaped = 
	    DatabaseManager.escapeDoubleQuote(
			    DatabaseManager.escapeSingleQuote(
			    DatabaseManager.escapeBackslash(passPhrase)));
	String query = "select sha1('" + DatabaseManager.sanitize(passPhrase) + "')";
        String passwordSha1 = null;

	logger.debug("Query is " + query);

	Connection connection = DatabaseManager.getDBConnection();
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query);
	    if ((resultSet!=null) && (resultSet.next())) {
		passwordSha1 = resultSet.getString(1);
	    }
	}
	catch (SQLException exp) {
	    logger.error("Error determining sha1 for user password\n " + query, exp);
	    throw new DatabaseAccessException(DatabaseManager.connectionURL, 
					      DatabaseManager.userName, 
					      "Error determining sha1 for user password "
					      + exp.getMessage(), 
					      exp);
	}
	finally {
	    // returning connection irrespective of whether stmt
	    // and result set are closed or not.
	    DatabaseManager.returnDBConnection(connection);
	    try {
		if (resultSet != null)
		    resultSet.close();
		if (statement != null)
		    statement.close();
	    }
	    catch (SQLException ignored) { }
        }
	return passwordSha1;
    }
}
