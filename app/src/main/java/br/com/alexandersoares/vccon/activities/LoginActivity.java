package br.com.alexandersoares.vccon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.helpers.InputValidation;
import br.com.alexandersoares.vccon.model.Usuario;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

        private final AppCompatActivity activity = LoginActivity.this;

        private NestedScrollView nestedScrollView;

        private TextInputLayout textInputLayoutEmail;
        private TextInputLayout textInputLayoutPassword;

        private TextInputEditText textInputEditTextEmail;
        private TextInputEditText textInputEditTextPassword;

        private AppCompatButton appCompatButtonLogin;

        private AppCompatTextView textViewLinkRegister;
        private List<Usuario> listUsers;
        private InputValidation inputValidation;
        private DatabaseHelper databaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            getSupportActionBar().hide();

            initViews();
            initListeners();
            initObjects();
        }

        /**
         * This method is to initialize views
         */
        private void initViews() {

            nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

            textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
            textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

            textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
            textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

            appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

            textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

        }

        /**
         * This method is to initialize listeners
         */
        private void initListeners() {
            appCompatButtonLogin.setOnClickListener(this);
            textViewLinkRegister.setOnClickListener(this);
        }

        /**
         * This method is to initialize objects to be used
         */
        private void initObjects() {
            databaseHelper = new DatabaseHelper(activity);
            inputValidation = new InputValidation(activity);

        }

        /**
         * This implemented method is to listen the click on view
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.appCompatButtonLogin:
                    verifyFromSQLite();
                    break;
                case R.id.textViewLinkRegister:
                    // Navigate to RegisterActivity
                    Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intentRegister);
                    break;
            }
        }

        /**
         * This method is to validate the input text fields and verify login credentials from SQLite
         */
        private void verifyFromSQLite() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                return;
            }

            if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                    , textInputEditTextPassword.getText().toString().trim())) {

                String nome = databaseHelper.getUserByEmail(textInputEditTextEmail.getText().toString().trim());
                String id = databaseHelper.getUserIdByEmail(textInputEditTextEmail.getText().toString().trim());

                Usuario usuario = databaseHelper.getUsuario(id);

                System.out.println(id);
                System.out.print(id);


                Intent accountsIntent = new Intent(activity, UserMenu.class);
                accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                accountsIntent.putExtra("CONDOMINIO", usuario.getCondominio());
                accountsIntent.putExtra("NUMERO", usuario.getNumero());
                accountsIntent.putExtra("NOME", nome);
                accountsIntent.putExtra("ID", id);


                emptyInputEditText();
                startActivity(accountsIntent);
                finish();


            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG);
                ViewGroup group = (ViewGroup) snackbar.getView();
                group.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.show();
            }
        }

        /**
         * This method is to empty all input edit text
         */
        private void emptyInputEditText() {
            textInputEditTextEmail.setText(null);
            textInputEditTextPassword.setText(null);
        }

}
