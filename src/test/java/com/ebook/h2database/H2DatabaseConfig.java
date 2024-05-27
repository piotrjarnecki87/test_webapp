package com.ebook.h2database;


import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;

public class H2DatabaseConfig {

    private DataSource dataSource;

    public H2DatabaseConfig() {
        this.dataSource = createDataSource();
    }

    private DataSource createDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");
        return ds;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
}
