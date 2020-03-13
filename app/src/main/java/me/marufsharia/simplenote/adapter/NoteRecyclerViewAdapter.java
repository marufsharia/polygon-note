package me.marufsharia.simplenote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.marufsharia.simplenote.R;
import me.marufsharia.simplenote.model.Note;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteVH> implements Filterable {
    List<Note> noteList;
    List<Note> fullNoteList;
    Context context;
    private Filter noteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Note> filteredList = new ArrayList<>();
            
            if (constraint == null || constraint.length() == 0) {
                Log.d("tag", constraint.toString());
                filteredList.addAll(fullNoteList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                
                for (Note item : fullNoteList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            
            FilterResults results = new FilterResults();
            results.values = filteredList;
            
            return results;
        }
        
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteList.clear();
            noteList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    
    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_row, parent, false);
      return new NoteVH(view);
      
    }
    
    @Override
    public void onBindViewHolder(@NonNull final NoteVH holder, final int position) {
    
        holder.txtTitle.setText(noteList.get(position).getTitle());
        holder.txtCategory.setText( String.valueOf(noteList.get(position).getCategory()));
        holder.txtCreated_at.setText(noteList.get(position).getCreated_at());
    
        holder.itemDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.itemDot);
                //inflating menu from xml resource
                popup.inflate(R.menu.note_item_popup_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnuEditNote:
                                Toast.makeText(context, "Edit : " + noteList.get(position).getId(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
        
        
        
    }
    
    @Override
    public int getItemCount() {
        return noteList.size();
    }
    
    public NoteRecyclerViewAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        fullNoteList = new ArrayList<>(noteList);
    }
    
    @Override
    public Filter getFilter() {
        return noteFilter;
    }
    
    
    
    public static class NoteVH extends RecyclerView.ViewHolder {
        CardView cardViewNote;
        TextView txtTitle,txtCategory,txtCreated_at;
        ImageView itemDot;
        public NoteVH(View view) {
            super(view);
            txtTitle=view.findViewById(R.id.txtTitle);
            txtCategory=view.findViewById(R.id.txtCategory);
            txtCreated_at=view.findViewById(R.id.txtTime);
            cardViewNote=view.findViewById(R.id.cardNote);
            itemDot= view.findViewById(R.id.itemPopupDot);
        }
    }
}
