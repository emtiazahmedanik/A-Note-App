package com.example.anote;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NoteEdit extends AppCompatActivity {


    Toolbar toolbar;
    EditText editTitle,editNote;
    ImageButton addNoteBtn;
    TextView date;
    MyDBHelper myDBHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Toast.makeText(getApplicationContext(),"In noteedit",Toast.LENGTH_SHORT);
        toolbar = findViewById(R.id.toolbarn2);
        editTitle = findViewById(R.id.titleedittextn2);
        editNote = findViewById(R.id.writenoteedittextn2);
        addNoteBtn = findViewById(R.id.noteaddbuttonn2);
        date = findViewById(R.id.daten2);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Change Note");
        }

        Intent intent = this.getIntent();
        String title = intent.getStringExtra("title");
        String note = intent.getStringExtra("note");
        String datefromintent = intent.getStringExtra("date");
        long idofrow = intent.getLongExtra("id",-1);


        myDBHelper = new MyDBHelper(getApplicationContext());

        editTitle.setText(title);
        editNote.setText(note);
        editNote.computeScroll();
        date.setText(datefromintent);


        editNote.addTextChangedListener(textWatcher);



        MyDBModel dbModel = new MyDBModel();
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleup = editTitle.getText().toString().trim();
                String noteup = editNote.getText().toString().trim();

                dbModel.id = idofrow;
                dbModel.title = titleup;
                dbModel.note = noteup;
                myDBHelper.UpdateDb(dbModel);

                MainActivity.adapter.notifyDataSetChanged();
                Intent next = new Intent(NoteEdit.this,MainActivity.class);
                next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
            }
        });
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(!editNote.getText().toString().isEmpty())
                addNoteBtn.setVisibility(View.VISIBLE);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}