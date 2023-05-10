package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.IngredienteItemBinding;
import com.rat.ratatouille23.databinding.IngredientePortataItemBinding;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.IngredientePortata;

import java.util.ArrayList;

public class IngredientiPortataItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<IngredientePortata> data = new ArrayList<>();

    private OnIngredienteCliccato onIngredienteCliccato;

    public IngredientiPortataItemAdapter(OnIngredienteCliccato onIngredienteCliccato) {
        this.onIngredienteCliccato = onIngredienteCliccato;
    }

    public void setData(ArrayList<IngredientePortata> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        IngredientePortataItemBinding ingredientePortataBinding;

        OnIngredienteCliccato onIngredienteCliccato;

        public ViewHolder(@NonNull IngredientePortataItemBinding binding) {
            super(binding.getRoot());
            this.ingredientePortataBinding = binding;
        }

        public static IngredientiPortataItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull IngredientePortataItemBinding binding = IngredientePortataItemBinding.inflate(layoutInflater, parent, false);
            return new IngredientiPortataItemAdapter.ViewHolder(binding);
        }

        public void aggiungiAzione(OnIngredienteCliccato onIngredienteCliccato) {
            this.onIngredienteCliccato = onIngredienteCliccato;
        }

        public void bind(IngredientePortata ingredientePortata) {
            ingredientePortataBinding.setIngredientePortata(ingredientePortata);

            ingredientePortataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'ingrediente cliccato è " + ingredientePortata);
                    onIngredienteCliccato.azione(ingredientePortata);
                }
            });
        }
    }

    @NonNull
    @Override
    public IngredientiPortataItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        IngredientiPortataItemAdapter.ViewHolder viewHolder;
        viewHolder = IngredientiPortataItemAdapter.ViewHolder.inflateFrom(parent);
        viewHolder.aggiungiAzione(onIngredienteCliccato);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IngredientePortata item = data.get(position);
        IngredientiPortataItemAdapter.ViewHolder binding = (IngredientiPortataItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnIngredienteCliccato {
        public void azione(IngredientePortata ingredientePortata);
    }
}
