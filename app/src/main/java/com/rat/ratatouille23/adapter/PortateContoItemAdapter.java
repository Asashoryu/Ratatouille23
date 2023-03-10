package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.PortataContoItemBinding;
import com.rat.ratatouille23.model.Portata;

import java.util.ArrayList;

public class PortateContoItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Portata> data = new ArrayList<>();

    private PortateContoItemAdapter.OnPortataContoCliccata onPortataContoCliccata;

    public PortateContoItemAdapter(PortateContoItemAdapter.OnPortataContoCliccata onPortataContoCliccata) {
        this.onPortataContoCliccata = onPortataContoCliccata;
    }
    public void setData(ArrayList<Portata> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        PortataContoItemBinding portataContoBinding;
        PortateContoItemAdapter.OnPortataContoCliccata onPortataContoCliccata;

        public ViewHolder(@NonNull PortataContoItemBinding binding) {
            super(binding.getRoot());
            this.portataContoBinding = binding;
        }

        public static PortateContoItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull PortataContoItemBinding binding = PortataContoItemBinding.inflate(layoutInflater, parent, false);
            return new PortateContoItemAdapter.ViewHolder(binding);
        }

        public void aggiungiAzione(PortateContoItemAdapter.OnPortataContoCliccata onPortataContoCliccata) {
            this.onPortataContoCliccata = onPortataContoCliccata;
        }

        public void bind(Portata portata) {
            portataContoBinding.setPortata(portata);

            portataContoBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'portata cliccato è " + portata.getNome());
                    onPortataContoCliccata.azione(portata);
                    System.err.println("item portate adapter finito " + portata.getNome());
                }
            });
        }
    }

    @NonNull
    @Override
    public PortateContoItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        PortateContoItemAdapter.ViewHolder viewHolder;
        viewHolder = PortateContoItemAdapter.ViewHolder.inflateFrom(parent);
        viewHolder.aggiungiAzione(onPortataContoCliccata);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Portata item = data.get(position);
        PortateContoItemAdapter.ViewHolder binding = (PortateContoItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnPortataContoCliccata {
        public void azione(Portata portata);
    }
}
