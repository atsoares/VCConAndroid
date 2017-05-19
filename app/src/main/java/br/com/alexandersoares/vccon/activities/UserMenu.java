package br.com.alexandersoares.vccon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.adapters.UsersRecyclerAdapter;
import br.com.alexandersoares.vccon.model.Usuario;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class UserMenu extends AppCompatActivity implements View.OnClickListener{

    private AppCompatActivity activity = UserMenu.this;
    private AppCompatButton appCompatButtonDados;
    private AppCompatButton appCompatButtonAnimais;
    private AppCompatButton appCompatButtonMoradores;
    private AppCompatButton appCompatButtonVeiculos;
    private AppCompatTextView textViewName;
    private List<Usuario> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().setTitle("Informações pessoais");
        initViews();
        initListeners();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        appCompatButtonDados = (AppCompatButton) findViewById(R.id.appCompatButtonDados);
        appCompatButtonAnimais = (AppCompatButton) findViewById(R.id.appCompatButtonAnimais);
        appCompatButtonMoradores = (AppCompatButton) findViewById(R.id.appCompatButtonMoradores);
        appCompatButtonVeiculos = (AppCompatButton) findViewById(R.id.appCompatButtonVeiculos);
        //recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonDados.setOnClickListener(this);
        appCompatButtonAnimais.setOnClickListener(this);
        appCompatButtonMoradores.setOnClickListener(this);
        appCompatButtonVeiculos.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        databaseHelper = new DatabaseHelper(activity);

        String nomeFromIntent = getIntent().getStringExtra("NOME");
        textViewName.setText(nomeFromIntent);

    }

    public void onClick(View v) {
        String idFromIntent = getIntent().getStringExtra("ID");
        switch (v.getId()) {
            case R.id.appCompatButtonDados:
                Intent intentDados = new Intent(getApplicationContext(), MeusDadosMenu.class);
                intentDados.putExtra("ID", idFromIntent);
                startActivity(intentDados);
                break;

            case R.id.appCompatButtonAnimais:
                Intent intentAnimais = new Intent(getApplicationContext(), AnimalListMenu.class);
                intentAnimais.putExtra("ID", idFromIntent);
                startActivity(intentAnimais);
                break;

            case R.id.appCompatButtonMoradores:

                Intent intentMoradores = new Intent(getApplicationContext(), AnimalListMenu.class);
                intentMoradores.putExtra("ID", idFromIntent);
                startActivity(intentMoradores);
                break;

            case R.id.appCompatButtonVeiculos:
                Intent intentVeiculos = new Intent(getApplicationContext(), AnimalListMenu.class);
                intentVeiculos.putExtra("ID", idFromIntent);
                startActivity(intentVeiculos);
                break;

            case R.id.textViewLinkRegister:
                Intent intentHome = new Intent(getApplicationContext(), RegisterActivity.class);
                intentHome.putExtra("ID", idFromIntent);
                startActivity(intentHome);
                break;
        }
    }

}