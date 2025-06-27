package com.spotify.oath2.tests;

import com.spotify.oath2.api.StatusCode;
import com.spotify.oath2.api.applicationApi.PlaylistApi;
import com.spotify.oath2.pojo.Playlist;
import com.spotify.oath2.pojo.errormessages.InvalidToken;
import com.spotify.oath2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static com.spotify.oath2.utils.FakerUtils.generateName;
import static com.spotify.oath2.utils.FakerUtils.generateDescription;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@Epic("Spotify Oath 2.0")
@Feature("Spotify API")
public class PlaylistTests {

  public Playlist playlistBuilder(String name, String description, boolean _public){
    Playlist playlist = Playlist.builder()
            .name(name)
                .description(description)
                    .isPublic(_public)
                        .build();
    return playlist;
  }

  @Step
  public void assertPlaylist(Playlist requestPlaylist, Playlist responsePlaylist){
    assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
    assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
    assertThat(requestPlaylist.getIsPublic(), equalTo(responsePlaylist.getIsPublic()));
  }

  @Step
  public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
    assertThat(actualStatusCode, equalTo(expectedStatusCode));
  }

  @Step
  public void assertError(InvalidToken responseError, int errorMessageStatusCode, String errorMessage){
    assertThat(responseError.getError().getStatus(), equalTo(errorMessageStatusCode));
    assertThat(responseError.getError().getMessage(), equalTo(errorMessage));

  }
  @Description("User should be able to create a playlist")
  @Test(description = "positive test - create a playlist")
  @Severity(CRITICAL)
  @Owner("Alexandra N")
  @Link(name = "TestWebsite", url = "https://google.com/")
  @Issue("AUTH-123")
  @TmsLink("TMS-456")
  public void create_playlist_test() {
    Playlist requestPlaylistBody = playlistBuilder(
        generateName(),
        generateDescription(),
        false);
    Response response= PlaylistApi.post(requestPlaylistBody);
    assertStatusCode(response.statusCode(), 201);
    assertPlaylist(requestPlaylistBody, response.as(Playlist.class));
  }

  @Test(description = "positive test - get a playlist")
  @Description("User should be able to get a playlist")
  @Severity(CRITICAL)
  @Owner("Alexandra N")
  @Link(name = "TestWebsite", url = "https://google.com/")
  @Issue("AUTH-124")
  @TmsLink("TMS-457")
  public void get_playlist_test() {
    Playlist requestPlaylistBody = playlistBuilder(
        "Playlist1 - created from RestAssured",
        "New playlist description",
        true);
    Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlayListId());
    assertStatusCode(response.statusCode(), StatusCode.CODE_200.getCode());
    assertPlaylist(requestPlaylistBody, response.as(Playlist.class));
  }

  @Description("User should be able to update a playlist")
  @Test(description = "positive test - update a playlist")
  @Severity(CRITICAL)
  @Owner("Alexandra N")
  @Link(name = "TestWebsite", url = "https://google.com/")
  @Issue("AUTH-125")
  @TmsLink("TMS-458")
  public void update_playlist() {
    Playlist requestPlaylistBody = playlistBuilder(
        "UPDATED Playlist1 - created from Postman",
        "New playlist description",
        false);

    Response response = PlaylistApi.put(requestPlaylistBody, DataLoader.getInstance().updatePlayListId());
    assertStatusCode(response.statusCode(), StatusCode.CODE_200.getCode());

  }

  @Description("User should NOT be able to create a playlist with empty name")
  @Test(description = "negative test - creating a playlist with empty name")
  @Severity(CRITICAL)
  @Owner("Alexandra N")
  @Link(name = "TestWebsite", url = "https://google.com/")
  @Issue("AUTH-126")
  @TmsLink("TMS-459")
  public void negative_create_playlist_with_empty_name_test() {

    Playlist requestPlaylistBodyEmptyName = Playlist.builder()
        .name("")
        .description(generateDescription())
        .isPublic(false)
        .build();

    Response response = PlaylistApi.post(requestPlaylistBodyEmptyName);
    assertError(response.as(InvalidToken.class), StatusCode.CODE_400.getCode(),StatusCode.CODE_400.getMsg());
  }

  @Description("User should NOT be able to get a playlist using an invalid token")
  @Test(description = "negative test - get a playlist with an invalid token")
  @Severity(CRITICAL)
  @Owner("Alexandra N")
  @Link(name = "TestWebsite", url = "https://google.com/")
  @Issue("AUTH-127")
  @TmsLink("TMS-460")
  public void negative_invalid_token_test() {
    Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlayListId(), "123");
    assertError(response.as(InvalidToken.class),  StatusCode.CODE_400_1.getCode(),StatusCode.CODE_400_1.getMsg());

  }
}
