package com.sample.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class PostgresDatasource {
	static final String JDBC_DRIVER = "org.postgresql.Driver";   
	static final String USER = "citus"; 
    static final String PASS = "P@ssword123456";
    static final String URL = "jdbc:postgresql://c-cosmo-postgres.soovqyprdlutqz.postgres.cosmos.azure.com:5432/citus?user=citus&password=P@ssword123456&sslmode=require";
    
//    static final String connectionStr = "jdbc:postgresql://c-cosmo-postgres.soovqyprdlutqz.postgres.cosmos.azure.com:5432/citus?user=citus&password=P@ssword123456&sslmode=require";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
	    	PGSimpleDataSource dataSource = new PGSimpleDataSource();
	
	    	dataSource.setServerName("c-cosmo-postgres.soovqyprdlutqz.postgres.cosmos.azure.com");
	    	dataSource.setPortNumber(5432);
	    	dataSource.setDatabaseName( "citus" );
	    	dataSource.setUser(USER);
	    	dataSource.setPassword(PASS);
	
	    	Connection conn = null; 
	    	
        Class.forName(JDBC_DRIVER); 

        System.out.println("Connect to database..."); 
        conn = DriverManager.getConnection(URL, USER, PASS);  
	    	      
	    	return conn;
    }
}
