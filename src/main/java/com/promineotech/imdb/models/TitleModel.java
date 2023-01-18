package com.promineotech.imdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TitleModel {
  private final int MINIMUM_TITLE_RELEASE_YEAR = 1874;

  private String id;
  private String name;
  private int releasedYear;
  
  public TitleModel(String id, String name, int releasedYear) {
    setId(id);
    setName(name);
    setReleasedYear(releasedYear);
  }
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getReleasedYear() {
    return releasedYear;
  }
  public void setReleasedYear(int releasedYear) {
    this.releasedYear = releasedYear;
  }
  
  /**
   * Checks to see if the title is valid.
   * @param title The title to validate
   * @return True if value, otherwise returns false.
   */
  @JsonIgnore
  public boolean isValid() {
    if ((getId() == null || getId().isEmpty())) {
      return false;
    }
    if ((getName() == null) || (getName().isEmpty())) {
      return false;
    }
    if (getReleasedYear() < MINIMUM_TITLE_RELEASE_YEAR) {
      return false;
    }
    return true;
  }
}
