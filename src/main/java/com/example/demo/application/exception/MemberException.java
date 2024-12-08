package com.example.demo.application.exception;

import lombok.Getter;


@Getter
public class MemberException extends RuntimeException {

  public MemberException(String message) {
      super(message);
    }

  public static class MemberErrorException extends MemberException {
    public MemberErrorException(String message) {
      super(message);
    }
  }

}
