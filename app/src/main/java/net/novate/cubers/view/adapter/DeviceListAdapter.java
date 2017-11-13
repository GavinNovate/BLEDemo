package net.novate.cubers.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.novate.cubers.R;
import net.novate.cubers.databinding.DeviceItemBinding;
import net.novate.cubers.model.Device;
import net.novate.cubers.view.event.DeviceItemEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * author: gavin
 * created on: 2017-11-8
 * description:
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {


    private List<Device> devices;

    private DeviceItemEvent event;

    public DeviceListAdapter(DeviceItemEvent event) {
        this.event = event;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.device_item,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setEvent(event);
        holder.binding.setDevice(devices.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return devices == null ? 0 : devices.size();
    }

    public void setDevices(List<Device> devices) {
        if (this.devices == null) {
            this.devices = new ArrayList<>(devices);
            notifyItemRangeInserted(0, devices.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return DeviceListAdapter.this.devices.size();
                }

                @Override
                public int getNewListSize() {
                    return devices.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return DeviceListAdapter.this.devices.get(oldItemPosition).equals(devices.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return DeviceListAdapter.this.devices.get(oldItemPosition).getName().equals(devices.get(newItemPosition).getName());
                }
            });
            this.devices = new ArrayList<>(devices);
            result.dispatchUpdatesTo(this);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private DeviceItemBinding binding;

        ViewHolder(DeviceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
