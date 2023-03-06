package com.rat.ratatouille23.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.CategoriaItemBinding;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Categoria;

import java.util.ArrayList;

public class CategorieItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Categoria> data = new ArrayList<>();

    private static OnCategoriaCliccata onCategoriaCliccata;

    public CategorieItemAdapter(OnCategoriaCliccata onCategoriaCliccata) {
        this.onCategoriaCliccata = onCategoriaCliccata;
    }

    public void setData(ArrayList<Categoria> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CategoriaItemBinding categoriaBinding;

        public ViewHolder(@NonNull CategoriaItemBinding binding) {
            super(binding.getRoot());
            this.categoriaBinding = binding;
        }

        public static CategorieItemAdapter.ViewHolder inflateFrom(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @NonNull CategoriaItemBinding binding = CategoriaItemBinding.inflate(layoutInflater, parent, false);
            return new CategorieItemAdapter.ViewHolder(binding);
        }

        public void bind(Categoria categoria) {
            categoriaBinding.setCategoria(categoria);

            categoriaBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.err.println("il valore della categoria cliccato è " + categoria);
                    onCategoriaCliccata.aggiornaProdotti(categoria);
                }
            });
        }
    }

    @NonNull
    @Override
    public CategorieItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        return CategorieItemAdapter.ViewHolder.inflateFrom(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Categoria item = data.get(position);
        CategorieItemAdapter.ViewHolder binding = (CategorieItemAdapter.ViewHolder) holder;
        binding.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnCategoriaCliccata {
        public void aggiornaProdotti(Categoria categoria);
    }
}