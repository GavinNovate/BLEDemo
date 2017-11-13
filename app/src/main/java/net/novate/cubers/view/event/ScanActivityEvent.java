package net.novate.cubers.view.event;

/**
 * author: gavin
 * create on: 2017/11/3.
 * description:
 */

public interface ScanActivityEvent {
    void onAction(int state, int scanState);
}
