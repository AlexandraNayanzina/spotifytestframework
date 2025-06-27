package com.spotify.oath2.api;

import com.spotify.oath2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager {

  private static String access_token;
  private static Instant expiry_time;

  public synchronized static String getToken() {
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
    } catch (Exception e) {
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
