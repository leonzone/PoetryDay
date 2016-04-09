package com.ada.poetryday.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ada.poetryday.utils.PreferenceUtils;

import butterknife.ButterKnife;

/**
 * Created by tomit on 2016/4/9.
 */
public abstract class  BaseActivity extends AppCompatActivity {
    protected PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceUtils = PreferenceUtils.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        ButterKnife.inject(this);
        initToolbar();
    }

    protected void initToolbar(Toolbar toolbar){
//        if (toolbar == null)
//            return;
//        toolbar.setBackgroundColor(getColorPrimary());
//        toolbar.setTitle(R.string.app_name);
//        toolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
//        toolbar.collapseActionView();
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }
//    protected abstract int getLayoutView();
//
//    protected abstract List<Object> getModules();

    protected abstract void initToolbar();
    protected abstract int getLayoutView();
}
