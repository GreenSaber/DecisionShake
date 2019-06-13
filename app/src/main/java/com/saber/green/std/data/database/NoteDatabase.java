package com.saber.green.std.data.database;

import android.content.Context;
import android.os.AsyncTask;

import com.saber.green.std.R;
import com.saber.green.std.data.dao.NoteDao;
import com.saber.green.std.entity.Note;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback(context.getApplicationContext().getString(R.string.option_1), context.getApplicationContext().getString(R.string.option_2)))
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback(final String... stringList){
        return new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(instance).execute(stringList);
            }
        };
    }

    private static class PopulateDbAsyncTask extends AsyncTask<String, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase noteDatabase) {
            this.noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(String... strings) {
            noteDao.insert(new Note(strings[0]));
            noteDao.insert(new Note(strings[1]));
            return null;
        }

    }
}
