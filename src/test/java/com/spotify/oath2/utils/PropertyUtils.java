package com.spotify.oath2.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class PropertyUtils {

  public static Properties propertyLoader(String filePath){

    Properties properties = new Properties();
    BufferedReader reader;
    try{
      reader = new BufferedReader(new FileReader(filePath));
      try{
        properties.load(reader);
        reader.close();
      } catch (Exception e){
        e.printStackTrace();
        throw new RuntimeException("Failed to load properties file" + filePath);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("Properties file not found at" + filePath);
    }
    return properties;
  }

}
