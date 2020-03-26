package com.example.contextual_actionmode_with_delete_function;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    ArrayList<Image> list = new ArrayList<>();
    MainActivity mainActivity;

    public ImageAdapter(ArrayList<Image> list, MainActivity mainActivity) {
        this.list = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v, mainActivity);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImageId());
        holder.textView.setText(list.get(position).getImageName());

        if(!MainActivity.isContextualModeEnabled){
            holder.checkBox.setVisibility(View.GONE);
        }
        else{
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
        View view;

        public MyViewHolder(@NonNull View itemView, MainActivity mainActivity) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ImageView);
            textView = itemView.findViewById(R.id.ImageTitle);
            checkBox = itemView.findViewById(R.id.checkBox);
            view = itemView;
            view.setOnLongClickListener(mainActivity);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mainActivity.MakeSelection(v, getAdapterPosition());
        }
    }

    public void RemoveItem(ArrayList<Image> selectionList) {
        for(int i=0;i<selectionList.size();i++){
            list.remove(selectionList.get(i));
            notifyDataSetChanged();
        }
    }
}
