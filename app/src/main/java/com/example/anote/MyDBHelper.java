package com.example.anote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    NotesActivity notesActivity = new NotesActivity();
    private static final String DATABASE_NAME = "Note_DB_NEW";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "NoteInfo";
    private static final String KEY_ID = "ID";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_DETAIL_NOTE = "DETAIL_NOTE";
    private static final String KEY_DATE = "DATE";
    Context context;

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT ," + KEY_DETAIL_NOTE + " TEXT,"+KEY_DATE+" TEXT"  +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        if(i1>i){
//            //CREATE TABLE Images (id INTEGER PRIMARY KEY AUTOINCREMENT,note_id INTEGER,image BLOB,FOREIGN KEY (note_id)REFERENCES Notes(id));
//            sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_IMAGES +"("+KEY_IMAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NOTE_ID+" INTEGER,"+KEY_IMAGE+" BLOB,"+"FOREIGN KEY"+" ("+KEY_NOTE_ID+")"+" REFERENCES "+ TABLE_NAME+"("+KEY_ID+")"+")");
//        }



    }



    public void Insert_Values(String title, String note, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_DETAIL_NOTE, note);
        values.put(KEY_DATE,date);

        db.insert(TABLE_NAME, null, values);
        //db.close();

    }

    public void Delete_Database() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE  FROM " + TABLE_NAME);
        //db.close();
    }

    public ArrayList<MyDBModel> FetchData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<MyDBModel> arrayofnote = new ArrayList<MyDBModel>();

        while (cursor.moveToNext()) {
            MyDBModel model = new MyDBModel();
            model.id = cursor.getInt(0);
            model.title = cursor.getString(1);
            model.note = cursor.getString(2);
            model.date = cursor.getString(3);
            arrayofnote.add(model);
        }
        //db.close();
        cursor.close();
        return arrayofnote;
    }
    public void UpdateDb(MyDBModel dbModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE,dbModel.title);
        cv.put(KEY_DETAIL_NOTE,dbModel.note);
        db.update(TABLE_NAME,cv,KEY_ID+"="+dbModel.id,null);
        //db.close();
    }

    public void deleteRow(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+KEY_ID+" = "+id);
    }


}
