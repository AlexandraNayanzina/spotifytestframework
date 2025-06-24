package com.spotify.oath2.api;

import com.spotify.oath2.tests.Secrets;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oath2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

import static com.spotify.oath2.api.Routs.API;
import static com.spotify.oath2.api.Routs.TOKEN;

public class BaseApi {

  Secrets user_id = Secrets.USER_ID;

  public static Response post(String path, String token, Object requestPlaylistBody) {
    return given(getRequestSpecification())
        .header("Authorization", "Bearer " + token)
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
        .header("Authorization", "Bearer " + token)
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
