package com.test.myspring.factory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.test.myspring.mapping.Bean;
import com.test.myspring.mapping.RefProperty;
import com.test.myspring.mapping.ValueProperty;
import com.test.myspring.util.StringUtil;


/**
 * Bean组件管理类
 * 
 * @author user
 * 
 */
public class BeanManager {
	//单例模式
	private static final Map<String,ThreadLocal<Object>> threadLocals = new HashMap<String,ThreadLocal<Object>>();
	
	/**
	 * 依赖注入
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Object di(Bean bean) throws Exception{
		String beanId = bean.getId();
		//如果集合中有，就直接返回
		if(threadLocals.containsKey(beanId)){
			return threadLocals.get(beanId).get();
		}
		Class<?> clazz = loadClass(bean.getClassName());
		Object obj = clazz.newInstance();
		diValue(bean, clazz, obj);
		diRef(bean, clazz, obj);
		//如果是单例模式
		if(bean.getScope().trim().equals("singleton")){
			ThreadLocal<Object> newObj = new ThreadLocal<Object>();
			newObj.set(obj);
			threadLocals.put(beanId, newObj);
		}
		return obj;
	}

	/**
	 * 注入value属性
	 * @param bean
	 * @throws Exception
	 */
	private void diValue(Bean bean,Class<?> clazz,Object obj) throws Exception{
		for (ValueProperty vp : bean.getValueProperties()) {
			String methodName = "set"+StringUtil.initcap(vp.getName());
			Method method = clazz.getMethod(methodName,vp.getType());
			method.invoke(obj,vp.getPropertyValue());
		}
	}
	
	/**
	 * 注入ref属性
	 * @param bean
	 * @throws Exception
	 */
	private void diRef(Bean bean,Class<?> clazz,Object obj) throws Exception{
		for (RefProperty rp : bean.getRefProperties()) {
			String methodName = "set"+StringUtil.initcap(rp.getName());
			Method method = clazz.getMethod(methodName,rp.getType());
			Bean bean2 = rp.getPropertyValue();
			Class<?> clazz2 = loadClass(bean2.getClassName());
			Object obj2 = clazz2.newInstance();
			//如果还有需要注入的value
			if(bean2.getValueProperties().size()>0){
				diValue(bean2, clazz2, obj2);
			}
			//如果还有需要注入的bean，则递归
			if(bean2.getRefProperties().size()>0){
				diRef(bean2, clazz2, obj2);
			}
			method.invoke(obj,obj2);
		}
	}
	
	/**
	 * 利用反射获得类
	 * 
	 * @param className
	 * @return Class<?>
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String className)
			throws ClassNotFoundException {
		Class<?> clazz = null;
		clazz = Class.forName(className);
		return clazz;
	}
}
