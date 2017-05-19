package br.com.alexandersoares.vccon.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.model.Carro;

/**
 * Created by alexl on 10/05/2017.
 */

public class CarrosRecyclerAdapter extends RecyclerView.Adapter<CarrosRecyclerAdapter.CarroViewHolder> {

    private List<Carro> listCarros;

    public CarrosRecyclerAdapter(List<Carro> listCarros) {
        this.listCarros = listCarros;
    }

    @Override
    public CarroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carro_recycler, parent, false);

        return new CarroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarroViewHolder holder, int position) {
        holder.textViewPlaca.setText(listCarros.get(position).getPlaca());
        holder.textViewMarca.setText(listCarros.get(position).getMarca());
        holder.textViewModelo.setText(listCarros.get(position).getModelo());
        holder.textViewCor.setText(listCarros.get(position).getCor()    );
    }

    @Override
    public int getItemCount() {
        Log.v(CarrosRecyclerAdapter.class.getSimpleName(),""+ listCarros.size());
        return listCarros.size();
    }


    /**
     * ViewHolder class
     */
    public class CarroViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewPlaca;
        public AppCompatTextView textViewMarca;
        public AppCompatTextView textViewModelo;
        public AppCompatTextView textViewCor;

        public CarroViewHolder(View view) {
            super(view);
            textViewPlaca = (AppCompatTextView) view.findViewById(R.id.textViewPlaca);
            textViewMarca = (AppCompatTextView) view.findViewById(R.id.textViewMarca);
            textViewModelo = (AppCompatTextView) view.findViewById(R.id.textViewModelo);
            textViewCor = (AppCompatTextView) view.findViewById(R.id.textViewCor);
        }
    }


}