package me.marufsharia.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
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
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    NoteRecyclerViewAdapter noteRecyclerViewAdapter;
    List<Note> noteList;
    private long backPressCount;
    private Toast backToast;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.btnFabNote);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteCreateActivity.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.noteRecyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Simple Note");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_add_circle_outline_black_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        
    
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(this);
        noteDatabaseHelper = new NoteDBHelper(this);
    
        long l = noteDatabaseHelper.createNote(new Note("mar", "this is description"));
        Toast.makeText(this, "Crated id  :  " + l , Toast.LENGTH_SHORT).show();
      //  getNotes();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        noteRecyclerViewAdapter = new NoteRecyclerViewAdapter(this, noteDatabaseHelper.getAllNotes());
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
    
        MenuItem searchItem = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
    
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        
            @Override
            public boolean onQueryTextChange(String newText) {
                noteRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
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
                this.setTheme(R.style.AppThemePurple);
                Toast.makeText(this, "Theme Change", Toast.LENGTH_SHORT).show();
                Intent intent2 = getPackageManager().getLaunchIntentForPackage(getPackageName());
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.mnuAbout:
                Toast.makeText(this, "new About", Toast.LENGTH_SHORT).show();
                OfficeAboutHelper officeAboutHelper = new OfficeAboutHelper(this, "https://raw.githubusercontent.com/marufsharia/polygon-note/master/app/src/main/assets/about_data.json");
                officeAboutHelper.showAboutActivity();
                break;
            case R.id.mnuExit:
                final FlatDialog flatDialog = new FlatDialog(MainActivity.this);
                flatDialog.setTitle("Exit?")
                        .setSubtitle("Are you want to exit?")
                        .setFirstButtonText("Yes")
                        .setSecondButtonText("No")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                System.exit(0);
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {
        if (backPressCount + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            finish();
            System.exit(0);
        } else {
            
            backToast = Toasty.custom(getApplicationContext(), "Press back again to exit.", R.drawable.ic_exit_to_app_black_24dp, R.color.lightGreen, Toasty.LENGTH_SHORT, true, true);
            backToast.show();
        }
        backPressCount = System.currentTimeMillis();
    }
    
}
