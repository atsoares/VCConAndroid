package br.com.alexandersoares.vccon.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandersoares.vccon.R;
import br.com.alexandersoares.vccon.adapters.AnimalsRecyclerAdapter;
import br.com.alexandersoares.vccon.model.Animal;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class MoradorListMenu extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = MoradorListMenu.this;
    private TextView textViewName;
    private TextView textViewTipo;
    private EditText textInputEditTextName;
    private EditText textInputEditTextTipo;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    private RecyclerView recyclerViewAnimais;
    private AlertDialog.Builder alertDialog;
    private List<Animal> listAnimals;
    private AnimalsRecyclerAdapter animalsRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_animais);
        getSupportActionBar().setTitle("Meus animais");

        initViews();

        initDialog();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewAnimais = (RecyclerView) findViewById(R.id.recyclerViewAnimais);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewTipo = (TextView) findViewById(R.id.textViewTipo);

        listAnimals  = new ArrayList<>();
        animalsRecyclerAdapter = new AnimalsRecyclerAdapter(listAnimals);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAnimais.setLayoutManager(mLayoutManager);
        recyclerViewAnimais.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAnimais.setHasFixedSize(true);
        recyclerViewAnimais.setAdapter(animalsRecyclerAdapter);

        animalsRecyclerAdapter.notifyDataSetChanged();

        String user_id = getIntent().getStringExtra("USER_ID");

        databaseHelper = new DatabaseHelper(activity);
        String idUser = databaseHelper.getUserIdByEmail(getIntent().getStringExtra("EMAIL"));


        if (user_id == null){
            if (idUser == null){
                initSwipe();
            }
        }else{
            if(checkList() == true){
                popular();
                initSwipe();
            }
        }

    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    databaseHelper.deleteAnimal(listAnimals.get(position).getId());
                    animalsRecyclerAdapter.removeItem(position);

                } else {
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle("Editar animal");
                    textInputEditTextName.setText(listAnimals.get(position).getName());
                    textInputEditTextTipo.setText(listAnimals.get(position).getTipo());
                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewAnimais);
    }
    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initDialog(){
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_animal_layout,null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Animal animal = new Animal();
                if(add){
                    add = false;

                    animal.setName(textInputEditTextName.getText().toString());
                    animal.setTipo(textInputEditTextTipo.getText().toString());
                    animal.setUser_id(getIntent().getStringExtra("USER_ID"));

                    animalsRecyclerAdapter.add(animal);
                    databaseHelper.addAnimal(animal);
                    dialog.dismiss();
                } else {

                    animal.setName(textInputEditTextName.getText().toString());
                    animal.setTipo(textInputEditTextTipo.getText().toString());
                    animal.setUser_id(getIntent().getStringExtra("USER_ID"));


                    databaseHelper.updateAnimal(animal);

                    listAnimals.set(edit_position,animal);
                    animalsRecyclerAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }

            }
        });
        textInputEditTextName = (EditText)view.findViewById(R.id.textInputEditTextName);
        textInputEditTextTipo = (EditText)view.findViewById(R.id.textInputEditTextTipo);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fab:
                removeView();
                add = true;
                alertDialog.setTitle("Adicionar animal");
                textInputEditTextName.setText("");
                textInputEditTextTipo.setText("");
                alertDialog.show();
                break;
        }
    }

    public boolean checkList(){

        String user_id = getIntent().getStringExtra("USER_ID");
        List<Animal> getAll = databaseHelper.getAllAnimalByID(user_id);
        if(getAll.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void popular(){

        String user_id = getIntent().getStringExtra("USER_ID");
        List<Animal> getAll = databaseHelper.getAllAnimalByID(user_id);
        animalsRecyclerAdapter.addAll(getAll);

    }

}