package com.example.faunaapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.R;

import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> {

    private List<Entry> entries;
    public EntriesAdapter(List<Entry> entries)
    {
        this.entries = entries;
    }

    @NonNull
    @Override
    public EntriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntriesAdapter.ViewHolder holder, int position) {
       holder.date.setText(entries.get(position).getDate());
       holder.time.setText(entries.get(position).getTime());
       holder.title.setText(entries.get(position).getTitle());
       holder.content.setText(entries.get(position).getHeading());
    }


    @Override
    public int getItemCount()
    {
        return entries.size();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, title, content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
        }
    }
}
