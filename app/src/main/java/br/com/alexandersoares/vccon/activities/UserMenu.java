package br.com.alexandersoares.vccon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import br.com.alexandersoares.vccon.R;
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
    private AppCompatTextView textViewCondominioName;
    private AppCompatTextView textViewNumeroCasa;
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
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        textViewCondominioName = (AppCompatTextView) findViewById(R.id.textViewCondominioName);
        textViewNumeroCasa = (AppCompatTextView) findViewById(R.id.textViewNumeroCasa);
        appCompatButtonDados = (AppCompatButton) findViewById(R.id.appCompatButtonDados);
        appCompatButtonAnimais = (AppCompatButton) findViewById(R.id.appCompatButtonAnimais);
        appCompatButtonMoradores = (AppCompatButton) findViewById(R.id.appCompatButtonMoradores);
        appCompatButtonVeiculos = (AppCompatButton) findViewById(R.id.appCompatButtonVeiculos);
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
        String condominioFromIntent = getIntent().getStringExtra("CONDOMINIO");
        String numeroFromIntent = getIntent().getStringExtra("NUMERO");
        textViewName.setText(nomeFromIntent);
        textViewCondominioName.setText(condominioFromIntent);
        textViewNumeroCasa.setText(numeroFromIntent);


    }

    public void onClick(View v) {
        String idFromIntent = getIntent().getStringExtra("ID");
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        switch (v.getId()) {
            case R.id.appCompatButtonDados:
                Intent intentDados = new Intent(getApplicationContext(), MeusDados.class);
                intentDados.putExtra("USER_ID", idFromIntent);
                intentDados.putExtra("EMAIL", emailFromIntent);
                startActivity(intentDados);
                break;

            case R.id.appCompatButtonAnimais:
                Intent intentAnimais = new Intent(getApplicationContext(), AnimalListMenu.class);
                intentAnimais.putExtra("USER_ID", idFromIntent);
                intentAnimais.putExtra("EMAIL", emailFromIntent);
                startActivity(intentAnimais);
                break;

            case R.id.appCompatButtonMoradores:

                Intent intentMoradores = new Intent(getApplicationContext(), MoradorListMenu.class);
                intentMoradores.putExtra("USER_ID", idFromIntent);
                intentMoradores.putExtra("EMAIL", emailFromIntent);
                startActivity(intentMoradores);
                break;

            case R.id.appCompatButtonVeiculos:
                Intent intentVeiculos = new Intent(getApplicationContext(), VeiculoListMenu.class);
                intentVeiculos.putExtra("USER_ID", idFromIntent);
                intentVeiculos.putExtra("EMAIL", emailFromIntent);
                startActivity(intentVeiculos);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }



}