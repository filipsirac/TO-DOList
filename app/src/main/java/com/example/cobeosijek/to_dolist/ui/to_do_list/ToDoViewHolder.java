package com.example.cobeosijek.to_dolist.ui.to_do_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.common.DateTimeUtils;
import com.example.cobeosijek.to_dolist.models.ToDoModel;
import com.example.cobeosijek.to_dolist.ui.listeners.OnItemClickListener;

import java.util.Locale;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private OnItemClickListener onItemClickListener;
    private TextView label;
    private TextView date;
    private ToDoModel toDoModel;
    private TextView firstLetter;

    public ToDoViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        initView(itemView);
    }

    private void initView(View itemView) {
        label = itemView.findViewById(R.id.title_label);
        date = itemView.findViewById(R.id.date_label);
        firstLetter = itemView.findViewById(R.id.first_letter);
        ViewGroup toDoItem = itemView.findViewById(R.id.to_do);
        toDoItem.setOnClickListener(this);
        toDoItem.setOnLongClickListener(this);
    }

    public void setArticle(ToDoModel toDoModel) {
        if (toDoModel != null) {
            this.toDoModel = toDoModel;
            label.setText(toDoModel.getTitle());
            firstLetter.setText(String.format(Locale.getDefault(), itemView.getContext().getString(R.string.first_letter_format), toDoModel.getTitle().charAt(0)));
            date.setText(DateTimeUtils.getDateFormat(toDoModel.getDate()));
        }
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null && toDoModel != null) {
            onItemClickListener.onItemClick(toDoModel);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (onItemClickListener != null && toDoModel != null) {
            onItemClickListener.onItemLongClick(toDoModel.getId());
        }
        return true;
    }
}
