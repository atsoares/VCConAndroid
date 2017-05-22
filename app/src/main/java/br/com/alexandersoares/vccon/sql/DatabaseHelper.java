package br.com.alexandersoares.vccon.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandersoares.vccon.model.Animal;
import br.com.alexandersoares.vccon.model.Carro;
import br.com.alexandersoares.vccon.model.Morador;
import br.com.alexandersoares.vccon.model.Usuario;

/**
 * Created by alexl on 09/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_CONDOMINIO = "user_condominio";
    private static final String COLUMN_USER_NUMERO = "user_numero";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_CONDOMINIO + " TEXT," + COLUMN_USER_NUMERO + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // User table name
    private static final String TABLE_ANIMAL = "animal";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ANIMAL_ID = "animal_id";
    private static final String COLUMN_ANIMAL_NAME = "animal_name";
    private static final String COLUMN_ANIMAL_TIPO = "animal_tipo";

    // create table sql query
    private String CREATE_ANIMAL_TABLE = "CREATE TABLE " + TABLE_ANIMAL + "("
            + COLUMN_ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ANIMAL_NAME + " TEXT,"
            + COLUMN_ANIMAL_TIPO + " TEXT," + COLUMN_USER_ID + " TEXT" + ")";

    // drop table sql query
    private String DROP_ANIMAL_TABLE = "DROP TABLE IF EXISTS " + TABLE_ANIMAL;

    // User table name
    private static final String TABLE_CARRO = "carro";

    // User Table Columns names
    private static final String COLUMN_CARRO_ID = "carro_id";
    private static final String COLUMN_CARRO_PLACA = "carro_placa";
    private static final String COLUMN_CARRO_MARCA = "carro_marca";
    private static final String COLUMN_CARRO_MODELO = "carro_modelo";
    private static final String COLUMN_CARRO_COR = "carro_cor";

    // create table sql query
    private String CREATE_CARRO_TABLE = "CREATE TABLE " + TABLE_CARRO + "("
            + COLUMN_CARRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CARRO_MARCA + " TEXT," + COLUMN_CARRO_PLACA + " TEXT,"
            + COLUMN_CARRO_MODELO + " TEXT," + COLUMN_CARRO_COR + " TEXT," + COLUMN_USER_ID + " TEXT" + ")";

    // drop table sql query
    private String DROP_CARRO_TABLE = "DROP TABLE IF EXISTS " + TABLE_CARRO;

    // User table name
    private static final String TABLE_MORADOR = "morador";

    // User Table Columns names
    private static final String COLUMN_MORADOR_ID = "morador_id";
    private static final String COLUMN_MORADOR_NAME = "morador_name";
    private static final String COLUMN_MORADOR_PARENTESCO = "morador_parentesco";


    // create table sql query
    private String CREATE_MORADOR_TABLE = "CREATE TABLE " + TABLE_MORADOR + "("
            + COLUMN_MORADOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MORADOR_NAME + " TEXT,"
            + COLUMN_MORADOR_PARENTESCO + " TEXT," + COLUMN_USER_ID + " TEXT"+ ")";

    // drop table sql query
    private String DROP_MORADOR_TABLE = "DROP TABLE IF EXISTS " + TABLE_MORADOR;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ANIMAL_TABLE);
        db.execSQL(CREATE_CARRO_TABLE);
        db.execSQL(CREATE_MORADOR_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_ANIMAL_TABLE);
        db.execSQL(DROP_CARRO_TABLE);
        db.execSQL(DROP_MORADOR_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CONDOMINIO, user.getCondominio());
        values.put(COLUMN_USER_NUMERO, user.getNumero());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Usuario> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<Usuario> userList = new ArrayList<Usuario>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Usuario user = new Usuario();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param
     */
    public void updateUser(String id, String nome, String condominio, String numero, String email, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, nome);
        values.put(COLUMN_USER_CONDOMINIO, condominio);
        values.put(COLUMN_USER_NUMERO, numero);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, senha);

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public String getUserByEmail(String email){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_NAME
        };

        String nome = "";

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order


        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            nome = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
        }


        cursor.close();
        db.close();

        return nome;

    }

    public Usuario getUsuario(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Usuario usuario = new Usuario();

        String[] columns = {
                COLUMN_USER_NAME,
                COLUMN_USER_CONDOMINIO,
                COLUMN_USER_NUMERO,
                COLUMN_USER_EMAIL
        };

        String selection = COLUMN_USER_ID + " = ?";

        Cursor cursor = db.query(TABLE_USER, columns, selection,
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                usuario.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                usuario.setCondominio(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CONDOMINIO)));
                usuario.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NUMERO)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact
        return usuario;
    }

    public String getUserIdByEmail(String email){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };

        String id = "";

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order


        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        }


        cursor.close();
        db.close();

        return id;

    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method is to create user record
     *
     * @param animal
     */
    public void addAnimal(Animal animal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, animal.getUser_id());
        values.put(COLUMN_ANIMAL_NAME, animal.getName());
        values.put(COLUMN_ANIMAL_TIPO, animal.getTipo());

        // Inserting Row
        db.insert(TABLE_ANIMAL, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Animal> getAllAnimal() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ANIMAL_ID,
                COLUMN_ANIMAL_TIPO,
                COLUMN_ANIMAL_NAME
        };
        // sorting orders
        String sortOrder =
                COLUMN_ANIMAL_NAME + " ASC";
        List<Animal> animalList = new ArrayList<Animal>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_ANIMAL, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Animal animal = new Animal();
                animal.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_ID))));
                animal.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_NAME)));
                animal.setTipo(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_TIPO)));
                // Adding animal record to list
                animalList.add(animal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return animalList;
    }

    /**
     * This method is to fetch all animals and return the list of animals records by ID
     *
     * @return list
     */
    public List<Animal> getAllAnimalByID(String user_id) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ANIMAL_ID,
                COLUMN_ANIMAL_TIPO,
                COLUMN_ANIMAL_NAME
        };
        // sorting orders
        String sortOrder =
                COLUMN_ANIMAL_NAME + " ASC";
        List<Animal> animalList = new ArrayList<Animal>();

        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_ID + " = ?";

        // selection argument
        String[] selectionArgs = {user_id};
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query(TABLE_ANIMAL, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                sortOrder);                      //The sort order



        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Animal animal = new Animal();
                animal.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_ID))));
                animal.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_NAME)));
                animal.setTipo(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_TIPO)));
                // Adding animal record to list
                animalList.add(animal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return animalList;
    }

    public Animal getAnimal(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Animal animal = new Animal();

        String[] columns = {
                COLUMN_ANIMAL_NAME,
                COLUMN_ANIMAL_TIPO
        };

        String selection = COLUMN_ANIMAL_ID + " = ?";

        Cursor cursor = db.query(TABLE_ANIMAL, columns, selection,
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                animal.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_NAME)));
                animal.setTipo(cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL_TIPO)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact
        return animal;
    }

    /**
     * This method to update user record
     *
     * @param
     */
    public void updateAnimal(String id, String nome, String tipo, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ANIMAL_NAME, nome);
        values.put(COLUMN_ANIMAL_TIPO, tipo);

        // updating row
        db.update(TABLE_ANIMAL, values, COLUMN_ANIMAL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param
     */
    public void deleteAnimal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_ANIMAL, COLUMN_ANIMAL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    /**
     * This method is to create morador record
     *
     * @param morador
     */
    public void addMorador(Morador morador) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, morador.getUser_id());
        values.put(COLUMN_MORADOR_NAME, morador.getName());
        values.put(COLUMN_MORADOR_PARENTESCO, morador.getParentesco());

        // Inserting Row
        db.insert(TABLE_MORADOR, null, values);
        db.close();
    }

    /**
     * This method is to fetch all animals and return the list of animals records by ID
     *
     * @return list
     */
    public List<Morador> getAllMoradorByID(String user_id) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_MORADOR_ID,
                COLUMN_MORADOR_NAME,
                COLUMN_MORADOR_PARENTESCO
        };
        // sorting orders
        String sortOrder =
                COLUMN_MORADOR_NAME + " ASC";
        List<Morador> moradorList = new ArrayList<Morador>();

        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_ID + " = ?";

        // selection argument
        String[] selectionArgs = {user_id};
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query(TABLE_MORADOR, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                sortOrder);                      //The sort order



        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Morador morador = new Morador();
                morador.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MORADOR_ID))));
                morador.setName(cursor.getString(cursor.getColumnIndex(COLUMN_MORADOR_NAME)));
                morador.setParentesco(cursor.getString(cursor.getColumnIndex(COLUMN_MORADOR_PARENTESCO)));
                // Adding animal record to list
                moradorList.add(morador);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return moradorList;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Morador> getAllMorador() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_MORADOR_ID,
                COLUMN_MORADOR_PARENTESCO,
                COLUMN_MORADOR_NAME
        };
        // sorting orders
        String sortOrder =
                COLUMN_MORADOR_NAME + " ASC";
        List<Morador> moradorList = new ArrayList<Morador>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_MORADOR, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Morador morador = new Morador();
                morador.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MORADOR_ID))));
                morador.setName(cursor.getString(cursor.getColumnIndex(COLUMN_MORADOR_NAME)));
                morador.setParentesco(cursor.getString(cursor.getColumnIndex(COLUMN_MORADOR_PARENTESCO)));
                // Adding morador record to list
                moradorList.add(morador);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return moradorList;
    }

    /**
     * This method to update morador record
     *
     * @param
     */
    public void updateMorador(String id, String nome, String parentesco, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_MORADOR_NAME, nome);
        values.put(COLUMN_MORADOR_PARENTESCO, parentesco);

        // updating row
        db.update(TABLE_MORADOR, values, COLUMN_MORADOR_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * This method is to delete morador record
     *
     * @param
     */
    public void deleteMorador(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete morador record by id
        db.delete(TABLE_MORADOR, COLUMN_MORADOR_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * This method is to create carro record
     *
     * @param carro
     */
    public void addCarro(Carro carro) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, carro.getUser_id());
        values.put(COLUMN_CARRO_PLACA, carro.getPlaca());
        values.put(COLUMN_CARRO_MARCA, carro.getMarca());
        values.put(COLUMN_CARRO_MODELO, carro.getModelo());
        values.put(COLUMN_CARRO_COR, carro.getCor());

        // Inserting Row
        db.insert(TABLE_CARRO, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Carro> getAllCarro() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CARRO_ID,
                COLUMN_CARRO_PLACA,
                COLUMN_CARRO_MODELO,
                COLUMN_CARRO_MARCA,
                COLUMN_CARRO_COR
        };
        // sorting orders
        String sortOrder =
                COLUMN_CARRO_MARCA + " ASC";
        List<Carro> carroList = new ArrayList<Carro>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CARRO, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_ID))));
                carro.setPlaca(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_PLACA)));
                carro.setMarca(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_MARCA)));
                carro.setModelo(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_MODELO)));
                carro.setCor(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_COR)));
                // Adding carro record to list
                carroList.add(carro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return carroList;
    }

    /**
     * This method is to fetch all animals and return the list of animals records by ID
     *
     * @return list
     */
    public List<Carro> getAllCarroByID(String user_id) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CARRO_ID,
                COLUMN_CARRO_PLACA,
                COLUMN_CARRO_MARCA,
                COLUMN_CARRO_MODELO,
                COLUMN_CARRO_COR
        };
        // sorting orders
        String sortOrder =
                COLUMN_CARRO_PLACA + " ASC";
        List<Carro> carroList = new ArrayList<Carro>();

        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_ID + " = ?";

        // selection argument
        String[] selectionArgs = {user_id};
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query(TABLE_CARRO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                sortOrder);                      //The sort order



        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {Carro carro = new Carro();
                carro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_ID))));
                carro.setPlaca(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_PLACA)));
                carro.setMarca(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_MARCA)));
                carro.setModelo(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_MODELO)));
                carro.setCor(cursor.getString(cursor.getColumnIndex(COLUMN_CARRO_COR)));
                // Adding morador record to list
                carroList.add(carro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return morador list
        return carroList;
    }

    /**
     * This method to update carro record
     *
     * @param
     */
    public void updateCarro(String id, String placa, String marca, String modelo, String cor, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_CARRO_PLACA, placa);
        values.put(COLUMN_CARRO_MARCA, marca);
        values.put(COLUMN_CARRO_MODELO, modelo);
        values.put(COLUMN_CARRO_COR, cor);

        // updating row
        db.update(TABLE_CARRO, values, COLUMN_CARRO_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param
     */
    public void deleteCarro(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_CARRO, COLUMN_CARRO_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }




}
