package com.lseg.helpers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseActions {

    private final Logger LOG = LogManager.getLogger(DatabaseActions.class);
    static String machineName = null;
    static String databaseURL = "DB URL";

    private static Connection getDBConnection() throws SQLException {
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String databseUser = "sa";
        String databasePassword = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(databaseURL, databseUser, databasePassword);
    }

    public String getValueFromField(String query, String columnValue) throws SQLException {
        Connection connection = getDBConnection();
        LOG.info(query);
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            return rs.getString(columnValue);
        } catch (SQLException exc) {
            exc.printStackTrace();
            LOG.info("Database call failed");
        } finally {
            connection.close();
        }
        return null;
    }

    public List<String> getColumnValuesList(String query) throws SQLException {
        Connection connection = getDBConnection();
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            List<String> valueList = new ArrayList<>();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) ;
                    String columnValue = resultSet.getString(i);
                    valueList.add(columnValue);
                }
            }
            LOG.info(valueList);
            return valueList;
        } catch (SQLException exc) {
            exc.printStackTrace();
            LOG.info("Database call failed");
        } finally {
            connection.close();
        }
        return null;
    }

    public List<String> getPreparedColumnValuesList(String query, String variable) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement prep = connection.prepareStatement(query);
        try {
            prep.setString(1, variable);
            ResultSet resultSet = prep.executeQuery();
            ResultSetMetaData rsmd = prep.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            List<String> valueList = new ArrayList<>();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) ;
                    String columnValue = resultSet.getString(i);
                    valueList.add(columnValue);
                }
            }
            LOG.info(valueList);
            return valueList;
        } catch (SQLException exc) {
            exc.printStackTrace();
            LOG.info("Database call failed");
        } finally {
            prep.close();
            connection.close();
        }
        return null;
    }

    public List<String> getPreparedVariableColumnValuesList(String query, List<String> variables) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement prep = connection.prepareStatement(query);
        try {
            int listSize = variables.size();
            for (int i = 1; i <= listSize; i++) {
                for (String variable : variables) {
                    prep.setString(i, variable);
                }
            }
            ResultSet resultSet = prep.executeQuery();
            ResultSetMetaData rsmd = prep.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            List<String> valueList = new ArrayList<>();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultSet.getString(i);
                    valueList.add(columnValue);
                }
            }
            LOG.info(valueList);
            return valueList;
        } catch (SQLException exc) {
            exc.printStackTrace();
            LOG.info("Database call failed");
        } finally {
            prep.close();
            connection.close();
        }
        return null;
    }

    private static Connection getDBConnection1() throws SQLException {
        String machineName = null;
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(machineName);
        String databaseURL = "jdbc:sqlserver://" + machineName + "\\SQLEXPRESS;trustServerCertificate=true";
        String databseUser = "sa";
        String databasePassword = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(databaseURL, databseUser, databasePassword);
    }

    private static void executeCommand(Connection conn, String cmd) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.execute(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
