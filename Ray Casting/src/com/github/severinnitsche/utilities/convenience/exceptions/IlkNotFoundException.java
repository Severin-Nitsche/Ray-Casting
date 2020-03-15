package com.github.severinnitsche.utilities.convenience.exceptions;

public class IlkNotFoundException extends Throwable {
  public IlkNotFoundException(String id) {
    super("Could not locate ilk with id:"+id);
  }
}
