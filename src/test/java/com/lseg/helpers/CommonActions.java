package com.lseg.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class CommonActions {
    private final Logger LOG = LogManager.getLogger(CommonActions.class);
    public static final DatabaseActions databaseActions = new DatabaseActions();
    protected static Properties properties = new Properties();

    public String readPropertiesFile(String data) {
        try {
            FileInputStream inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            data = properties.getProperty(data);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info(e.getMessage());
        }
        return data;
    }
    protected void compareDatabaseListValues(String query, String[] data) {
        List<String> valueList = null;
        try {
            valueList = databaseActions.getColumnValuesList(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = 0;
        if (valueList != null) {
            for (String value : valueList) {
                Assert.assertEquals(value, data[count]);
                count++;
            }
        }
    }

    protected void comparePreparedDatabaseListValues(String query, String variable, String[] data) {
        List<String> valueList = null;
        try {
            valueList = databaseActions.getPreparedColumnValuesList(query, variable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = 0;
        if (valueList != null) {
            for (String value : valueList) {
                Assert.assertEquals(value, data[count]);
                count++;
            }
        }
    }

    protected void comparePreparedVariableDatabaseListValues(String query, List<String> variables, String[] data) {
        List<String> valueList = null;
        try {
            valueList = databaseActions.getPreparedVariableColumnValuesList(query, variables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = 0;
        if (valueList != null) {
            for (String value : valueList) {
                Assert.assertEquals(value, data[count]);
                count++;
            }
        }
    }
}
