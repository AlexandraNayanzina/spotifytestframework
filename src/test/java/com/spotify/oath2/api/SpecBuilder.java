package com.spotify.oath2.api;

import com.spotify.oath2.tests.Secrets;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecBuilder {

  RequestSpecification requestSpecification;
  ResponseSpecification responseSpecification;
//  Secrets access_token = Secrets.ACCESS_TOKEN;
   static String access_token = "BQDTzp9zgBNawQqm_OJrOcp33E4OJLOqbK4U9DvaVgN3H2MaYBsr2qxBwCCKD7IeMx31JjL_-Ny0l_HLoGShSh2lK3TDuqRNRtlQGArYKU1lvRgtXS8ePUP9wj1UYj1iZ5sArwNtaV8B4Nq9pODmSzDrQbjtFkVX-VJhCZ4fWeIn5ZLM2legUfObWZiSajcIF38wAGn_H2kYCOvc_EXHfv-T0Ys_Ddav_rdaJJdS-3gOv8ozkahGhIqwRHwK7s3tkYupymcqGhNoOXtqt6QpRegl-ZXJxX2ZWGe0F5fQjS3V5fLMsSLUOGbn";

  public static RequestSpecification getRequestSpecification(){
    return new RequestSpecBuilder()
        .setBaseUri("https://api.spotify.com")
        .setBasePath("/v1")
        .addHeader("Authorization", "Bearer " + access_token)
        .setContentType(ContentType.JSON)
        .log(LogDetail.ALL)
        .build();
  }

  public static ResponseSpecification getResponseSpecification(){
    return new ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .build();
  }

}
