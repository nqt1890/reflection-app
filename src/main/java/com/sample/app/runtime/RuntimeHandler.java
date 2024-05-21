package com.sample.app.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.annotations.FakeAutowired;
import com.sample.app.annotations.FakeService;
import com.sample.app.iface.IRepository;

public class RuntimeHandler {
	static final Map<String, Object> SINGLETON_INSTANCE = new HashMap<>();
	
	public static <T> void preloadService(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = obj.getClass();   //TestController
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field: fields) {
			if (field.getType().isInterface() && field.isAnnotationPresent(FakeAutowired.class)) {
				
				if (IRepository.class.isAssignableFrom(field.getType())) {
					if (field.getType().getGenericInterfaces().length > 0) {
						Type type = field.getType().getGenericInterfaces()[0];
						ParameterizedType pType = (ParameterizedType) type;
						Class<?> modelCls = (Class<?>) pType.getActualTypeArguments()[0];

						T instance = initRepository(field.getType(), modelCls);
						field.setAccessible(true);
						field.set(obj, instance);
					}
				}
				else {
					T instance = initService(field.getType());
					field.setAccessible(true);
					field.set(obj, instance);
				}
			}
		}
		System.out.println(SINGLETON_INSTANCE);
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation"})
	private static <T> T initService(Class<?> interf) {
		Reflections reflections = new Reflections("com.sample.app.service");   
		// interf == IPersonService
		Set<?> classes = reflections.getSubTypesOf(interf);
		
		Class<?> clazz = null;
		for (Object obj :classes.toArray()) {
			Class<?> cls = (Class<?>) obj;
			if (cls.isAnnotationPresent(FakeService.class)) {
				clazz = cls;
				break;
			}
		}
		if (clazz == null) {
			return null;
		}
		String name = clazz.getName();
		
		if (SINGLETON_INSTANCE.get(name) == null) {
			try {
				// impl PersonService
				SINGLETON_INSTANCE.put(name, clazz.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return (T) SINGLETON_INSTANCE.get(name);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static <T> T initRepository(Class<?> repoCls, Class<?> modelCls) {
		 return (T) Proxy.newProxyInstance(repoCls.getClassLoader(), new Class[] { repoCls },
				 new RepositoryHandler(modelCls));
	}
	
	
	public static void loadAllBeansFromControllers(ApplicationContext app) throws IllegalArgumentException, IllegalAccessException {
		// scan and init service from all RestController
		Map<String, Object> beans = app.getBeansWithAnnotation(RestController.class);
		System.out.println(app.getBeansWithAnnotation(Component.class));
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object bean = app.getBean(entry.getKey());
			preloadService(bean);
		}
	}
}
