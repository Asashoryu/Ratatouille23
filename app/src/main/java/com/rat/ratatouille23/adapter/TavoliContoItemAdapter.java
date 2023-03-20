package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.TavoloDisponibileItemBinding;
import com.rat.ratatouille23.databinding.TavoloItemBinding;
import com.rat.ratatouille23.databinding.TavoloOccupatoItemBinding;
import com.rat.ratatouille23.model.Tavolo;

import java.util.ArrayList;

public class TavoliContoItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Tavolo> tavoli = new ArrayList<>();

    private OnTavoloCliccato onTavoloCliccato;

    private final int OCCUPATO = 1;
    private final int DISPONIBILE = 2;

    public TavoliContoItemAdapter(TavoliContoItemAdapter.OnTavoloCliccato onTavoloCliccato) {
        this.onTavoloCliccato = onTavoloCliccato;
    }

    public void setData(ArrayList<Tavolo> tavoli) {
        this.tavoli = tavoli;
        notifyDataSetChanged();
    }

    public static class TavoloDisponibileViewHolder extends RecyclerView.ViewHolder {

        TavoloDisponibileItemBinding tavoloBinding;

        TavoliContoItemAdapter.OnTavoloCliccato onTavoloCliccato;

        public TavoloDisponibileViewHolder(@NonNull TavoloDisponibileItemBinding binding) {
            super(binding.getRoot());
            this.tavoloBinding = binding;
        }

        public static TavoliContoItemAdapter.TavoloDisponibileViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull TavoloDisponibileItemBinding binding = TavoloDisponibileItemBinding.inflate(layoutInflater, parent, false);
            return new TavoliContoItemAdapter.TavoloDisponibileViewHolder(binding);
        }

        public void aggiungiAzione(TavoliContoItemAdapter.OnTavoloCliccato onTavoloCliccato) {
            this.onTavoloCliccato = onTavoloCliccato;
        }

        public void bind(Tavolo tavolo) {
            tavoloBinding.setTavolo(tavolo);

            tavoloBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'tavolo cliccato è " + tavolo);
                    onTavoloCliccato.azione(tavolo);
                }
            });
        }
    }

    public static class TavoloOccupatoViewHolder extends RecyclerView.ViewHolder {

        TavoloOccupatoItemBinding tavoloBinding;

        TavoliContoItemAdapter.OnTavoloCliccato onTavoloCliccato;

        public TavoloOccupatoViewHolder(@NonNull TavoloOccupatoItemBinding binding) {
            super(binding.getRoot());
            this.tavoloBinding = binding;
        }

        public static TavoliContoItemAdapter.TavoloOccupatoViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull TavoloOccupatoItemBinding binding = TavoloOccupatoItemBinding.inflate(layoutInflater, parent, false);
            return new TavoliContoItemAdapter.TavoloOccupatoViewHolder(binding);
        }

        public void aggiungiAzione(TavoliContoItemAdapter.OnTavoloCliccato onTavoloCliccato) {
            this.onTavoloCliccato = onTavoloCliccato;
        }

        public void bind(Tavolo tavolo) {
            tavoloBinding.setTavolo(tavolo);

            tavoloBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'tavolo cliccato è " + tavolo);
                    onTavoloCliccato.azione(tavolo);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        if (viewType == DISPONIBILE) {
            TavoliContoItemAdapter.TavoloOccupatoViewHolder viewHolder;
            viewHolder = TavoliContoItemAdapter.TavoloOccupatoViewHolder.inflateFrom(parent);
            viewHolder.aggiungiAzione(onTavoloCliccato);
            return viewHolder;
        }
        else if (viewType == OCCUPATO) {
            TavoliContoItemAdapter.TavoloDisponibileViewHolder viewHolder;
            viewHolder = TavoliContoItemAdapter.TavoloDisponibileViewHolder.inflateFrom(parent);
            viewHolder.aggiungiAzione(onTavoloCliccato);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tavolo item = tavoli.get(position);
        if (holder.getItemViewType() == DISPONIBILE) {
            TavoliItemAdapter.TavoloDisponibileViewHolder binding = (TavoliItemAdapter.TavoloDisponibileViewHolder) holder;
            binding.bind(item);
        }
        else if (holder.getItemViewType() == OCCUPATO) {
            TavoliItemAdapter.TavoloOccupatoViewHolder binding = (TavoliItemAdapter.TavoloOccupatoViewHolder) holder;
            binding.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return tavoli.size();
    }

    public int getItemViewType(int position) {
        if (tavoli.get(position).getDisponibile() == true) {
            return DISPONIBILE;
        }
        else {
            return OCCUPATO;
        }
    }

    public interface OnTavoloCliccato {
        public void azione(Tavolo tavolo);
    }
}
