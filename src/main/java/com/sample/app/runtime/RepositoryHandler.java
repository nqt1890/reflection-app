package com.sample.app.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sample.app.util.PostgresDatasource;

public class RepositoryHandler implements InvocationHandler {
	
	private Class<?> model;
//	private Object realObject;
	
	public RepositoryHandler(Class<?> model) { 
		this.model = model;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null; 
        
//        if (true) 
//        	invoke(proxy, method, args);
        
        if (isGetMethod(method)) {
			result = queryData(model, method, args);
        }
        else if (isSaveMethod(method)) {
        	result = saveData(model, method, args);
        }
        
		return result;
    }

    
    @SuppressWarnings("unchecked")
	private <T> T queryData(Class<?> clazz, Method method, Object[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
    	String table = model.getSimpleName();
		
    	String condition = method.getName().replace("findBy", ""); 
    	String query = String.format("SELECT * FROM %s WHERE %s = ?", table, condition);
    	System.out.println(query);
    	System.out.println(method.getName() + " => " + condition);
    	
    	Connection conn = PostgresDatasource.getConnection();
    	PreparedStatement statement = conn.prepareStatement(query);
//    	statement.setString(1, args[0].toString());
    	statement.setInt(1, Integer.parseInt(args[0].toString()));
    	
    	ResultSet rs = statement.executeQuery();
    	
    	Object result = null;
		while (rs.next()) {
			result = setObject(model, rs);
	        break;
		}
    	return (T) result;
    }
    
    
    private boolean saveData(Class<?> clazz, Method method, Object[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, SQLException {
    	String table = model.getSimpleName();
    	
    	Field[] fields = model.getDeclaredFields();
    	String fieldQuery = "", valuesQuery = "";
    	
    	for(int i = 0; i < fields.length; i++) { 
    		Method getMethod = getMethodByField(clazz, fields[i]);
    		boolean stringParam = getMethod.getReturnType().equals(String.class);
    		if (i == fields.length - 1) {
    			fieldQuery += fields[i].getName();
    			if (stringParam)
    				valuesQuery += "'" + getMethod.invoke(args[0]) + "'";
    			else 
    				valuesQuery += getMethod.invoke(args[0]);
    		}
    		else {
    			fieldQuery += fields[i].getName() + ", ";
    			if (stringParam)
    				valuesQuery += "'" + getMethod.invoke(args[0]) + "',";
    			else 
    				valuesQuery += getMethod.invoke(args[0]) + ", ";
    		}
    	}
		
    	String query = String.format("INSERT INTO %s (%s) VALUES (%s)", table, fieldQuery, valuesQuery);
    	System.out.println(query);
    	
    	Connection conn = PostgresDatasource.getConnection();
    	PreparedStatement statement = conn.prepareStatement(query);
 
    	int rs;
		try {
			rs = statement.executeUpdate();
			boolean isSaved = rs > 0;
			return isSaved;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    
    private boolean isGetMethod(Method method) {
    	if (method.getName().contains("findBy") && method.getParameterCount() > 0) {
    		return true;
    	}
    	return false;
    }
    
    private boolean isSaveMethod(Method method) {
    	if (method.getName().equals("save") && method.getParameterCount() > 0) {
    		return true;
    	}
    	return false;
    }
    
    private Method getMethodByField(Class<?> clazz, Field field) {
    	Method[] methods = clazz.getMethods();
    	for(Method method: methods) {
    		if (method.getName().startsWith("get") && method.getName().toLowerCase().contains(field.getName().toLowerCase())) {
    			return method;
    		}
    	}
    	return null;
    }
	
    @SuppressWarnings({ "deprecation", "unchecked" })
	private <T> T setObject(Class<?> model, ResultSet rs) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
    	Object obj = model.newInstance();
    	Method[] methods = model.getMethods();
    	
    	for(Method method: methods) {
    		if (method.getName().startsWith("set")) {
    			String fieldName = method.getName().replace("set", "").toLowerCase();
    			System.out.println(method.getName() + " => " + method.getParameterTypes()[0]);
    			method.invoke(obj, rs.getObject(fieldName, method.getParameterTypes()[0]));
    		}
    	}
    	return (T) obj;
    }
}
