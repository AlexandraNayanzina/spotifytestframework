package com.spotify.oath2.api.applicationApi;

import com.spotify.oath2.api.BaseApi;
import com.spotify.oath2.pojo.Playlist;

import static com.spotify.oath2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

import com.spotify.oath2.tests.Secrets;
import io.restassured.response.Response;

public class PlaylistApi {

  Secrets user_id = Secrets.USER_ID;
//  Secrets access_token = Secrets.ACCESS_TOKEN;
  static String access_token = "BQDck572N_0iR_lx7xLcsI0Dkl3GSLOIRz964sNR12UvHP06QRiGV-j62F8XwyJ_AsbBWhgyWMY3cAeyzeTvp_bneY6PvCLMqPhLS4T8ZldKab0rT0r8SircQbGyTdhoYtFk1ezwwF2wYupfKR7dtKZlH6xyVps3_tvmzDd8ILYYJFImCY0S4RL5OEeoJLEPHzL20_s_F1RS-SY52sk5rxL7JpnJ3mXxi7VmpgP6TH7BOwZQK9cAEV0JvysLaHft6EBh4NHPnoYPrD-p6Pj6rPMuIHsxvYA8X5eb1t2HDo-FcDrV9r2vqmvF";


  public static Response post(Playlist requestPlaylistBody, String user_id) {

    return BaseApi.post("/users/" + user_id + "/playlists", access_token, requestPlaylistBody, user_id);

  }


  public static Response get(String playlistId) {

    return BaseApi.get("/playlists/" + playlistId, access_token, playlistId);

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
        .header("Authorization", "Bearer " + access_token)
        .body(requestPlaylistBody)
        .when()
        .put("/playlists/" + playlistId)
        .then()
        .spec(getResponseSpecification())
        .extract()
        .response();

  }
}
