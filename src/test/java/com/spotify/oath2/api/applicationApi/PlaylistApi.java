package com.spotify.oath2.api.applicationApi;

import com.spotify.oath2.api.BaseApi;
import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.spotify.oath2.api.Routs.PLAYLISTS;
import static com.spotify.oath2.api.Routs.USERS;
import static com.spotify.oath2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpecification;
import static com.spotify.oath2.api.TokenManager.getToken;
import static io.restassured.RestAssured.given;

public class PlaylistApi {


  public static Response post(Playlist requestPlaylistBody) {
    return BaseApi.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + "/playlists", getToken(), requestPlaylistBody);
  }


  public static Response get(String playlistId) {
    return BaseApi.get(PLAYLISTS + "/" + playlistId, getToken(), playlistId);
  }

  public static Response get(String playlistId, String token) {

    return given(getRequestSpecification())
        .header("Authorization", token)
        .when()
        .get(PLAYLISTS + "/" + playlistId)
        .then()
        .spec(getResponseSpecification())
        .extract()
        .response();
  }

  public static Response put(Playlist requestPlaylistBody, String playlistId) {

    return given(getRequestSpecification())
        .header("Authorization", "Bearer " + getToken())
        .body(requestPlaylistBody)
        .when()
        .put(PLAYLISTS + "/" + playlistId)
        .then()
        .log().all()
        .spec(getResponseSpecification())
        .extract()
        .response();

  }
}
