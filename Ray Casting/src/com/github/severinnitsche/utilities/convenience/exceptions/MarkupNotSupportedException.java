package com.github.severinnitsche.utilities.convenience.exceptions;

public class MarkupNotSupportedException extends Throwable {
  public MarkupNotSupportedException(String type) {
    super("Could not find Markup handler for: "+type);
  }
}
