package com.example.learnmaps.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

  private LatLng position; // required field
  private String title; // required field
  private String snippet; // required field
  private int iconPicture;
  private UserPedagang userPedagang;

  public ClusterMarker(LatLng position, String title, String snippet, int iconPicture, UserPedagang userPedagang) {
    this.position = position;
    this.title = title;
    this.snippet = snippet;
    this.iconPicture = iconPicture;
    this.userPedagang = userPedagang;
  }

  public int getIconPicture() {
    return iconPicture;
  }

  public void setIconPicture(int iconPicture) {
    this.iconPicture = iconPicture;
  }

  public UserPedagang getUserPedagang() {
    return userPedagang;
  }

  public void setUser(UserPedagang userPedagang) {
    this.userPedagang = userPedagang;
  }

  public void setPosition(LatLng position) {
    this.position = position;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setSnippet(String snippet) {
    this.snippet = snippet;
  }

  public LatLng getPosition() {
    return position;
  }

  public String getTitle() {
    return title;
  }

  public String getSnippet() {
    return snippet;
  }
}