package com.consomme.droidkaigi2017sample.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.consomme.droidkaigi2017sample.R;
import com.consomme.droidkaigi2017sample.databinding.ListItemSampleBinding;

import java.util.ArrayList;
import java.util.List;

public class SampleRecyclerAdapter extends RecyclerView.Adapter<SampleRecyclerAdapter.ViewHolder> {

    private static final int MAX_ITEM_SIZE = 50;

    private List<String> itemList;

    public SampleRecyclerAdapter() {
        super();
        createItemList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemSampleBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.list_item_sample, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.textView.setText(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void createItemList() {
        itemList = new ArrayList<>();
        for (int i = 0; i < MAX_ITEM_SIZE; i++) {
            itemList.add(String.valueOf(i + 1));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ListItemSampleBinding binding;

        ViewHolder(ListItemSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
