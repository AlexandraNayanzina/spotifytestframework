package com.spotify.oath2.api.applicationApi;

import com.spotify.oath2.api.BaseApi;
import com.spotify.oath2.pojo.Playlist;

import static com.spotify.oath2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpecification;
import static com.spotify.oath2.api.TokenManager.getToken;
import static io.restassured.RestAssured.given;

import com.spotify.oath2.tests.Secrets;
import io.restassured.response.Response;

public class PlaylistApi {

  Secrets user_id = Secrets.USER_ID;

  public static Response post(Playlist requestPlaylistBody, String user_id) {

    return BaseApi.post("/users/" + user_id + "/playlists", getToken(), requestPlaylistBody, user_id);

  }


  public static Response get(String playlistId) {

    return BaseApi.get("/playlists/" + playlistId, getToken(), playlistId);

  }

  public static Response get(String playlistId, String token) {

    return given(getRequestSpecification())
        .header("Authorization", token)
        .when()
        .get("/playlists/" + playlistId)
        .then()
        .spec(getResponseSpecification())
        .extract()
        .response();
  }

  public static Response put(Playlist requestPlaylistBody, String playlistId){

    return given(getRequestSpecification())
        .header("Authorization", "Bearer " + getToken())
        .body(requestPlaylistBody)
        .when()
        .put("/playlists/" + playlistId)
        .then()
        .spec(getResponseSpecification())
        .extract()
        .response();

  }
}
