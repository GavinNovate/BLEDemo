package net.novate.cubers.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import net.novate.cubers.App;

/**
 * author: gavin
 * create on: 2017/10/28.
 * description:BaseViewModel
 */

public class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public App getApp() {
        return getApplication();
    }
}
