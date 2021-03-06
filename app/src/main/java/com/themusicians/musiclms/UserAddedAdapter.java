package com.themusicians.musiclms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.themusicians.musiclms.chat.Chat;
import com.themusicians.musiclms.entity.Node.User;
import java.util.ArrayList;
import java.util.List;

/**
 * The adapter for the AddedUsers page
 *
 * @author Jerome Lau
 * @since Nov 25, 2020
 */

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class UserAddedAdapter extends RecyclerView.Adapter<UserAddedAdapter.MyViewHolder> {

  ArrayList<User> list;
  ArrayList<String> addedList = new ArrayList<>();
  FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
  User currUser = new User(currentUser.getUid());
  Context context;

  /**
   * Initialize variables
   *
   * @param list ListView of added user list
   * @param c Context of page
   */
  public UserAddedAdapter(@NonNull ArrayList<User> list, Context c) {
    context = c;
    this.list = list;
  }

  public UserAddedAdapter(Context context, List<User> aUsers) {}

  // Function to tell the class about the Card view
  // which the data will be shown
  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.user_card_holder_added, parent, false);
    return new MyViewHolder(view);
  }
  // Function to bind the view in Card view with data in
  // User class
  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.userName2.setText(list.get(position).getName());
    holder.userEmail2.setText(list.get(position).getEmail());
    holder.userRole2.setText(list.get(position).getRole());
    holder.userChat.setVisibility(View.VISIBLE);
    holder.onClick(position);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  // Sub Class to create references of the views in Crad
  // view (here "person.xml")
  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView userName2, userEmail2, userRole2;
    Button userChat, viewUser, deleteUser;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      userName2 = itemView.findViewById(R.id.searchName2);
      userEmail2 = itemView.findViewById(R.id.searchEmail2);
      userRole2 = itemView.findViewById(R.id.searchRole2);
      userChat = itemView.findViewById(R.id.addedChat);
      viewUser = itemView.findViewById(R.id.addedViewUser);
      deleteUser = itemView.findViewById(R.id.addedDelete);
    }

    // Add button onClick
    public void onClick(int position) {
      userChat.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              callChat(position);
            }
          });
      viewUser.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              callViewUser(position);
            }
          });
      deleteUser.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              callDeleteUser(position);
            }
          });
    }
  }

  /**
   * Redirects user to chat page
   *
   * @param position
   */
  public void callChat(int position) {
    currUser
        .getEntityDatabase()
        .child(currentUser.getUid())
        .addListenerForSingleValueEvent(
            new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = snapshot.getValue(User.class);
                currUser.setRecentText(list.get(position).getId());
                currUser.save();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {}
            });
    Intent toChat = new Intent(context, Chat.class);
    context.startActivity(toChat);
  }

  /** On view user, view other users profile */
  public void callViewUser(int position) {
    currUser
        .getEntityDatabase()
        .child(currentUser.getUid())
        .addListenerForSingleValueEvent(
            new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = snapshot.getValue(User.class);
                currUser.setViewUser(list.get(position).getId());
                currUser.save();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {}
            });

    Intent toView = new Intent(context, UserSearchView.class);
    context.startActivity(toView);
  }

  public void callDeleteUser(int position) {
    DatabaseReference delete =
        FirebaseDatabase.getInstance()
            .getReference()
            .child("node__user")
            .child(currentUser.getUid())
            .child("addedUsers");
    delete.addChildEventListener(
        new ChildEventListener() {
          @Override
          public void onChildAdded(
              @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if (!snapshot.getValue(String.class).equals(list.get(position).getId())) {
              addedList.add(snapshot.getValue(String.class));
            }
          }

          @Override
          public void onChildChanged(
              @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

          @Override
          public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

          @Override
          public void onChildMoved(
              @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

          @Override
          public void onCancelled(@NonNull DatabaseError error) {}
        });

    currUser
        .getEntityDatabase()
        .child(currentUser.getUid())
        .addListenerForSingleValueEvent(
            new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = snapshot.getValue(User.class);

                currUser.setAddedUsers(addedList);
                currUser.save();
                Toast.makeText(
                        context,
                        list.get(position).getName() + context.getString(R.string.user_deleted),
                        Toast.LENGTH_SHORT)
                    .show();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {}
            });
  }
}
