package com.example.anote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NotesActivity extends AppCompatActivity {

    MyDBHelper dbHelper;
    private static final int IMAGE_PICK_REQUEST = 100;
    Toolbar toolbar;
    EditText editTitle,editNote;
    ImageButton addNoteBtn;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar);
        editTitle = findViewById(R.id.titleedittext);
        editNote = findViewById(R.id.writenoteedittext);
        addNoteBtn = findViewById(R.id.noteaddbutton);
        date = findViewById(R.id.date);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Notes");
        }

        editNote.addTextChangedListener(textWatcher);
        SimpleDateFormat df = new SimpleDateFormat("KK:mm:ss a, dd/MM/yyyy", Locale.getDefault());
        String currentdatetime = df.format(new Date());
        date.setText(currentdatetime);


        dbHelper = new MyDBHelper(getApplicationContext());
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                dbHelper.Insert_Values(title,note,currentdatetime);

                Intent next = new Intent(NotesActivity.this,MainActivity.class);
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