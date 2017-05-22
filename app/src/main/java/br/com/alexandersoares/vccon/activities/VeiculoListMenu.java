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
import br.com.alexandersoares.vccon.adapters.CarrosRecyclerAdapter;
import br.com.alexandersoares.vccon.model.Carro;
import br.com.alexandersoares.vccon.sql.DatabaseHelper;

/**
 * Created by alexl on 10/05/2017.
 */

public class VeiculoListMenu extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = VeiculoListMenu.this;
    private TextView textViewPlaca;
    private TextView textViewMarca;
    private TextView textViewModelo;
    private TextView textViewCor;
    private EditText textInputEditTextPlaca;
    private EditText textInputEditTextMarca;
    private EditText textInputEditTextModelo;
    private EditText textInputEditTextCor;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    private RecyclerView recyclerViewVeiculos;
    private AlertDialog.Builder alertDialog;
    private List<Carro> listCarros;
    private CarrosRecyclerAdapter veiculosRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_veiculos);
        getSupportActionBar().setTitle("Meus veículos");

        initViews();

        initDialog();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewVeiculos = (RecyclerView) findViewById(R.id.recyclerViewVeiculos);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        textViewPlaca = (TextView) findViewById(R.id.textViewPlaca);
        textViewMarca = (TextView) findViewById(R.id.textViewMarca);
        textViewModelo = (TextView) findViewById(R.id.textViewModelo);
        textViewCor = (TextView) findViewById(R.id.textViewCor);

        listCarros  = new ArrayList<>();
        veiculosRecyclerAdapter = new CarrosRecyclerAdapter(listCarros);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewVeiculos.setLayoutManager(mLayoutManager);
        recyclerViewVeiculos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVeiculos.setHasFixedSize(true);
        recyclerViewVeiculos.setAdapter(veiculosRecyclerAdapter);

        veiculosRecyclerAdapter.notifyDataSetChanged();

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
                    databaseHelper.deleteCarro(listCarros.get(position).getId());
                    veiculosRecyclerAdapter.removeItem(position);

                } else {
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle("Editar veículo");
                    textInputEditTextPlaca.setTag(listCarros.get(position).getId());
                    textInputEditTextPlaca.setText(listCarros.get(position).getPlaca());
                    textInputEditTextMarca.setText(listCarros.get(position).getMarca());
                    textInputEditTextModelo.setText(listCarros.get(position).getModelo());
                    textInputEditTextCor.setText(listCarros.get(position).getCor());
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
        itemTouchHelper.attachToRecyclerView(recyclerViewVeiculos);
    }
    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initDialog(){
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_veiculo_layout,null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Carro carro = new Carro();
                if(add){
                    add = false;

                    carro.setPlaca(textInputEditTextPlaca.getText().toString());
                    carro.setMarca(textInputEditTextMarca.getText().toString());
                    carro.setModelo(textInputEditTextModelo.getText().toString());
                    carro.setCor(textInputEditTextCor.getText().toString());
                    carro.setUser_id(getIntent().getStringExtra("USER_ID"));

                    veiculosRecyclerAdapter.add(carro);
                    databaseHelper.addCarro(carro);
                    dialog.dismiss();
                } else {

                    carro.setPlaca(textInputEditTextPlaca.getText().toString());
                    carro.setMarca(textInputEditTextMarca.getText().toString());
                    carro.setModelo(textInputEditTextModelo.getText().toString());
                    carro.setCor(textInputEditTextCor.getText().toString());
                    carro.setUser_id(getIntent().getStringExtra("USER_ID"));

                    String placa = carro.getPlaca();
                    String marca = carro.getMarca();
                    String modelo = carro.getModelo();
                    String cor = carro.getCor();
                    String userId = carro.getUser_id();
                    String id = textInputEditTextPlaca.getTag().toString();

                    databaseHelper.updateCarro(id, placa, marca, modelo, cor, userId);

                    listCarros.set(edit_position,carro);
                    veiculosRecyclerAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }

            }
        });
        textInputEditTextPlaca = (EditText)view.findViewById(R.id.textInputEditTextPlaca);
        textInputEditTextMarca = (EditText)view.findViewById(R.id.textInputEditTextMarca);
        textInputEditTextModelo = (EditText)view.findViewById(R.id.textInputEditTextModelo);
        textInputEditTextCor = (EditText)view.findViewById(R.id.textInputEditTextCor);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fab:
                removeView();
                add = true;
                alertDialog.setTitle("Adicionar veículo");
                textInputEditTextPlaca.setText("");
                textInputEditTextMarca.setText("");
                textInputEditTextModelo.setText("");
                textInputEditTextCor.setText("");
                alertDialog.show();
                break;
        }
    }

    public boolean checkList(){

        String user_id = getIntent().getStringExtra("USER_ID");
        List<Carro> getAll = databaseHelper.getAllCarroByID(user_id);
        if(getAll.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void popular(){

        String user_id = getIntent().getStringExtra("USER_ID");
        List<Carro> getAll = databaseHelper.getAllCarroByID(user_id);
        veiculosRecyclerAdapter.addAll(getAll);

    }

}