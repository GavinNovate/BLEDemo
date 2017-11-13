package net.novate.cubers.view.event;

import net.novate.cubers.model.Device;

/**
 * author: gavin
 * create on: 2017/11/13.
 * description:
 */

public interface DeviceItemEvent {
    void onSelected(Device device);
}
