package com.centafrique.lancelinvestment.user_webiste.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

public class FileData {

  private String name;
  private String url;

  public FileData(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}