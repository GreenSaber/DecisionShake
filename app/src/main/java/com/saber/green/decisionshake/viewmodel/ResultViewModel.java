package com.saber.green.decisionshake.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.saber.green.decisionshake.data.repository.NoteRepository;
import com.saber.green.decisionshake.entity.Note;

public class ResultViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<Note> randomNote;

    public ResultViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public LiveData<Note> getRandomNote() {
        randomNote = (randomNote == null) ? noteRepository.getRandomNote() : randomNote;
        return randomNote;
    }

}
