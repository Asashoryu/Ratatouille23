package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.PortataItemBinding;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.Portata;

import java.util.ArrayList;

public class PortateItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Portata> data = new ArrayList<>();

    private OnPortataCliccata onPortataCliccata;

    public PortateItemAdapter(OnPortataCliccata onPortataCliccata) {
        this.onPortataCliccata = onPortataCliccata;
    }

    public void setData(ArrayList<Portata> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        PortataItemBinding portataBinding;
        OnPortataCliccata onPortataCliccata;

        public ViewHolder(@NonNull PortataItemBinding binding) {
            super(binding.getRoot());
            this.portataBinding = binding;
        }

        public static PortateItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull PortataItemBinding binding = PortataItemBinding.inflate(layoutInflater, parent, false);
            return new PortateItemAdapter.ViewHolder(binding);
        }

        public void aggiungiAzione(OnPortataCliccata onPortataCliccata) {
            this.onPortataCliccata = onPortataCliccata;
        }

        public void bind(Portata portata) {
            portataBinding.setPortata(portata);

            portataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'portata cliccato è " + portata.getNome());
                    onPortataCliccata.azione(portata);
                    System.err.println("item portate adapter finito " + portata.getNome());
                }
            });
        }
    }

    @NonNull
    @Override
    public PortateItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        ViewHolder viewHolder;
        viewHolder = PortateItemAdapter.ViewHolder.inflateFrom(parent);
        viewHolder.aggiungiAzione(onPortataCliccata);
        return viewHolder;
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

    public interface OnPortataCliccata {
        public void azione(Portata portata);
    }
}
