package com.geccocrawler.gecco.monitor;

import com.geccocrawler.gecco.annotation.MBean;
import org.reflections.Reflections;
import org.weakref.jmx.MBeanExporter;

import java.lang.management.ManagementFactory;
import java.util.Set;

public class GeccoJmx {

	public static Reflections singleton = new Reflections("com.geccocrawler.gecco.monitor");
	
	private static MBeanExporter exporter = new MBeanExporter(ManagementFactory.getPlatformMBeanServer());
	
	public static void export(String classpath) {
		Set<Class<?>> mbeanClasses = singleton.getTypesAnnotatedWith(MBean.class);
		for(Class<?> mbeanClass : mbeanClasses) {
			MBean mbean = (MBean)mbeanClass.getAnnotation(MBean.class); 
			String name = mbean.value();
	    	try {
				exporter.export(classpath+":name="+name, mbeanClass.newInstance());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void unexport() {
		exporter.unexportAllAndReportMissing();
	}

}
