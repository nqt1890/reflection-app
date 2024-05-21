//package com.sample.app.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class H2Datasource {
//	static final String JDBC_DRIVER = "org.h2.Driver";   
//	static final String USER = "sa"; 
//    static final String PASS = "";
//    static final String URL = "jdbc:h2:mem:testdb";
//    
//    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//    	Connection conn = null; 
//    	
//        Class.forName(JDBC_DRIVER); 
//
//        System.out.println("Connect to database..."); 
//        conn = DriverManager.getConnection(URL, USER, PASS);  
//    	      
//    	return conn;
//    }
//}
