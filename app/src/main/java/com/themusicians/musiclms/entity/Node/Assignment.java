package com.themusicians.musiclms.entity.Node;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;
import com.themusicians.musiclms.entity.Attachment.AllAttachment;
import com.themusicians.musiclms.entity.Attachment.Attachment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Assignment.java
 *
 * @contributor
 * @author Nathan Tsai
 * @since Nov 2, 2020
 */
@IgnoreExtraProperties
public class Assignment extends Node {

  /** The type of node. Must be final */
  protected final String type = "assignment";

  /**
   * The fields for the Assignment
   *
   * <p>Public properties will be automatically saved by Firebase Private will not
   */
  protected List<String> assignees;

  protected String classId;

  protected long dueDate;

  /** Whether this assignment is completed */
  protected boolean assignmentComplete;

  protected Object assignmentCompleteTime;

  /** Whether this assignment is marked */
  protected boolean assignmentMarked;

  protected Object assignmentMarkedTime;

  /** Fields for the progress bar */
  protected int countOfTotalToDos = 0;

  protected int countOfDoneToDos = 0;

  protected List<String> userID;

//  protected Stack<String> todosID;

  /** The to do items attached to this assignment */
  protected Map<String, Boolean> toDoIds;

  // This must make the field name of toDoIds
  public static final String toDoIdsName = "toDoIds";

  /** The default constructor for Firebase */
  public Assignment() {
    super();
  }

  /** The constructor used to update an existing or to set an id */
  public Assignment(String id) {
    super(id);
  }

  /** Load an assignment from the database */
  /*
  public Assignment load(String entityId) {
    final String LOAD_ASSIGNMNET_TAG = "Load Assignment";
    final Assignment[] loadAssignment = new Assignment[1];
    entityDatabase.child( entityId )
        .addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            loadAssignment[0] = dataSnapshot.getValue(Assignment.class);
            Log.w(LOAD_ASSIGNMNET_TAG, "loadAssignment:onDataChange");
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(LOAD_ASSIGNMNET_TAG, "loadAssignment:onCancelled", databaseError.toException());
            // ...
          }
        });

    // Set Assignment to not load
    loadAssignment[0].enforceNew(false);

    return loadAssignment[0];
  }
  /**/

  /**
   * Implement getType()
   *
   * @return the type of node
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * Return the attachments that can be added to an assignment
   *
   * @return a list of attachment ids
   */
  @Override
  @Exclude
  public List<String> getAllowedAttachments() {
    return new LinkedList<String>() {
      {
        add("comment");
        add("file");
      }
    };
  }

  /**
   * Implement getBaseTable()
   *
   * @return the database table to store the entity
   */
  @Override
  public String getBaseTable() {
    return getEntityType() + "__" + getType();
  }

  /**
   * Get To Do Items location
   *
   * <p>Assumes assignment has an id
   */
  @Exclude
  public DatabaseReference getToDoItemsKeyQuery() {
    assert getId() != null;
    return getEntityDatabase()
        .child(getId())
        .child(toDoIdsName);
  }

  /**
   * Add assignment to user on save
   */
  @Override
  public void postSave() {
    // Add assignment to user
    if (getUid() != null) {
      User tempUser = new User(getUid());
      tempUser.getRelatedAssignmentDbReference().child(this.getId()).setValue(true);
    }
  }

  /**
   * Delete the assignment to dos
   */
  @Override
  public void delete() {
    if (getToDoIds() != null && !getToDoIds().isEmpty()) {
      ToDoItem tempToDoItem = new ToDoItem(null);
      for (String toDoId : getToDoIds().keySet()) {
        tempToDoItem.setId(toDoId);
        tempToDoItem.delete();
      }
    }

    super.delete();

    Log.w("Assignment", "Deleted Assignment: " + getId());
  }

  /**
   * Settings and Getters
   *
   * Required for Firebase Database
   */

  /**
   * The users the assignment is given to
   */
  public List<String> getAssignees() {
    if (assignees == null) {
      assignees = new LinkedList<>();
    }
    return assignees;
  }

  public void addAssignees(String assignees) {
    getAssignees().add(assignees);
  }

  public void setAssignees(List<String> assignees) {
    this.assignees = assignees;
  }

  /**
   * For when the assignment is completed by student
   */
  public boolean getAssignmentComplete() {
    return assignmentComplete;
  }

  public void setAssignmentComplete(boolean assignmentComplete) {
    this.assignmentComplete = assignmentComplete;

    if (getAssignmentComplete()) {
      setAssignmentCompleteTime(ServerValue.TIMESTAMP);
    }
  }

  public Object getAssignmentCompleteTime() {
    return assignmentCompleteTime;
  }

  public void setAssignmentCompleteTime(Object assignmentCompleteTime) {
    this.assignmentCompleteTime = assignmentCompleteTime;
  }

  /**
   * For when the teacher is finished marking the assignment
   */
  public boolean getAssignmentMarked() {
    return assignmentMarked;
  }

  public void setAssignmentMarked(boolean assignmentMarked) {
    this.assignmentMarked = assignmentMarked;
  }

  public Object getAssignmentMarkedTime() {
    return assignmentMarkedTime;
  }

  public void setAssignmentMarkedTime(Object assignmentMarkedTime) {
    this.assignmentMarkedTime = assignmentMarkedTime;
  }

  /**
   * Other
   */
  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
  }

  public long getDueDate() {
    return dueDate;
  }

  public void setDueDate(long dueDate) {
    this.dueDate = dueDate;
  }

  public Map<String, Boolean> getToDoIds() {
    if (toDoIds == null) {
      toDoIds = new HashMap<>();
    }
    return toDoIds;
  }

  public void addToDoId(String toDoId) {
    getToDoIds().put(toDoId, true);
  }

  public void addToDoId(String toDoId, Boolean completed) {
    getToDoIds().put(toDoId, completed);
  }

  public void setToDoIds(Map<String, Boolean> toDoIds) {
    this.toDoIds = toDoIds;
  }

  /**
   * For calculating the student's progress
   */
  public void setCountOfTotalToDos(){ this.countOfTotalToDos = getToDoIds().size(); }

  public int getCountOfTotalToDos(){ return countOfTotalToDos; }

//  public void addCountOfDoneToDos(){ this.countOfDoneToDos++; }

  public int getCountOfDoneToDos(){
    countOfDoneToDos = 0;

    for (Boolean isChecked : getToDoIds().values()) {
      if (isChecked) {
        countOfDoneToDos++;
      }
    }

    return countOfDoneToDos;
  }

//  public void pushToDos(String todoid) {
//    if (this.todosID == null) {
//      this.todosID = new Stack<String>();
//    }
//    this.todosID.push(todoid);
//  }

}
