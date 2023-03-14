package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.IngredienteItemBinding;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.Ingrediente;

import java.util.ArrayList;

public class IngredientiItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Ingrediente> data = new ArrayList<>();

    private OnIngredienteCliccato onIngredienteCliccato;

    public IngredientiItemAdapter(OnIngredienteCliccato onIngredienteCliccato) {
        this.onIngredienteCliccato = onIngredienteCliccato;
    }

    public void setData(ArrayList<Ingrediente> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        IngredienteItemBinding ingredienteBinding;

        OnIngredienteCliccato onIngredienteCliccato;

        public ViewHolder(@NonNull IngredienteItemBinding binding) {
            super(binding.getRoot());
            this.ingredienteBinding = binding;
        }

        public static IngredientiItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull IngredienteItemBinding binding = IngredienteItemBinding.inflate(layoutInflater, parent, false);
            return new IngredientiItemAdapter.ViewHolder(binding);
        }

        public void aggiungiAzione(OnIngredienteCliccato onIngredienteCliccato) {
            this.onIngredienteCliccato = onIngredienteCliccato;
        }

        public void bind(Ingrediente ingrediente) {
            ingredienteBinding.setIngrediente(ingrediente);

            ingredienteBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'ingrediente cliccato è " + ingrediente);
                    onIngredienteCliccato.azione(ingrediente);
                }
            });
        }
    }

    @NonNull
    @Override
    public IngredientiItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        IngredientiItemAdapter.ViewHolder viewHolder;
        viewHolder = IngredientiItemAdapter.ViewHolder.inflateFrom(parent);
        viewHolder.aggiungiAzione(onIngredienteCliccato);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ingrediente item = data.get(position);
        IngredientiItemAdapter.ViewHolder binding = (IngredientiItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnIngredienteCliccato {
        public void azione(Ingrediente ingrediente);
    }
}