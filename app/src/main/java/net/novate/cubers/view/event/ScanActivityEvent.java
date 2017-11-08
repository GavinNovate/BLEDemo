package net.novate.cubers.view.event;

import android.databinding.ObservableInt;

/**
 * author: gavin
 * create on: 2017/11/8.
 * description:
 */

public interface ScanActivityEvent {
    void onAction(ObservableInt state);
}
