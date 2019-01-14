package com.xt.jdbc;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xt
 * @date 2019/1/11 - 16:24
 * @description
 */
public class JDBCTest {

    @Test
    public void testConnection2() throws Exception {
        System.out.println(getConnection2());
    }

    public Connection getConnection2() throws Exception {
        // 1. 准备连接数据库的4个字符串
        // 1) 创建 Properties 对象
        Properties properties = new Properties();

        // 2) 获取 jdbc.properties 对应的输入流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");

        // 3) 加载2）对应的输入流
        properties.load(is);

        // 4) 具体确定 driver，url，url，password 4个字符串
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        // 2. 加载数据库驱动程序(对应的 Driver 实现类中有注册驱动的静态代码块)
        Class.forName(driver);

        // 3. 通过 DriverManager 的 getConnection() 方法获取数据库连接
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * DriverManager: 驱动的管理类
     * 1）可以通过重载的 getConnection() 方法获取数据库连接。较为方便
     * 2）可以同时管理多个驱动程序: 若注册了多个数据库连接，则调用 getConnection()
     *    方法时传入的参数不同，即返回不同的数据库连接。
     * @throws Exception
     */
    @Test
    public void testDriverManager() throws Exception {
        // 1. 准备连接数据库的4个字符串
        // 驱动的全类名
        String driver = "com.mysql.jdbc.Driver";
        // JDBC url
        String url = "jdbc:mysql:///dbgirl";
        // user
        String user = "root";
        // password
        String password = "root";

        // 2. 加载数据库驱动程序(对应的 Driver 实现类中有注册驱动的静态代码块)
        Class.forName(driver);

        // 3. 通过 DriverManager 的 getConnection() 方法获取数据库连接
        Connection connection = DriverManager.getConnection(url, user, password);

        System.out.println(connection);

    }
    /**
     * Driver 是一个接口：数据库厂商必须提供实现的接口，能从其中获取数据库连接
     *
     * 1. 加入mysql驱动
     * 1）解压mysql-connector-java-5.1.7.zip
     * 2) 在当前项目下新建lib目录
     * 3）把mysql-connector-java-5.1.7-bin.jar复制到lib目录下
     * 4）在project structure-modules里加入jar
     * @throws SQLException
     */
    @Test
    public void test() throws SQLException {
        // 1. 创建一个Driver实现类的对象
        Driver driver = new com.mysql.jdbc.Driver();

        // 2. 准备连接数据库的基本信息: url, user, password
        String url = "jdbc:mysql://localhost:3306/dbgirl";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "root");

        // 3. 调用 Driver 接口的 connect(url, info) 获取数据库连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    /**
     * 编写一个通用的方法，在不修改源程序的情况下，可以获取任何数据库的连接
     * 解决方案: 把数据库驱动 Driver 实现类的全类名、url、user、password 放入一个
     * 配置文件中，通过修改配置文件的方式实现和具体的数据库解耦。
     * @return
     */
    public Connection getConnection() throws Exception {
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;

        // 读取类路径下的 jdbc.properties 文件
        InputStream is = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        // 通过反射创建 Driver 对象
        Driver driver = (Driver) Class.forName(driverClass).newInstance();
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);

        // 通过 Driver 的 connect 方法获取数据库连接
        Connection connection = driver.connect(jdbcUrl, info);
        return connection;
    }

    @Test
    public void testGetConnection() throws Exception {
        System.out.println(getConnection());
    }
}
