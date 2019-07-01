package com.saber.green.std.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.MobileAds;
import com.muddzdev.styleabletoast.StyleableToast;
import com.saber.green.std.R;
import com.saber.green.std.entity.Note;
import com.saber.green.std.ui.adapter.NoteAdapter;
import com.saber.green.std.viewmodel.NoteViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 101;
    public static final int EDIT_NOTE_REQUEST = 102;
    NoteViewModel noteViewModel;

    @BindView(R.id.button_add_note) Button buttonAddNote;
    @BindView(R.id.button_delete_all_notes) Button buttonDeleteAllNotes;
    @BindView(R.id.button_ready) Button buttonReady;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    final NoteAdapter adapter = new NoteAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-2121398048827766~4429870535");
        ButterKnife.bind(this);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        initRecyclerView();
        observeNoteChange();
        onNoteItemSwipe();
        onEditNoteItemClick();
        onTextItemClick();
        onDeleteNoteItemClick();
        onAddNoteButtonClick();
        onDeleteAllNotesButtonClick();
        onReadyButtonClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String option = data.getStringExtra(AddEditNoteActivity.EXTRA_OPTION);
            Note note = new Note(option);
            noteViewModel.insert(note);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                return;
            }
            String option = data.getStringExtra(AddEditNoteActivity.EXTRA_OPTION);
            Note note = new Note(option);
            note.setId(id);
            noteViewModel.update(note);
        }
    }

    public void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void observeNoteChange() {
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    public void onAddNoteButtonClick() {
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
    }

    public void onDeleteAllNotesButtonClick() {
        buttonDeleteAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteViewModel.deleteAllNotes();
                adapter.notifyItemRemoved(0);
            }
        });
    }

    public void onReadyButtonClick() {
        buttonReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteViewModel.getAllNotes().getValue().size() <2) {
                    StyleableToast.makeText(MainActivity.this, getString(R.string.toast_setup_2_options), Toast.LENGTH_LONG, R.style.customToastCenter).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ShakeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }

            }
        });
    }

    private void startEditActivity(Note note) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
        intent.putExtra(AddEditNoteActivity.EXTRA_OPTION, note.getTitle());
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    public void onEditNoteItemClick() {
        adapter.setOnEditItemClickListener(new NoteAdapter.onEditItemClickListener() {
            @Override
            public void onEditItemClick(Note note) {
                startEditActivity(note);
            }
        });
    }

    public void onTextItemClick() {
        adapter.setOnTextItemClickListener(new NoteAdapter.onTextItemClickListener() {
            @Override
            public void onTextItemClick(Note note) {
                startEditActivity(note);
            }
        });
    }

    public void onDeleteNoteItemClick() {
        adapter.setOnDeleteItemClickListener(new NoteAdapter.onDeleteItemClickListener() {
            @Override
            public void onDeleteItemClick(Note note) {
                noteViewModel.delete(note);
            }
        });
    }

    public void onNoteItemSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
