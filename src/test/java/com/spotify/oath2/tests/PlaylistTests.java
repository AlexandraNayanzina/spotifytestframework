package com.spotify.oath2.tests;

import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.pojo.errormessages.ErrorMessage;
import com.spotify.oath2.pojo.errormessages.InvalidToken;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.spotify.oath2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oath2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import com.spotify.oath2.api.SpecBuilder.*;

public class PlaylistTests {

  Secrets user_id = Secrets.USER_ID;

  @Test
  public void create_playlist_test(){
    Playlist requestPlaylistBody = Playlist.builder()
        .name("Playlist1 - created from RestAssured")
        .description("New playlist description")
        .isPublic(false)
        .build();

    Playlist playlistResponseBody = given(getRequestSpecification())
        .body(requestPlaylistBody)
    .when()
        .post("/users/" + user_id.getSecret() + "/playlists")
    .then()
        .spec(getResponseSpecification())
        .log().all()
        .assertThat()
        .statusCode(201)
        .extract()
        .response()
        .as(Playlist.class);
    assertThat(requestPlaylistBody.getName(), equalTo(playlistResponseBody.getName()));
    assertThat(requestPlaylistBody.getDescription(), equalTo(playlistResponseBody.getDescription()));
    assertThat(requestPlaylistBody.getIsPublic(), equalTo(playlistResponseBody.getIsPublic()));

  }

  @Test
  public void get_playlist_test(){

    String playlistId = "6nEyasrhm33s3PVi4KYQGO";

    Playlist playlistResponse = given(getRequestSpecification())
    .when()
        .get("/playlists/" + playlistId)
    .then()
        .spec(getResponseSpecification())
        .assertThat()
        .statusCode(200)
        .extract()
        .response()
        .as(Playlist.class);

    assertThat(playlistResponse.getName(), equalTo("Playlist1 - created from RestAssured"));
    assertThat(playlistResponse.getDescription(), equalTo("New playlist description"));
    assertThat(playlistResponse.getIsPublic(), equalTo(true));
  }

  @Test
  public void update_playlist(){

    String playlistId = "10GxXRxMF6mi9lfirYVa4j";

    Playlist requestPlaylistBody = Playlist.builder()
        .name("UPDATED Playlist1 - created from Postman")
        .description("New playlist description")
        .isPublic(false)
        .build();

    given(getRequestSpecification())
        .body(requestPlaylistBody)
    .when()
        .put("/playlists/" + playlistId)
    .then()
        .spec(getResponseSpecification())
        .assertThat()
        .statusCode(200);

  }

  @Test
  public void negative_create_playlist_with_empty_name_test() {

    Playlist requestPlaylistBody = Playlist.builder()
        .name("")
        .description("New playlist description")
        .isPublic(false)
        .build();

    InvalidToken invalidToken = given(getRequestSpecification())
        .body(requestPlaylistBody)
        .when()
        .post("/users/" + user_id.getSecret() + "/playlists")
        .then()
        .log().all()
        .extract()
        .response()
        .as(InvalidToken.class);
    assertThat(invalidToken.getError().getMessage(), equalTo("Missing required field: name"));
    assertThat(invalidToken.getError().getStatus(), equalTo(400));
  }

  @Test
  public void negative_invalid_token_test(){

    String playlistId = "10GxXRxMF6mi9lfirYVa4j";

    InvalidToken invalidToken = given()
        .baseUri("https://api.spotify.com")
        .basePath("/v1")
        .header("Authorization", "***")
        .contentType(ContentType.JSON)
        .log().all()
    .when()
        .get("/playlists/" + playlistId)
    .then()
        .spec(getResponseSpecification())
        .log().all()
        .extract()
        .response()
        .as(InvalidToken.class);

    assertThat(invalidToken.getError().getMessage(), equalTo("Only valid bearer authentication supported"));
    assertThat(invalidToken.getError().getStatus(), equalTo(400));
  }
}
