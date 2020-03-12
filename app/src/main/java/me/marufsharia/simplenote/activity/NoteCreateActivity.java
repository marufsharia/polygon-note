package me.marufsharia.simplenote.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.EditorToolbar;
import net.dankito.richtexteditor.callback.DidHtmlChangeListener;
import net.dankito.richtexteditor.callback.HtmlChangedListener;
import net.dankito.richtexteditor.model.DownloadImageConfig;
import net.dankito.richtexteditor.model.DownloadImageUiSetting;
import net.dankito.utils.android.permissions.IPermissionsService;
import net.dankito.utils.android.permissions.PermissionsService;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import me.marufsharia.simplenote.R;
import me.marufsharia.simplenote.adapter.NoteCategoryCustomBottomSheet;

public class NoteCreateActivity extends AppCompatActivity {
    
    Toolbar toolbar;
    private RichTextEditor editor;
    private EditorToolbar editorToolbar;
    private IPermissionsService permissionsService;
    
    NoteCategoryCustomBottomSheet noteCategoryCustomBottomSheet;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);
        editor = findViewById(R.id.editor);
        editorToolbar = findViewById(R.id.editorToolbar);
        editorToolbar.setEditor(editor);
        permissionsService = new PermissionsService(this);
        
        noteCategoryCustomBottomSheet = new NoteCategoryCustomBottomSheet(this);
        View view = noteCategoryCustomBottomSheet.onCreateSheetContentView(this);
        button= view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag","adssad");
                Toast.makeText(NoteCreateActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        editor.setEditorFontSize(20);
        editor.setPadding((int) (4 * getResources().getDisplayMetrics().density));
        
        // some properties you also can set on editor
        // editor.setEditorBackgroundColor(Color.YELLOW);
        // editor.setEditorFontColor(Color.MAGENTA);
        // editor.setEditorFontFamily("cursive");
        
        // show keyboard right at start up
        //editor.focusEditorAndShowKeyboardDelayed();
        // editor.setHtml("<h1>Hello</h1>word");
        
        // only needed if you allow to automatically download remote images
        editor.setDownloadImageConfig(new DownloadImageConfig(DownloadImageUiSetting.AllowSelectDownloadFolderInCode,
                                                              new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "downloaded_images")));
        
        
        /*      Set listeners on RichTextEditor         */
        
        // get informed when edited HTML changed
        editor.addDidHtmlChangeListener(new DidHtmlChangeListener() {
            @Override
            public void didHtmlChange(boolean didHtmlChange) {
                // e.g. set save button to enabled / disabled
                // btnSave.setEnabled(didHtmlChange);
            }
        });
        
        // use this listener with care, it may decreases performance tremendously
        editor.addHtmlChangedListener(new HtmlChangedListener() {
            @Override
            public void htmlChangedAsync(@NotNull String html) {
                // htmlChangedAsync() gets called on a background thread, so if you want to use it on UI thread you have to call runOnUiThread()
            }
        });
        
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
                noteCategoryCustomBottomSheet.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
