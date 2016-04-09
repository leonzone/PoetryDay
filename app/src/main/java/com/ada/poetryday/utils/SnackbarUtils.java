package com.ada.poetryday.utils;

import android.app.Activity;
import android.widget.Toast;


/**
 */
public class SnackbarUtils {

    public static void show(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//        Toast toast = new Toast(mContext);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_layout, null, false);
//        TextView textView = (TextView)view.findViewById(R.id.toast_text);
//        textView.setText(message);
//        toast.setView(view);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//        int color = Color.BLACK;
//        if (activity instanceof BaseActivity){
//            color = (((BaseActivity) activity)).getColorPrimary();
//        }
//        color = color & 0xddffffff;
//        SnackbarManager.show(
//                Snackbar.with(activity.getApplicationContext())
//                        .color(color)
//                        .duration((Snackbar.SnackbarDuration.LENGTH_SHORT.getDuration() / 2))
//                        .text(message), activity);
    }

}
