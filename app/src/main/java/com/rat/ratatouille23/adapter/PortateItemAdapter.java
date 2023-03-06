package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.PortataItemBinding;
import com.rat.ratatouille23.model.Portata;

import java.util.ArrayList;

public class PortateItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Portata> data = new ArrayList<>();

    public void setData(ArrayList<Portata> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        PortataItemBinding portataBinding;

        public ViewHolder(@NonNull PortataItemBinding binding) {
            super(binding.getRoot());
            this.portataBinding = binding;
        }

        public static PortateItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull PortataItemBinding binding = PortataItemBinding.inflate(layoutInflater, parent, false);
            return new PortateItemAdapter.ViewHolder(binding);
        }

        public void bind(Portata portata) {
            portataBinding.setPortata(portata);

            portataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'portata cliccato è " + portata);
                    if (portata != null) {
                        System.out.println(portata.getNome());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public PortateItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        return PortateItemAdapter.ViewHolder.inflateFrom(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Portata item = data.get(position);
        PortateItemAdapter.ViewHolder binding = (PortateItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
}
