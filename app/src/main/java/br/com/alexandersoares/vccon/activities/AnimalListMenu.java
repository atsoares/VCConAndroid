package br.com.alexandersoares.vccon.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.adapters.AnimalsRecyclerAdapter;
import br.com.alexandersoares.vccon.model.Animal;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class AnimalListMenu extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = AnimalListMenu.this;
    private AppCompatTextView textViewName;
    private AppCompatButton bt_flutuante;
    private RecyclerView recyclerViewAnimais;
    private List<Animal> listAnimals;
    private AnimalsRecyclerAdapter animalsRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_animais);
        getSupportActionBar().setTitle("");
        initViews();
        initListeners();
        initObjects();

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        String idFromIntent = getIntent().getStringExtra("ID");
        switch (v.getId()) {
            case R.id.bt_flutuante:
                // Navigate to RegisterActivity
                Intent intentAddAnimal = new Intent(getApplicationContext(), AddAnimalActivity.class);
                intentAddAnimal.putExtra("ID", idFromIntent);
                startActivity(intentAddAnimal);
                break;
        }
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewAnimais = (RecyclerView) findViewById(R.id.recyclerViewAnimais);
        bt_flutuante = (AppCompatButton) findViewById(R.id.bt_flutuante);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        bt_flutuante.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listAnimals  = new ArrayList<>();
        animalsRecyclerAdapter = new AnimalsRecyclerAdapter(listAnimals);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAnimais.setLayoutManager(mLayoutManager);
        recyclerViewAnimais.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAnimais.setHasFixedSize(true);
        recyclerViewAnimais.setAdapter(animalsRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listAnimals.clear();
                String user_id = getIntent().getStringExtra("ID");
                listAnimals.addAll(databaseHelper.getAllAnimalByID(user_id));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                animalsRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


}