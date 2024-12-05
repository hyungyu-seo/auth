package com.example.demo.application.exception;

public class MemberException extends RuntimeException {
  private final int statusCode;

  public MemberException(String message, int statusCode) {
      super(message);
    this.statusCode = statusCode;
    }

  public static class MemberFoundException extends MemberException {
    public MemberFoundException() {
      super("중복!", 404);
    }
  }
}
