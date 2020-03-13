package me.marufsharia.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.marufsharia.simplenote.R;
import me.marufsharia.simplenote.adapter.NoteRecyclerViewAdapter;
import me.marufsharia.simplenote.db.NoteDBHelper;
import me.marufsharia.simplenote.db.SQLiteDatabaseHelper;
import me.marufsharia.simplenote.model.Note;
import me.marufsharia.simplenote.utility.MyDividerItemDecoration;
import p32929.officeaboutlib.Others.OfficeAboutHelper;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabaseHelper sqLiteDatabaseHelper;
    NoteDBHelper noteDatabaseHelper;
    
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Note> noteList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.noteRecyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Simple Note");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_add_circle_outline_black_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        
    
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(this);
        noteDatabaseHelper = new NoteDBHelper(this);

       long l = noteDatabaseHelper.createNote(new Note("Hello world", "this is description"));
        Toast.makeText(this, "Crated id  :  " + l , Toast.LENGTH_SHORT).show();
      //  getNotes();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        NoteRecyclerViewAdapter noteRecyclerViewAdapter = new NoteRecyclerViewAdapter(this,noteDatabaseHelper.getAllNotes());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 2));
        recyclerView.setAdapter(noteRecyclerViewAdapter);
        
    }
    
    public void getNotes(){
        noteList = new ArrayList<>();
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(this);
        noteDatabaseHelper = new NoteDBHelper(this);
        noteList=noteDatabaseHelper.getAllNotes();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //code for item select event handling
        switch(item.getItemId()){
            case R.id.mnuNewNote:
                Toast.makeText(this, "new Note", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NoteCreateActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuSetting:
    
                Toast.makeText(this, "new Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuAbout:
                Toast.makeText(this, "new About", Toast.LENGTH_SHORT).show();
                OfficeAboutHelper officeAboutHelper = new OfficeAboutHelper(this, "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/Office/OfficeInfo.json");
                officeAboutHelper.showAboutActivity();
                break;
            case R.id.mnuExit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}
