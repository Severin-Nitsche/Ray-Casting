package com.github.severinnitsche.utilities.convenience.Exceptions;

public class IlkNotFoundException extends Throwable {
  public IlkNotFoundException(String id) {
    super("Could not locate ilk with id:"+id);
  }
}
