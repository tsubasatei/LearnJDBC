package com.xt.test;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xt
 * @date 2019/1/11 - 16:24
 * @description
 */
public class TestJDBC {

    @Test
    public void test() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/dbgirl";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "root");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    /**
     * 通用
     * @return
     */
    public Connection getConnection() throws Exception {
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;

        InputStream is = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        Driver driver = (Driver) Class.forName(driverClass).newInstance();
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);

        Connection connection = driver.connect(jdbcUrl, info);
        return connection;
    }

    @Test
    public void testGetConnection() throws Exception {
        System.out.println(getConnection());
    }
}
