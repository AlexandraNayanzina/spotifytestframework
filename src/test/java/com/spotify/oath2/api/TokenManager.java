package com.spotify.oath2.api;

import com.spotify.oath2.tests.Secrets;
import com.spotify.oath2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TokenManager {
  static Secrets clientId = Secrets.CLIENT_ID;
  static Secrets clientSecret = Secrets.CLIENT_SECRET;
  static Secrets refreshToken = Secrets.REFRESH_TOKEN;

  private static String access_token;
  private static Instant expiry_time;

  public static String getToken() {
    try {
      if (access_token == null || Instant.now().isAfter(expiry_time)) {
        System.out.println("Renewing token ... ");
        Response response = renewToken();
        access_token = response.path("access_token");
        int expiryDurationInSeconds = response.path("expires_in");
        expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
      } else {
        System.out.println("Token is not required renewal");
      }
    }
    catch (Exception e) {
      throw new RuntimeException("Failed to renew token");
    }
    return access_token;
  }


  private static Response renewToken() {

    HashMap<String, String> formParams = new HashMap<>();
    formParams.put("client_id", ConfigLoader.getInstance().getClientId());
    formParams.put("client_secret", ConfigLoader.getInstance().getClietSecret());
    formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
    formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

    Response response = BaseApi.postAccount(formParams);

    if (response.statusCode() != 200) {
      throw new RuntimeException("Renew token is failed");
    }
    return response;
  }
}
