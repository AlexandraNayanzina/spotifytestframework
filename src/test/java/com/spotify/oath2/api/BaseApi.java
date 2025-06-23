package com.spotify.oath2.api;

import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.tests.Secrets;
import io.restassured.response.Response;

import static com.spotify.oath2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class BaseApi {

  Secrets user_id = Secrets.USER_ID;
//  Secrets access_token = Secrets.ACCESS_TOKEN;
  static String access_token = "";


  public static Response post(String path, String token, Object requestPlaylistBody, String user_id) {
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
