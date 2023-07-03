package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.PortataContoItemBinding;
import com.rat.ratatouille23.model.PortataOrdine;

import java.util.ArrayList;

public class PortateContoItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PortataOrdine> data = new ArrayList<>();

    private PortateContoItemAdapter.OnPortataOrdineContoCliccata onPortataOrdineContoCliccata;

    public PortateContoItemAdapter(PortateContoItemAdapter.OnPortataOrdineContoCliccata onPortataOrdineContoCliccata) {
        this.onPortataOrdineContoCliccata = onPortataOrdineContoCliccata;
    }

    public void setData(ArrayList<PortataOrdine> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        PortataContoItemBinding portataOrdineContoBinding;
        PortateContoItemAdapter.OnPortataOrdineContoCliccata onPortataOrdineContoCliccata;

        public ViewHolder(@NonNull PortataContoItemBinding binding) {
            super(binding.getRoot());
            this.portataOrdineContoBinding = binding;
        }

        public static PortateContoItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull PortataContoItemBinding binding = PortataContoItemBinding.inflate(layoutInflater, parent, false);
            return new PortateContoItemAdapter.ViewHolder(binding);
        }

        public void aggiungiAzione(PortateContoItemAdapter.OnPortataOrdineContoCliccata onPortataOrdineContoCliccata) {
            this.onPortataOrdineContoCliccata = onPortataOrdineContoCliccata;
        }

        public void bind(PortataOrdine portataOrdine) {
            portataOrdineContoBinding.setPortataOrdine(portataOrdine);

            portataOrdineContoBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPortataOrdineContoCliccata.azione(portataOrdine);
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
        viewHolder.aggiungiAzione(onPortataOrdineContoCliccata);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PortataOrdine item = data.get(position);
        PortateContoItemAdapter.ViewHolder binding = (PortateContoItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnPortataOrdineContoCliccata {
        void azione(PortataOrdine portataOrdine);
    }
}
