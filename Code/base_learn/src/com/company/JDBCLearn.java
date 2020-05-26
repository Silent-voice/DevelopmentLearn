package com.company;
/*
    JDBC : Java DataBase Connectivity
    1. 类似于一种数据库操作接口，声明了数据库操作的基本方法
    2. 各种数据库都有自己的jar包，自行实现了上述的方法，所以一套java代码可以运行在不同的数据库上
    3. sql的jar包通过网络访问数据库

    MySQL
    1. URL格式    jdbc:mysql://<hostname>:<port>/<db>?key1=value1&key2=value2
    2. 数据对应关系
        BIT, BOOL	        boolean
        INTEGER	            int
        BIGINT	            long
        REAL	            float
        FLOAT, DOUBLE	    double
        CHAR, VARCHAR	    String
        DECIMAL	            BigDecimal
        DATE	            java.sql.Date, LocalDate
        TIME	            java.sql.Time, LocalTime
 */


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class JDBCLearn {

    // 连接
    public void fun1() throws SQLException {
        // JDBC连接的URL, 设置时区，不然会报错serverTimezone=UTC
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?serverTimezone=UTC";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "14061216";
        // 获取连接:
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        // TODO: 访问数据库...
        // 关闭连接:
        conn.close();
    }

    // 查询
    public void fun2() throws SQLException {
        // JDBC连接的URL, 设置时区，不然会报错serverTimezone=UTC
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?serverTimezone=UTC";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "14061216";

        /*
            1. try (resource) 在代码块结束后会自动释放资源，前提是resource实现了java.lang.AutoCloseable接口
            2. Connection、Statement、ResultSet这些资源使用后都需要关闭
            3. 返回结果ResultSet
                3.1 第一行不是数据
                3.2 第一列也不是查询语句中指定的字段
                3.3 所以数据索引都是从1开始
         */
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)){
            // 创建Statement对象，用于查询
            try (Statement stmt = conn.createStatement()) {
                // 执行SQL语句
                String sql = "SELECT id, grade, name, gender FROM students WHERE gender=\'M\'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    // 判断是否有下一行，有将当前行移动到下一行，所以第一行并没有处理
                    while (rs.next()) {
                        long id = rs.getLong(1);            // 获取当前行数据的第1列字段值
                        long grade = rs.getLong(2);
                        String name = rs.getString(3);      // 根据字段数据类型使用不同的方法
                        String gender = rs.getString(4);
                    }
                }
            }
        }
    }

    /*
        1. 使用Statement不安全，有SQL注入风险
        2. 推荐使用PreparedStatement，先使用 ? 进行占位，填充时会自动对数据进行无害化处理(对特殊字符进行转义)
     */
    public void fun3(String gender, long grade) throws SQLException {
        // JDBC连接的URL, 设置时区，不然会报错serverTimezone=UTC
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?serverTimezone=UTC";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "14061216";

        // 使用 ? 进行占位
        String sql = "SELECT id, grade, name, gender FROM students WHERE gender=? AND grade=?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            // 使用PreparedStatement建立连接
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // 对占位符进行填充，会自动对特殊字符进行处理
                ps.setObject(1, gender);   // 注意：索引从1开始
                ps.setObject(2, grade);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        long d_id = rs.getLong("id");
                        long d_grade = rs.getLong("grade");
                        String d_name = rs.getString("name");
                        String d_gender = rs.getString("gender");
                    }
                }
            }
        }
    }


    /*
        1. executeUpdate() 可以执行增、删、改、语句
     */
    public void fun4(long id, long grade, String name, String gender) throws SQLException {
        // JDBC连接的URL, 设置时区，不然会报错serverTimezone=UTC
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?serverTimezone=UTC";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "14061216";

        // 插入1条数据
        String sql = "INSERT INTO students (id, grade, name, gender) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setObject(1, id);
                ps.setObject(2, grade);
                ps.setObject(3, name);
                ps.setObject(4, gender);

                // 返回插入数据个数
                int n = ps.executeUpdate();

            }
        }

        // 插入时获取自增主键，如果有多个自增列，会全部获取
        String sql2 = "INSERT INTO students (grade, name, gender) VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setObject(1, grade);
                ps.setObject(2, name);
                ps.setObject(3, gender);

                // 返回插入数据个数
                int n = ps.executeUpdate();
                // 获取刚才插入数据的自增主键
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long gen_id = rs.getLong(1); // 注意：索引从1开始
                    }
                }

            }
        }

        // 改
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE students SET name=? WHERE id=?")) {
                ps.setObject(1, "Bob");
                ps.setObject(2, 999);
                int n = ps.executeUpdate();
            }
        }

        // 删
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id=?")) {
                ps.setObject(1, 999);
                int n = ps.executeUpdate();
            }
        }

    }


    /*
    SQL事务
    1. 类似于synchronized，一组SQL语句组成的操作序列，这些语句要不全部执行成功，要不全部不执行
    2. 如果有一条语句执行失败，就进行回滚，取消之前执行的所有操作
    */
    public void fun5(long id, long grade, String name, String gender) throws SQLException {
        // JDBC连接的URL, 设置时区，不然会报错serverTimezone=UTC
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?serverTimezone=UTC";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "14061216";

        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        try {
            // 关闭自动提交功能
            conn.setAutoCommit(false);
            // 设定隔离级别为READ COMMITTED:
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            // 执行一系列SQL语句
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO students (id, grade, name, gender) VALUES (?,?,?,?)")) {
                ps.setObject(1, id);
                ps.setObject(2, grade);
                ps.setObject(3, name);
                ps.setObject(4, gender);
                int n = ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("UPDATE students SET name=? WHERE id=?")) {
                ps.setObject(1, "Bob");
                ps.setObject(2, 999);
                int n = ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id=?")) {
                ps.setObject(1, 999);
                int n = ps.executeUpdate();
            }

            // 提交事务
            conn.commit();
        }catch (SQLException e){
            // 回滚事务:
            conn.rollback();
        }finally {
            // 恢复原样，关闭连接
            conn.setAutoCommit(true);
            conn.close();
        }



    }


    /*
        Batch
        1. 一系列相似的SQL语句，不同于事务，没有原子性，相互之间不干扰
     */
    public void fun6(ArrayList<String> names, ArrayList<String> genders) throws SQLException {
        // JDBC连接的URL, 设置时区，不然会报错serverTimezone=UTC
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?serverTimezone=UTC";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "14061216";

        String sql = "INSERT INTO students (name, gender) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            // 这种方法效率低
            for (int i = 0; i < names.size(); i ++) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setObject(1, names.get(i));
                    ps.setObject(2, genders.get(i));
                    ps.executeUpdate();
                }
            }
            // 使用batch
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < names.size(); i ++) {
                    ps.setObject(1, names.get(i));
                    ps.setObject(2, genders.get(i));
                    ps.addBatch(); // 添加到batch
                }
                // 执行batch
                int[] ns = ps.executeBatch();   // 返回每个SQL语句执行的结果数量集合

            }




        }
    }


    /*
        Connection 连接池
        1. 建立Connection连接比较费资源，不适合频繁操作，所以类似进程池，有了连接池
        2. JDBC只设计了DataSource接口，具体由第三方库实现
        3. DataSource创建也很耗资源，一般是作为全局变量，在整个活动周期内使用

     */
    public void fun7() throws SQLException{
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("root");
        config.setPassword("password");
        config.addDataSourceProperty("connectionTimeout", "1000");      // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000");           // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "10");          // 最大连接数：10
        DataSource ds = new HikariDataSource(config);


        // 在此获取连接，没有创建新的，有就获取
        try (Connection conn = ds.getConnection()) {

        }   // 假关闭，只是将连接设为空闲


    }

}



