package br.com.alexandersoares.vccon.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.model.Morador;

/**
 * Created by alexl on 10/05/2017.
 */

public class MoradoresRecyclerAdapter extends RecyclerView.Adapter<MoradoresRecyclerAdapter.MoradorViewHolder> {

    private List<Morador> listMoradores;

    public MoradoresRecyclerAdapter(List<Morador> listMoradores) {
        this.listMoradores = listMoradores;
    }

    @Override
    public MoradorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_morador_recycler, parent, false);

        return new MoradorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoradorViewHolder holder, int position) {
        holder.textViewName.setText(listMoradores.get(position).getName());
        holder.textViewParentesco.setText(listMoradores.get(position).getParentesco());
    }

    @Override
    public int getItemCount() {
        Log.v(MoradoresRecyclerAdapter.class.getSimpleName(),""+listMoradores.size());
        return listMoradores.size();
    }

    public void add(Morador morador){
        listMoradores.add(morador);
        notifyItemInserted(listMoradores.size());
    }

    public void addAll(List<Morador> listMoradores) {
        for (Morador morador : listMoradores) {
            add(morador);
        }
    }

    public void removeItem(int position) {
        listMoradores.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMoradores.size());
    }

    /**
     * ViewHolder class
     */
    public class MoradorViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewParentesco;

        public MoradorViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewParentesco = (AppCompatTextView) view.findViewById(R.id.textViewParentesco);
        }
    }


}