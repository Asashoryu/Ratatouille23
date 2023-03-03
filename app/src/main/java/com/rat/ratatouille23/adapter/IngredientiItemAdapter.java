package com.rat.ratatouille23.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rat.ratatouille23.databinding.IngredienteItemBinding;
import com.rat.ratatouille23.model.Ingrediente;

import java.util.ArrayList;

public class IngredientiItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Ingrediente> data = new ArrayList<>();

    public void setData(ArrayList<Ingrediente> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        IngredienteItemBinding ingredienteBinding;

        public ViewHolder(@NonNull IngredienteItemBinding binding) {
            super(binding.getRoot());
            this.ingredienteBinding = binding;
        }

        public static IngredientiItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull IngredienteItemBinding binding = IngredienteItemBinding.inflate(layoutInflater, parent, false);
            return new IngredientiItemAdapter.ViewHolder(binding);
        }

        public void bind(Ingrediente ingrediente) {
            ingredienteBinding.setIngrediente(ingrediente);

            ingredienteBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore dell'ingrediente cliccato è " + ingrediente);
                    if (ingrediente != null) {
                        System.out.println(ingrediente.getNome());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public IngredientiItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        return IngredientiItemAdapter.ViewHolder.inflateFrom(parent);
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
}