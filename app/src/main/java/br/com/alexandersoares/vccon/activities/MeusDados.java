package br.com.alexandersoares.vccon.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.helpers.InputValidation;
import br.com.alexandersoares.vccon.model.Usuario;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class MeusDados extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = MeusDados.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutNomeCondominio;
    private TextInputLayout textInputLayoutNumeroResidencia;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextCondominio;
    private TextInputEditText textInputEditTextNumero;

    private AppCompatButton appCompatButtonRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Usuario user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);
        getSupportActionBar().setTitle("Meus dados");

        initViews();
        initListeners();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutNomeCondominio = (TextInputLayout) findViewById(R.id.textInputLayoutNomeCondominio);
        textInputLayoutNumeroResidencia = (TextInputLayout) findViewById(R.id.textInputLayoutNumeroResidencia);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextCondominio = (TextInputEditText) findViewById(R.id.textInputEditTextCondominio);
        textInputEditTextNumero = (TextInputEditText) findViewById(R.id.textInputEditTextNumero);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);


    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {


        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);

        String user_id = getIntent().getStringExtra("USER_ID");

        String idUser = databaseHelper.getUserIdByEmail(getIntent().getStringExtra("EMAIL"));


        if (user_id == null){
            if (idUser == null){
            }
        }else{
            if(checkList() == true){
                popular();
            }
        }



    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextCondominio, textInputLayoutNomeCondominio, getString(R.string.error_message_condominio))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextNumero, textInputLayoutNumeroResidencia, getString(R.string.error_message_numero))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setCondominio(textInputEditTextCondominio.getText().toString().trim());
            user.setNumero(textInputEditTextNumero.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());

            String nome = user.getName();
            String condominio = user.getCondominio();
            String numero = user.getName();
            String email = user.getName();

            String id = getIntent().getStringExtra("USER_ID");

            databaseHelper.updateUser(id, nome, condominio, numero, email);

            // Snack Bar to show success message that record saved successfully
            Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.sucesso_update), Snackbar.LENGTH_LONG);
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            snackbar.show();


        } else {
            // Snack Bar to show error message that record already exists

            Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG);
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            snackbar.show();
        }
    }

    public boolean checkList(){

        String user_id = getIntent().getStringExtra("USER_ID");
        Usuario getAll = databaseHelper.getUsuario(user_id);
        if(getAll == null){
            return false;
        }else {
            return true;
        }
    }

    public void popular(){

        String user_id = getIntent().getStringExtra("USER_ID");
        Usuario usuario = databaseHelper.getUsuario(user_id);

        Log.i("usuarioNome", usuario.getName());
        Log.i("usuarioEmail", usuario.getEmail());
        Log.i("usuarioCondominio", usuario.getCondominio());
        Log.i("usuarioNumero", usuario.getNumero());

        textInputEditTextEmail.setText(usuario.getEmail());

        textInputEditTextName.setText(usuario.getName());

        textInputEditTextCondominio.setText(usuario.getCondominio());

        textInputEditTextNumero.setText(usuario.getNumero());

    }

}