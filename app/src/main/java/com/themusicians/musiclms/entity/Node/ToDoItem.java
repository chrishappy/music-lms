package com.themusicians.musiclms.entity.Node;

import com.google.firebase.database.IgnoreExtraProperties;
import com.themusicians.musiclms.entity.Attachment.Attachment;

import java.util.List;
import java.util.Map;

/**
 * @file Assignment.java
 *
 * @contributor
 * @author Nathan Tsai
 * @since Nov 13, 2020
 */
@IgnoreExtraProperties
public class ToDoItem extends Node {

  protected String type = "to_do_item";

  /**
   * The fields for the To Do Item
   *
   * <p>Public properties will be automatically saved by Firebase Private will not
   */
  protected long requireRecording;

  protected long toDoState;

  protected List<String> attachedAssignments;

  protected Object TimeCompleted;

  /** The default constructor for Firebase + loadMultiple */
  public ToDoItem() {
    super();
  }

  public ToDoItem(String id) {
    super(id);
  }
  
  /**
   * Implement getBaseTable()
   * @return the database table to store the entity
   */
  @Override
  public String getBaseTable() {
    return getEntityType() + "__" + getType();
  }

  /** Settings and Getters */
  public long getRequireRecording() {
    return requireRecording;
  }

  public void setRequireRecording(long requireRecording) {
    this.requireRecording = requireRecording;
  }

  public long getToDoState() {
    return toDoState;
  }

  public void setToDoState(long toDoState) {
    this.toDoState = toDoState;
  }

  public List<String> getAttachedAssignments() {
    return attachedAssignments;
  }

  public void setAttachedAssignments(List<String> attachedAssignments) {
    this.attachedAssignments = attachedAssignments;
  }

  public Object getTimeCompleted() {
    return TimeCompleted;
  }

  public void setTimeCompleted(Object timeCompleted) {
    TimeCompleted = timeCompleted;
  }

  @Override
  public String getType() {
    return type;
  }
}