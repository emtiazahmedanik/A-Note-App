package com.example.anote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageButton imageButton;
    public static RecyclerViewAdaper adapter;
    MyDBHelper myDBHelper=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.sstatusbar));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        floatingActionButton = findViewById(R.id.floatingbutton);
        floatingActionButton.setRippleColor(ColorStateList.valueOf(Color.parseColor("#FF224466")));
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF70C674")));
        toolbar = findViewById(R.id.toolbarMain);
        recyclerView = findViewById(R.id.recycleview);
        imageButton = findViewById(R.id.deletedb);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("All Notes");

        if(myDBHelper==null)
            myDBHelper = new MyDBHelper(MainActivity.this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, NotesActivity.class);
                next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete All")
                                .setMessage("Do you want to delete all the notes ?")
                                        .setIcon(R.drawable.baseline_delete_24)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        myDBHelper.Delete_Database();
                                                        adapter.notifyDataSetChanged();
                                                        recyclerView.setVisibility(View.GONE);

                                                    }
                                                })
                                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                            }
                                                        });
                builder.show();


            }
        });




        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ArrayList<MyDBModel> arrayList = new ArrayList<>();
        arrayList = myDBHelper.FetchData();


        adapter = new RecyclerViewAdaper(MainActivity.this,arrayList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);






    }
}