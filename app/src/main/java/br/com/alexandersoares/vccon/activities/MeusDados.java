package br.com.alexandersoares.vccon.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.adapters.UsersRecyclerAdapter;
import br.com.alexandersoares.vccon.model.Usuario;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

import static br.com.alexandersoares.vccon.R.id.textViewLinkRegister;

/**
 * Created by alexl on 10/05/2017.
 */

public class MeusDados extends AppCompatActivity {

    private AppCompatActivity activity = MeusDados.this;
    private AppCompatTextView textViewName;
    private AppCompatTextView textViewCondominioName;
    private AppCompatTextView textViewNumeroCasa;
    private List<Usuario> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().setTitle("Meus dados");

        initViews();
        initObjects();

    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        textViewCondominioName = (AppCompatTextView) findViewById(R.id.textViewCondominioName);
        textViewNumeroCasa = (AppCompatTextView) findViewById(R.id.textViewNumeroCasa);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);

        String nomeFromIntent = getIntent().getStringExtra("NOME");
        textViewName.setText(nomeFromIntent);


        getDataFromSQLite();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonDados:

                break;
            case textViewLinkRegister:

                break;
        }
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}