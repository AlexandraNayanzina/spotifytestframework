package com.spotify.oath2.tests;

import com.spotify.oath2.api.applicationApi.PlaylistApi;
import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.pojo.errormessages.InvalidToken;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

  Secrets user_id = Secrets.USER_ID;
  Secrets playlistId = Secrets.PLAYLIST_ID;

  @Test
  public void create_playlist_test() {
    Playlist requestPlaylistBody = Playlist.builder()
        .name("Playlist1 - created from RestAssured")
        .description("New playlist description")
        .isPublic(false)
        .build();

    Response response= PlaylistApi.post(requestPlaylistBody, user_id.getSecret());
    assertThat(response.statusCode(), equalTo(201));

    Playlist playlistResponseBody = response.as(Playlist.class);

    assertThat(requestPlaylistBody.getName(), equalTo(playlistResponseBody.getName()));
    assertThat(requestPlaylistBody.getDescription(), equalTo(playlistResponseBody.getDescription()));
    assertThat(requestPlaylistBody.getIsPublic(), equalTo(playlistResponseBody.getIsPublic()));

  }

  @Test
  public void get_playlist_test() {

    Response response = PlaylistApi.get(playlistId.getSecret());
    assertThat(response.statusCode(), equalTo(200));
    Playlist playlistResponse = response.as(Playlist.class);

    assertThat(playlistResponse.getName(), equalTo("Playlist1 - created from RestAssured"));
    assertThat(playlistResponse.getDescription(), equalTo("New playlist description"));
    assertThat(playlistResponse.getIsPublic(), equalTo(true));
  }

  @Test
  public void update_playlist() {

    String playlistId = "10GxXRxMF6mi9lfirYVa4j";

    Playlist requestPlaylistBody = Playlist.builder()
        .name("UPDATED Playlist1 - created from Postman")
        .description("New playlist description")
        .isPublic(false)
        .build();

    Response response = PlaylistApi.put(requestPlaylistBody, playlistId);
    assertThat(response.statusCode(), equalTo(200));

  }

  @Test
  public void negative_create_playlist_with_empty_name_test() {

    Playlist requestPlaylistBodyEmptyName = Playlist.builder()
        .name("")
        .description("New playlist description")
        .isPublic(false)
        .build();

    Response response = PlaylistApi.post(requestPlaylistBodyEmptyName, user_id.getSecret());
    assertThat(response.statusCode(), equalTo(400));
    InvalidToken invalidToken = response.as(InvalidToken.class);

    assertThat(invalidToken.getError().getMessage(), equalTo("Missing required field: name"));
    assertThat(invalidToken.getError().getStatus(), equalTo(400));
  }

  @Test
  public void negative_invalid_token_test() {


    Response response = PlaylistApi.get(playlistId.getSecret(), "123");
    InvalidToken invalidToken = response.as(InvalidToken.class);

    assertThat(invalidToken.getError().getMessage(), equalTo("Only valid bearer authentication supported"));
    assertThat(invalidToken.getError().getStatus(), equalTo(400));
  }
}
