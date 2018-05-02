package org.bsamartins.tasksdemo;

import org.bsamartins.tasksdemo.configuration.ApplicationConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {
 
   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] { ApplicationConfig.class };
   }
 
   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] {};
   }
 
   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }
}