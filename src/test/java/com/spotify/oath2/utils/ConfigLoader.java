package com.spotify.oath2.utils;

import java.util.Properties;

public class ConfigLoader {

  private final Properties properties;
  private static ConfigLoader configLoader;


  private ConfigLoader() {
    properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");

  }

  public static ConfigLoader getInstance() {
    if (configLoader == null) {
      configLoader = new ConfigLoader();
    }
    return configLoader;
  }

  public String getClientId(){
    String clientIdProperty = properties.getProperty("client_id");
    if(clientIdProperty != null) return clientIdProperty;
    else throw new RuntimeException("property client_id is not specified in the config.properties file");
  }

  public String getClietSecret(){
    String prop = properties.getProperty("client_secret");
    if(prop != null) return prop;
    else throw new RuntimeException("property client_secret is not specified in the config.properties file");
  }

  public String getGrantType(){
    String prop = properties.getProperty("grant_type");
    if(prop != null) return prop;
    else throw new RuntimeException("property grant_type is not specified in the config.properties file");
  }

  public String getRefreshToken(){
    String prop = properties.getProperty("refresh_token");
    if(prop != null) return prop;
    else throw new RuntimeException("property refresh_token is not specified in the config.properties file");
  }

  public String getUserId(){
    String userIdProperty = properties.getProperty("user_id");
    if(userIdProperty != null) return userIdProperty;
    else throw new RuntimeException("propertyh client_id is not specified in the config.properties file");
  }
}
