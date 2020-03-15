package com.github.severinnitsche.utilities.math.exceptions;

public class MatrixConversionException extends Throwable {
  
  public MatrixConversionException(ExceptionType e) {
    super(e==ExceptionType.Dimensions?"Dimensions not Matching":e==ExceptionType.Point?"Matrix is a Point":e==ExceptionType.Vector?"Matrix is a Vector":nullException());
  }
  
  private static String nullException() {
    throw new NullPointerException();
  }
  
  public enum ExceptionType {
    Dimensions,Point,Vector
  }
  
}
