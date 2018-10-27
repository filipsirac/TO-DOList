package com.example.cobeosijek.to_dolist.ui.to_do_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.models.ToDoModel;
import com.example.cobeosijek.to_dolist.ui.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoViewHolder>{

    private final List<ToDoModel> toDoList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(List<ToDoModel> items) {
        toDoList.clear();
        toDoList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_to_do, parent, false);
        return new ToDoViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        final ToDoModel toDo = toDoList.get(position);
        holder.setArticle(toDo);
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public void removeToDo(int toDoId) {
        for (int i = 0; i < toDoList.size(); i++) {
            if (toDoList.get(i).getId() == toDoId) {
                int position = toDoList.indexOf(toDoList.get(i));
                toDoList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }
}
