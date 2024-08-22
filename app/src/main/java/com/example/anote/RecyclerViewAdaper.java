package com.example.anote;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdaper extends RecyclerView.Adapter<RecyclerViewAdaper.ViewHolder> {
    Context context;
    ArrayList<MyDBModel> arrayList;
    int lastposition = -1;
    public RecyclerViewAdaper(Context context, ArrayList<MyDBModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclelayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdaper.ViewHolder holder, int position) {
        holder.editTitle.setText(arrayList.get(position).title);
        holder.date.setText(arrayList.get(position).date);
        holder.EditNote.setText(arrayList.get(position).note);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent next = new Intent(view.getContext(), NoteEdit.class);
            next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            next.putExtra("title",arrayList.get(holder.getAdapterPosition()).title);
            next.putExtra("note",arrayList.get(holder.getAdapterPosition()).note);
            next.putExtra("id",arrayList.get(holder.getAdapterPosition()).id);
            next.putExtra("pos",holder.getAdapterPosition());
            next.putExtra("date",arrayList.get(holder.getAdapterPosition()).date);
            view.getContext().startActivity(next);
            }

        });

        holder.itemdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper myDBHelper = new MyDBHelper(view.getContext());
                myDBHelper.deleteRow(arrayList.get(holder.getAdapterPosition()).id);
                deleteAnimation(holder.itemView);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.cardView.setVisibility(View.GONE);
                    }
                },1000);

            }
        });

        setAnimation(holder.itemView,position);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,editTitle,EditNote;
        CardView cardView;
        ImageButton itemdelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editTitle = itemView.findViewById(R.id.recyclelayouttitle);
            EditNote = itemView.findViewById(R.id.recyclelayoutcategory);
            date = itemView.findViewById(R.id.recycledate);
            cardView = itemView.findViewById(R.id.cardview);
            itemdelete = itemView.findViewById(R.id.itemdelete);
        }
    }
    private void setAnimation(View view,int position){
        if(position>lastposition) {
            Animation slideIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.startAnimation(slideIn);
            lastposition = position;
        }
    }
    private void deleteAnimation(View view){
        Animation slideout = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        slideout.setDuration(1000);
        view.startAnimation(slideout);
    }
}
