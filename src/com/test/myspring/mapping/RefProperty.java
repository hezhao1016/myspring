package com.test.myspring.mapping;

import com.test.myspring.exception.MySpringException;
import com.test.myspring.factory.BeanFactoryManager;
import com.test.myspring.factory.BeanManager;

/**
 * Ref Ù–‘”≥…‰¿‡
 * @author user
 *
 */
public class RefProperty{

	private String name;
	private Class<?> type;
	private Object propertyValue;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	public Class<?> getType() {
		Bean bean = getPropertyValue();
		Class<?> clazz= null;
		try {
			clazz = BeanManager.loadClass(bean.getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public Bean getPropertyValue() {
		BeanFactoryManager beanFactoryManager = new BeanFactoryManager();
		try {
			Bean bean = beanFactoryManager.getBean(this.propertyValue.toString());
			return bean;
		} catch (MySpringException e) {
			e.printStackTrace();
		}
		return new Bean();
	}
	
}
