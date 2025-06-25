package com.spotify.oath2.tests;

import com.spotify.oath2.api.applicationApi.PlaylistApi;
import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.pojo.errormessages.InvalidToken;
import com.spotify.oath2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

  public Playlist playlistBuilder(String name, String description, boolean _public){
    Playlist playlist = Playlist.builder()
            .name(name)
                .description(description)
                    .isPublic(_public)
                        .build();
    return playlist;
  }

  public void assertPlaylist(Playlist requestPlaylist, Playlist responsePlaylist){
    assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
    assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
    assertThat(requestPlaylist.getIsPublic(), equalTo(responsePlaylist.getIsPublic()));
  }

  public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
    assertThat(actualStatusCode, equalTo(expectedStatusCode));
  }

  public void assertError(InvalidToken responseError, int errorMessageStatusCode, String errorMessage){
    assertThat(responseError.getError().getStatus(), equalTo(errorMessageStatusCode));
    assertThat(responseError.getError().getMessage(), equalTo(errorMessage));

  }

  @Test
  public void create_playlist_test() {
    Playlist requestPlaylistBody = playlistBuilder(
        "Playlist1 - created from RestAssured",
        "New playlist description",
        false);
    Response response= PlaylistApi.post(requestPlaylistBody);
    assertStatusCode(response.statusCode(), 201);
    assertPlaylist(requestPlaylistBody, response.as(Playlist.class));
  }

  @Test
  public void get_playlist_test() {
    Playlist requestPlaylistBody = playlistBuilder(
        "Playlist1 - created from RestAssured",
        "New playlist description",
        true);
    Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlayListId());
    assertStatusCode(response.statusCode(), 200);
    assertPlaylist(requestPlaylistBody, response.as(Playlist.class));
  }

  @Test
  public void update_playlist() {
    Playlist requestPlaylistBody = playlistBuilder(
        "UPDATED Playlist1 - created from Postman",
        "New playlist description",
        false);

    Response response = PlaylistApi.put(requestPlaylistBody, DataLoader.getInstance().updatePlayListId());
    assertStatusCode(response.statusCode(), 200);

  }

  @Test
  public void negative_create_playlist_with_empty_name_test() {

    Playlist requestPlaylistBodyEmptyName = Playlist.builder()
        .name("")
        .description("New playlist description")
        .isPublic(false)
        .build();

    Response response = PlaylistApi.post(requestPlaylistBodyEmptyName);
    assertError(response.as(InvalidToken.class), 400,"Missing required field: name" );
  }

  @Test
  public void negative_invalid_token_test() {


    Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlayListId(), "123");
    assertError(response.as(InvalidToken.class), 400,"Only valid bearer authentication supported");

  }
}
