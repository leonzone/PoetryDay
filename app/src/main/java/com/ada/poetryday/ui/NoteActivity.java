package com.ada.poetryday.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ada.poetryday.R;
import com.ada.poetryday.model.Diary;
import com.ada.poetryday.utils.SnackbarUtils;
import com.ada.poetryday.utils.TimeUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 */
public class NoteActivity extends BaseActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    MaterialEditText labelEditText;

    MaterialEditText contentEditText;

    TextView oprTimeLineTextView;


    private MenuItem doneMenuItem;

    private int operateNoteType = 0;

    private Diary diary;

    public final static String OPERATE_NOTE_TYPE_KEY = "OPERATE_NOTE_TYPE_KEY";

    public final static int VIEW_NOTE_TYPE = 0x00;
    public final static int EDIT_NOTE_TYPE = 0x01;
    public final static int CREATE_NOTE_TYPE = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_diary;
    }
    public void onEventMainThread(Diary diary) {
        this.diary = diary;
        initToolbar();
        initEditText();
        initTextView();
    }

    private void parseIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            operateNoteType = intent.getExtras().getInt(OPERATE_NOTE_TYPE_KEY, 0);
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar(toolbar);
        toolbar.setTitle(R.string.view_note);
        switch (operateNoteType) {
            case CREATE_NOTE_TYPE:
                toolbar.setTitle(R.string.new_note);
                break;
            case EDIT_NOTE_TYPE:
                toolbar.setTitle(R.string.edit_note);
                break;
            case VIEW_NOTE_TYPE:
                toolbar.setTitle(R.string.view_note);
                break;
            default:
                break;
        }
    }



    private void initEditText() {
        switch (operateNoteType) {
            case EDIT_NOTE_TYPE:
                labelEditText.requestFocus();
                labelEditText.setText(diary.getLabel());
                contentEditText.setText(diary.getContent());
                labelEditText.setSelection(diary.getLabel().length());
                contentEditText.setSelection(diary.getContent().length());
                break;
            case VIEW_NOTE_TYPE:
                labelEditText.setText(diary.getLabel());
                contentEditText.setText(diary.getContent());
                labelEditText.setOnFocusChangeListener(new SimpleOnFocusChangeListener());
                contentEditText.setOnFocusChangeListener(new SimpleOnFocusChangeListener());
                break;
            default:
                labelEditText.requestFocus();
                break;
        }
        labelEditText.addTextChangedListener(new SimpleTextWatcher());
        contentEditText.addTextChangedListener(new SimpleTextWatcher());
    }

    private void initTextView() {
        boolean all = preferenceUtils.getBooleanParam(getString(R.string.show_note_history_log_key));
        oprTimeLineTextView.setText(getOprTimeLineText(diary, all));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        doneMenuItem = menu.getItem(0);
        doneMenuItem.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (doneMenuItem.isVisible()) {
                    SnackbarUtils.show(NoteActivity.this,"保存笔记");
                    saveNote();
                    return true;
                }
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doneMenuItem != null && doneMenuItem.isVisible()) {
                saveNote();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void saveNote() {
        hideKeyBoard(labelEditText);
        diary.setLabel(labelEditText.getText().toString());
        diary.setContent(contentEditText.getText().toString());
        diary.setLastOprTime(TimeUtils.getCurrentTimeInLong());

        switch (operateNoteType) {
            case CREATE_NOTE_TYPE:
//                finalDb.saveBindId(diary);
                break;
            default:
//                finalDb.update(diary);
                break;
        }
        finish();
    }

    class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (doneMenuItem == null)
                return;
            String labelSrc = labelEditText.getText().toString();
            String contentSrc = contentEditText.getText().toString();
            String label = labelSrc.replaceAll("\\s*|\t|\r|\n", "");
            String content = contentSrc.replaceAll("\\s*|\t|\r|\n", "");
            if (!TextUtils.isEmpty(label) && !TextUtils.isEmpty(content)) {
                if (TextUtils.equals(labelSrc, diary.getLabel()) && TextUtils.equals(contentSrc, diary.getContent())) {
                    doneMenuItem.setVisible(false);
                    return;
                }
                doneMenuItem.setVisible(true);
            } else {
                doneMenuItem.setVisible(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class SimpleOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && toolbar != null) {
                toolbar.setTitle(R.string.edit_note);
            }
        }
    }

    private String getOprTimeLineText(Diary diary, boolean all) {
//        if (diary == null || diary.getLogs() == null)
//            return "";
//        String create = getString(R.string.create);
//        String edit = getString(R.string.edit);
//        StringBuilder sb = new StringBuilder();
//        if (diary.getLogs().getList().size() <= 0) {
//            return "";
//        }
//        NoteOperateLog log;
//        List<NoteOperateLog> logs = diary.getLogs().getList();
//        int size = logs.size();
//        if (!all) {
//            log = logs.get(size - 1);
//            if (log.getType() == NoteConfig.NOTE_CREATE_OPR) {
//                sb.append(getString(R.string.note_log_text, create, TimeUtils.getTime(log.getTime())));
//            } else {
//                sb.append(getString(R.string.note_log_text, edit, TimeUtils.getTime(log.getTime())));
//            }
//            return sb.toString();
//        }
//        for (int i = size - 1; i >= 0; i--) {
//            log = logs.get(i);
//            if (log.getType() == NoteConfig.NOTE_CREATE_OPR) {
//                sb.append(getString(R.string.note_log_text, create, TimeUtils.getTime(log.getTime())));
//            } else {
//                sb.append(getString(R.string.note_log_text, edit, TimeUtils.getTime(log.getTime())));
//            }
//            sb.append("\n");
//        }
        return "";
    }

    private void hideKeyBoard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
