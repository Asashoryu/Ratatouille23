package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.TavoloItemBinding;
import com.rat.ratatouille23.model.Tavolo;

import java.util.ArrayList;

public class TavoliItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Tavolo> data = new ArrayList<>();

    private static TavoliItemAdapter.OnTavoloCliccato onTavoloCliccato;

    public TavoliItemAdapter(TavoliItemAdapter.OnTavoloCliccato onTavoloCliccato) {
        this.onTavoloCliccato = onTavoloCliccato;
    }

    public void setData(ArrayList<Tavolo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TavoloItemBinding tavoloBinding;

        public ViewHolder(@NonNull TavoloItemBinding binding) {
            super(binding.getRoot());
            this.tavoloBinding = binding;
        }

        public static TavoliItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull TavoloItemBinding binding = TavoloItemBinding.inflate(layoutInflater, parent, false);
            return new TavoliItemAdapter.ViewHolder(binding);
        }

        public void bind(Tavolo tavolo) {
            tavoloBinding.setTavolo(tavolo);

            tavoloBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'tavolo cliccato è " + tavolo);
                    onTavoloCliccato.naviga(tavolo);
                }
            });
        }
    }

    @NonNull
    @Override
    public TavoliItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        return TavoliItemAdapter.ViewHolder.inflateFrom(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tavolo item = data.get(position);
        TavoliItemAdapter.ViewHolder binding = (TavoliItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnTavoloCliccato {
        public void naviga(Tavolo tavolo);
    }
}
