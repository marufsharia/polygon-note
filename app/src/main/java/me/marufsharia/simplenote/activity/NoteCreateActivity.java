package me.marufsharia.simplenote.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flipboard.bottomsheet.BottomSheetLayout;

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.EditorToolbar;
import net.dankito.richtexteditor.model.DownloadImageConfig;
import net.dankito.richtexteditor.model.DownloadImageUiSetting;
import net.dankito.utils.android.permissions.IPermissionsService;
import net.dankito.utils.android.permissions.PermissionsService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.marufsharia.simplenote.R;

public class NoteCreateActivity extends AppCompatActivity {
    
    Toolbar toolbar;
    private RichTextEditor editor;
    private EditorToolbar editorToolbar;
    private IPermissionsService permissionsService;
    BottomSheetLayout bottomSheet;
    Button button;
    Spinner spinner;
    List<NoteCategory> noteCategories;
    ArrayAdapter<NoteCategory> adapter;
    NoteCategory noteCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);
        noteCategories = new ArrayList<>();
        noteCategories.add(new NoteCategory(1, "Untitled"));
        noteCategories.add(new NoteCategory(2, "Education"));
        noteCategories.add(new NoteCategory(3, "Programming"));
        noteCategories.add(new NoteCategory(4, "Android"));
    
        adapter = new ArrayAdapter<NoteCategory>(this, android.R.layout.simple_spinner_item,
                                                 noteCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        editor = findViewById(R.id.editor);
        editorToolbar = findViewById(R.id.editorToolbar);
        editorToolbar.setEditor(editor);
        permissionsService = new PermissionsService(this);
        bottomSheet = findViewById(R.id.bottomsheet); //https://github.com/Flipboard/bottomsheet/wiki/API-Documentation
        
        editor.setEditorFontSize(20);
        editor.setPadding((int) (4 * getResources().getDisplayMetrics().density));
    
        //some properties you also can set on editor
        editor.setEditorBackgroundColor(Color.YELLOW);
        editor.setEditorFontColor(Color.MAGENTA);
        editor.setEditorFontFamily("cursive");
        
        // show keyboard right at start up
        // editor.focusEditorAndShowKeyboardDelayed();
        // editor.setHtml("<h1>Hello</h1>word");
        
        // only needed if you allow to automatically download remote images
        editor.setDownloadImageConfig(new DownloadImageConfig(DownloadImageUiSetting.AllowSelectDownloadFolderInCode,
                                                              new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "downloaded_images")));
    
    
        /*   Set listeners on RichTextEditor */
       /* // get informed when edited HTML changed
        editor.addDidHtmlChangeListener(new DidHtmlChangeListener() {
            @Override
            public void didHtmlChange(boolean didHtmlChange) {
                // e.g. set save button to enabled / disabled
                // btnSave.setEnabled(didHtmlChange);
            }
        });
        /*
        // use this listener with care, it may decreases performance tremendously
        editor.addHtmlChangedListener(new HtmlChangedListener() {
            @Override
            public void htmlChangedAsync(@NotNull String html) {
                // htmlChangedAsync() gets called on a background thread, so if you want to use it on UI thread you have to call runOnUiThread()
            }
        });*/
    
    
    }
    
    @Override
    public void onBackPressed() {
        if (!editorToolbar.handlesBackButtonPress()) {
            super.onBackPressed();
        }
    }
    
    
    // only needed if you like to insert images from local device so the user gets asked for permission to access external storage if needed
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsService.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    
    // then when you want to do something with edited html
//    private void save() {
//        editor.getCurrentHtmlAsync(new GetCurrentHtmlCallback() {
//
//            @Override
//            public void htmlRetrieved(@NotNull String html) {
//                saveHtml(html);
//            }
//        });
//    }
//
//    private void saveHtml(String html) {
//        // ...
//    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_bottom_sheet_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuCat:
                bottomSheet.showWithSheetView(LayoutInflater.from(this).inflate(R.layout.note_category_bottom_sheet, bottomSheet, false));
                bottomSheetViewInterAction(bottomSheet.getSheetView());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void bottomSheetViewInterAction(View view) {
        
        spinner = view.findViewById(R.id.cat_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                noteCategory = (NoteCategory) adapterView.getSelectedItem();
                
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            
            }
        });
        
        //button
        button = view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NoteCreateActivity.this, "Id :  " + noteCategory.getId() + "name: " + noteCategory.getName(), Toast.LENGTH_SHORT).show();
                bottomSheet.dismissSheet();
            }
        });
    }
}
