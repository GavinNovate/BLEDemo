package net.novate.cubers.binding;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import net.novate.cubers.R;

/**
 * author: gavin
 * create on: 2017/11/8.
 * description:
 */

public class AppBindingAdapter {

    @BindingAdapter(value = "noticeText")
    public static void notice(TextView view, int state) {
        String[] strings = view.getContext().getResources().getStringArray(R.array.notice);
        if (state >= 0 && state < strings.length) {
            view.setText(strings[state]);
        }
    }

    @BindingAdapter(value = "actionText")
    public static void actionText(TextView view, int state) {

    }

    @BindingAdapter(value = "actionShow")
    public static void actionShow(TextView view, int state) {

    }
}
