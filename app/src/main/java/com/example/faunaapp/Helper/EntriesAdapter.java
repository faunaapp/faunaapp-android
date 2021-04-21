package com.example.faunaapp.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.R;

import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> {

    List<Entry> entries;
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
    }

    @Override
    public int getItemCount()
    {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
