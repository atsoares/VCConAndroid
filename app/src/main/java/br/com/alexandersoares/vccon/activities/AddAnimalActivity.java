package br.com.alexandersoares.vccon.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.helpers.InputValidation;
import br.com.alexandersoares.vccon.model.Animal;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

public class AddAnimalActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = AddAnimalActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutTipo;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextTipo;

    private AppCompatButton appCompatButtonRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Animal animal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        getSupportActionBar().setTitle("Adicionar animal");

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

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);

        textInputLayoutTipo = (TextInputLayout) findViewById(R.id.textInputLayoutTipo);

        textInputEditTextTipo = (TextInputEditText) findViewById(R.id.textInputEditTextTipo);

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
        animal = new Animal();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
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
        if (!inputValidation.isInputEditTextFilled(textInputEditTextTipo, textInputLayoutTipo, getString(R.string.error_message_email))) {
            return;
        }


            animal.setName(textInputEditTextName.getText().toString().trim());
            animal.setTipo(textInputEditTextTipo.getText().toString().trim());

            String userId = getIntent().getStringExtra("ID");

            databaseHelper.addAnimal(animal, userId);

            // Snack Bar to show success message that record saved successfully
            Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG);

            snackbar.show();
            emptyInputEditText();



    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextTipo.setText(null);
    }
}