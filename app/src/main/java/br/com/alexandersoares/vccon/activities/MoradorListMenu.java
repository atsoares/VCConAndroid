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
import br.com.alexandersoares.vccon.adapters.MoradoresRecyclerAdapter;
import br.com.alexandersoares.vccon.model.Morador;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class MoradorListMenu extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = MoradorListMenu.this;
    private TextView textViewName;
    private TextView textViewParentesco;
    private EditText textInputEditTextName;
    private EditText textInputEditTextParentesco;
    private EditText textInputEditTextId;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    private RecyclerView recyclerViewMoradores;
    private AlertDialog.Builder alertDialog;
    private List<Morador> listMoradores;
    private MoradoresRecyclerAdapter moradoresRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moradores_da_casa);
        getSupportActionBar().setTitle("Moradores");

        initViews();

        initDialog();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewMoradores = (RecyclerView) findViewById(R.id.recyclerViewMoradores);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewParentesco = (TextView) findViewById(R.id.textViewParentesco);

        listMoradores = new ArrayList<>();
        moradoresRecyclerAdapter = new MoradoresRecyclerAdapter(listMoradores);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMoradores.setLayoutManager(mLayoutManager);
        recyclerViewMoradores.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMoradores.setHasFixedSize(true);
        recyclerViewMoradores.setAdapter(moradoresRecyclerAdapter);

        moradoresRecyclerAdapter.notifyDataSetChanged();

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
                    databaseHelper.deleteMorador(listMoradores.get(position).getId());
                    moradoresRecyclerAdapter.removeItem(position);

                } else {
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle("Editar morador");
                    textInputEditTextName.setTag(listMoradores.get(position).getId());
                    textInputEditTextName.setText(listMoradores.get(position).getName());
                    textInputEditTextParentesco.setText(listMoradores.get(position).getParentesco());
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
        itemTouchHelper.attachToRecyclerView(recyclerViewMoradores);
    }
    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initDialog(){
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_morador_layout,null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Morador morador = new Morador();
                if(add){
                    add = false;

                    morador.setName(textInputEditTextName.getText().toString());
                    morador.setParentesco(textInputEditTextParentesco.getText().toString());
                    morador.setUser_id(getIntent().getStringExtra("USER_ID"));

                    moradoresRecyclerAdapter.add(morador);
                    databaseHelper.addMorador(morador);
                    dialog.dismiss();
                } else {

                    morador.setName(textInputEditTextName.getText().toString());
                    morador.setParentesco(textInputEditTextParentesco.getText().toString());
                    morador.setUser_id(getIntent().getStringExtra("USER_ID"));

                    String nome = morador.getName();
                    String parentesco = morador.getParentesco();
                    String userId = morador.getUser_id();
                    String id = textInputEditTextName.getTag().toString();

                    databaseHelper.updateMorador(id, nome, parentesco, userId);

                    listMoradores.set(edit_position,morador);
                    moradoresRecyclerAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }

            }
        });
        textInputEditTextName = (EditText)view.findViewById(R.id.textInputEditTextName);
        textInputEditTextParentesco = (EditText)view.findViewById(R.id.textInputEditTextParentesco);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fab:
                removeView();
                add = true;
                alertDialog.setTitle("Adicionar morador");
                textInputEditTextName.setText("");
                textInputEditTextParentesco.setText("");
                alertDialog.show();
                break;
        }
    }

    public boolean checkList(){

        String user_id = getIntent().getStringExtra("USER_ID");
        List<Morador> getAll = databaseHelper.getAllMoradorByID(user_id);
        if(getAll.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void popular(){

        String user_id = getIntent().getStringExtra("USER_ID");
        List<Morador> getAll = databaseHelper.getAllMoradorByID(user_id);
        moradoresRecyclerAdapter.addAll(getAll);

    }

}