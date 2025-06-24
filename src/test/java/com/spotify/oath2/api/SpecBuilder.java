package com.spotify.oath2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oath2.api.Routs.*;


public class SpecBuilder {

  RequestSpecification requestSpecification;
  ResponseSpecification responseSpecification;

  public static RequestSpecification getRequestSpecification(){
    return new RequestSpecBuilder()
        .setBaseUri("https://api.spotify.com")
        .setBasePath(BASE_PATH)
        .setContentType(ContentType.JSON)
        .log(LogDetail.ALL)
        .build();
  }

  public static RequestSpecification getAccountRequestSpecification(){
    return new RequestSpecBuilder()
        .setBaseUri("https://accounts.spotify.com")
        .setContentType(ContentType.URLENC)
        .log(LogDetail.ALL)
        .build();
  }

  public static ResponseSpecification getResponseSpecification(){
    return new ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .build();
  }

}
