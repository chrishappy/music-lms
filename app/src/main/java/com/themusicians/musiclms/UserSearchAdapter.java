package com.themusicians.musiclms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themusicians.musiclms.entity.Node.User;

import java.util.ArrayList;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.MyViewHolder>{

  ArrayList<User> list;
  public UserSearchAdapter(ArrayList<User> list){
    this.list = list;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_holder,parent,false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.name.setText(list.get(position).getName());
    holder.email.setText(list.get(position).getEmail());
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name, email;
    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.searchName);
      email = itemView.findViewById(R.id.searchEmail);
    }
  }
}
