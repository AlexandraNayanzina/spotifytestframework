package com.spotify.oath2.utils;

import java.util.Properties;

public class ConfigLoader {

  private static ConfigLoader configLoader;


  public static ConfigLoader getInstance() {
    if (configLoader == null) {
      configLoader = new ConfigLoader();
    }
    return configLoader;
  }

  public String getClientId(){
    return getPropertyOrThrowException("client_id");
  }

  public String getClietSecret(){
    return getPropertyOrThrowException("client_secret");
  }

  public String getGrantType(){
    return getPropertyOrThrowException("grant_type");
  }

  public String getRefreshToken(){
    return getPropertyOrThrowException("refresh_token");
  }

  public String getUserId(){
    return getPropertyOrThrowException("user_id");
  }

  private String getPropertyOrThrowException(String propertyName) {
    var value = System.getProperty(propertyName);
    if (value == null) {
      throw new RuntimeException("Property '" + propertyName + "' is null");
    }
    return value;
  }
}
