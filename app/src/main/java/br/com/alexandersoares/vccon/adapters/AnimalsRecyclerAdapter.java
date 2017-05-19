package br.com.alexandersoares.vccon.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.model.Animal;

/**
 * Created by alexl on 10/05/2017.
 */

public class AnimalsRecyclerAdapter extends RecyclerView.Adapter<AnimalsRecyclerAdapter.AnimalViewHolder> {

    private List<Animal> listAnimals;

    public AnimalsRecyclerAdapter(List<Animal> listAnimals) {
        this.listAnimals = listAnimals;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal_recycler, parent, false);

        return new AnimalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        holder.textViewName.setText(listAnimals.get(position).getName());
        holder.textViewTipo.setText(listAnimals.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        Log.v(AnimalsRecyclerAdapter.class.getSimpleName(),""+ listAnimals.size());
        return listAnimals.size();
    }


    /**
     * ViewHolder class
     */
    public class AnimalViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewTipo;

        public AnimalViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewTipo = (AppCompatTextView) view.findViewById(R.id.textViewTipo);
        }
    }


}