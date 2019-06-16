package com.saber.green.std.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.saber.green.std.R;
import com.saber.green.std.entity.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private onEditItemClickListener onEditItemClickListener;
    private onDeleteItemClickListener onDeleteItemClickListener;
    private onTextItemClickListener onTextItemClickListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.titleTextView.setText(currentNote.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_text_view) TextView titleTextView;
        @BindView(R.id.edit_button_view) Button editButton;
        @BindView(R.id.delete_button_view) Button deleteButton;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onEditItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onEditItemClickListener.onEditItemClick(notes.get(position));
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onDeleteItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onDeleteItemClickListener.onDeleteItemClick(notes.get(position));
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onTextItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onTextItemClickListener.onTextItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface onEditItemClickListener {
        void onEditItemClick(Note note);
    }

    public interface onDeleteItemClickListener {
        void onDeleteItemClick(Note note);
    }

    public interface onTextItemClickListener {
        void onTextItemClick(Note note);
    }

    public void setOnEditItemClickListener(onEditItemClickListener listener) {
        this.onEditItemClickListener = listener;
    }

    public void setOnDeleteItemClickListener(onDeleteItemClickListener listener) {
        this.onDeleteItemClickListener = listener;
    }

    public void setOnTextItemClickListener(onTextItemClickListener listener) {
        this.onTextItemClickListener = listener;
    }
}
