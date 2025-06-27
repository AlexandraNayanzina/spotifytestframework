package com.spotify.oath2.api;

public enum StatusCode {

  CODE_200(200, ""),
  CODE_201(201, ""),
  CODE_400(400, "Missing required field: name"),
  CODE_400_1(400, "Only valid bearer authentication supported");


  private final int code;
  private final String message;

  StatusCode(int code, String message){
    this.code = code;
    this.message = message;
  }

  public int getCode(){
    return code;
  }


  public String getMsg(){
    return message;
  }
}
