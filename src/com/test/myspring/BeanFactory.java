package com.test.myspring;

import com.test.myspring.factory.BeanFactoryManager;
import com.test.myspring.factory.BeanManager;
import com.test.myspring.mapping.Bean;

/**
 * Bean组件工厂
 * @author user
 *
 */
public class BeanFactory {
	private String config;
	private BeanFactoryManager beanFactoryManager;
	
	/**
	 * 得到BeanFactory实例
	 */
	public BeanFactory(){
		init();
	}
	
	/**
	 * 得到BeanFactory实例
	 * @param config 配置文件名称，可设置多个，用英文逗号分隔
	 */
	public BeanFactory(String config){
		this.config = config;
		init();
	}
	
	/**
	 * 初始化BeanFactory
	 */
	public void init() {
		init(this.config);
	}
	
	/**
	 * 初始化BeanFactory
	 * @param config，配置文件名称，可设置多个，用英文逗号分隔
	 */
	public void init(String config) {
		// 可以包含多个配置文件
		String[] configFiles = null;
		if (config == null || config.isEmpty()) {
			configFiles = new String[] { "myspring.xml" };
		} else {
			// 拆分配置文件名称字符串
			configFiles = config.split(",");
		}
		this.beanFactoryManager = new BeanFactoryManager(configFiles);
	}

	/**
	 * 根据Id获取组件
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId){
		try {
			Bean bean = beanFactoryManager.getBean(beanId);
			Object obj = new BeanManager().di(bean);
			return obj;
		} catch (Exception e) {
			System.out.println("获取Bean组件["+beanId+"]出错");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到配置文件名称
	 * @return
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * 设置配置文件名称
	 * @param config
	 */
	public void setConfig(String config) {
		this.config = config;
	}

}
