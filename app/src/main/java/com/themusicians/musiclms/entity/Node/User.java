package com.themusicians.musiclms.entity.Node;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

/**
 * @file Assignment.java
 *
 * @contributor Jerome Lau
 * @author Nathan Tsai
 * @since Nov 2, 2020
 *     <p>--------------------------------
 * @todo Create Edit Form
 * @todo Create UI
 */
public class User extends Node {
  /**
   * Set the type of Node
   */
  protected String type = "user";

  /**
   * The user's email
   * @todo implement later
   */
  protected String email;

  /**
   * Whether the user is a teacher, student
   * @todo add parent
   */
  protected String role;

  /**
   * The fields for the default Node
   *
   * <p>Public properties will be automatically saved by Firebase Private will not
   */

  //  public List<String> assignees;
  //
  //  public int classId;

  /** The default constructor for Firebase + loadMultiple */
  public User() {
    super();
  }

  public User( String id ) {
    super( id );
  }

  /**
   * Implement getBaseTable()
   * @return the database table to store the entity
   */
  @Override
  public String getBaseTable() {
    return "node__user";
  }

  /**
   *
   */

  protected String sendText;
  protected String makeCall;
  protected String joinZoom;
  protected String scheduleZoom;
  protected String watchYoutube;
  protected String uploadYoutube;

  public String getSendText() {
    return sendText;
  }

  public void setSendText(String sendText) {
    this.sendText = sendText;
  }

  public String getMakeCall() {
    return makeCall;
  }

  public void setMakeCall(String makeCall) {
    this.makeCall = makeCall;
  }

  public String getJoinZoom() {
    return joinZoom;
  }

  public void setJoinZoom(String joinZoom) {
    this.joinZoom = joinZoom;
  }

  public String getScheduleZoom() {
    return scheduleZoom;
  }

  public void setScheduleZoom(String scheduleZoom) {
    this.scheduleZoom = scheduleZoom;
  }

  public String getWatchYoutube() {
    return watchYoutube;
  }

  public void setWatchYoutube(String watchYoutube) {
    this.watchYoutube = watchYoutube;
  }

  public String getUploadYoutube() {
    return uploadYoutube;
  }

  public void setUploadYoutube(String uploadYoutube) {
    this.uploadYoutube = uploadYoutube;
  }

}
