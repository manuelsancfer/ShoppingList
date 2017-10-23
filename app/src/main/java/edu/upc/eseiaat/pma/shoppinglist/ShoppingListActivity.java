package edu.upc.eseiaat.pma.shoppinglist;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ArrayList<String> itemList;
    private ArrayAdapter adapter;

    private ListView list;
    private Button btn_add;
    private EditText edit_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        list = (ListView) findViewById(R.id.list);          //obtener lista
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_item = (EditText) findViewById(R.id.edit_item);


        itemList = new ArrayList<>();                       //creamos una lista vacia
        itemList.add("Patatas");                            //añadir item a lista
        itemList.add("Papel WC");
        itemList.add("Zanahoria");
        itemList.add("Danone");


        adapter = new ArrayAdapter<>(                       //creamos adaptador
                this,                                       //contexto
                android.R.layout.simple_list_item_1,        //recurso de la lista
                itemList                                    //los datos
        );

        //apretar el botón, mirar el contenido de la caja de texto y meterlo en la lista:
        btn_add.setOnClickListener(new View.OnClickListener() { //
            @Override
            public void onClick(View view) {
                addItem();                                  //añade a la lista
            }
        });


        //para poder añadir desde el teclado con el botón verde:
        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }
        });


        list.setAdapter(adapter);


        //Para poder borrar:
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos);                           //método para borrar
                return true;
            }
        });
    }

    private void maybeRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);    //constructor de dialogo
        builder.setTitle(R.string.confirm);                     //título dialogo

        // para poder traducir el mensage del cuadro de dialogo:
        String fmt = getResources().getString(R.string.confirm_message);
        builder.setMessage(String.format(fmt,itemList.get(pos)));

        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList.remove(pos);                                   //eliminar seleccionado
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.create().show();
    }

    private void addItem() {
        String item_text = edit_item.getText().toString();      //obtener el texto
        if(!item_text.isEmpty()) {                              //evitar añadir texto vacio
            itemList.add(item_text);                            //añadimos a la lista
            adapter.notifyDataSetChanged();                     //notificar al adaptador
            edit_item.setText("");                              //borrar lo que añades de edittext
        }
    }
}
