package me.marufsharia.simplenote.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;

import me.marufsharia.simplenote.R;

public class NoteCategoryCustomBottomSheet extends BaseBottomSheet {
    
    public NoteCategoryCustomBottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }
    
    public NoteCategoryCustomBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }
    
    @NonNull
    @Override
    public final View onCreateSheetContentView(@NonNull Context context) {
        return LayoutInflater.from(context).inflate(
                R.layout.note_category_bottom_sheet,
                this,
                false
        );
    }
    
}
