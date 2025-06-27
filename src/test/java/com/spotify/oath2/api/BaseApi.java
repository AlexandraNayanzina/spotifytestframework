package com.spotify.oath2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oath2.api.Routs.API;
import static com.spotify.oath2.api.Routs.TOKEN;
import static com.spotify.oath2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class BaseApi {

  public static Response post(String path, String token, Object requestPlaylistBody) {
    return given(getRequestSpecification())
        .auth().oauth2(token)
        .body(requestPlaylistBody)
        .when()
        .post(path)
        .then()
        .spec(getResponseSpecification())
        .extract()
        .response();
  }

  public static Response postAccount(HashMap<String, String> formParams){
    return given()
              .spec(getAccountRequestSpecification())
              .formParams(formParams)
           .when()
              .post(API + TOKEN)
           .then()
              .spec(SpecBuilder.getResponseSpecification())
              .extract()
              .response();
  }

  public static Response get(String path, String token, String playlistId) {

    return given(getRequestSpecification())
        .auth().oauth2(token)
        .when()
        .get(path)
        .then()
        .spec(getResponseSpecification())
        .extract()
        .response();
  }


  public static Response put(String path, String token, Object requestPlaylistBody, String playlistId) {
    return BaseApi.put(path, token, playlistId, playlistId);
  }

}
