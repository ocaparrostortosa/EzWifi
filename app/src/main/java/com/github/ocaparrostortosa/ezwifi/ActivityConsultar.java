package com.github.ocaparrostortosa.ezwifi;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.DatosWifiDAO;
import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.UsuarioDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Oscar on 29/06/2017.
 */

public class ActivityConsultar extends AppCompatActivity{

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    //
    private DatabaseReference lugarRef;
    private DatabaseReference nombreRef;
    private DatabaseReference claveRef;
    private DatabaseReference elementoRef;
    //
    private DatabaseReference clavesTotales;
    private UsuarioDAO userDao;
    private String username;
    private String userPath;
    private String numeroDeClaves = "0";
    private TableLayout tableLayout;
    private TableRow filaTabla;
    private ImageView iconoCopiar;
    private TableRow.LayoutParams layoutFila =
            new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    private TableRow.LayoutParams layoutElemento =
            new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
    private String lugarWifi;
    private String nombreWifi;
    private String claveWifi;
    private int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        getSupportActionBar().setTitle("Consultar WiFi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = getIntent().getStringExtra("EXTRA_CURRENTUSER");

        inicialize();
    }

    /**
     * Método que nos permite volver atrás para el botón de la flecha en la actionBar.
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }

    private void inicialize(){
        database = FirebaseDatabase.getInstance();

        userDao = new UsuarioDAO(database);

        userPath = userDao.getUserPath(username);

        clavesTotales = database.getReference(userPath + "/clavesGuardadas/clavesTotales");



        clavesTotales.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null)
                    numeroDeClaves = "null";
                else
                    numeroDeClaves = dataSnapshot.getValue().toString();
                printInformationIntoLayout();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void printInformationIntoLayout(){
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        TextView tableTextTitulo = (TextView) findViewById(R.id.tableTextTitulo);
        TextView tableTextContenido = (TextView) findViewById(R.id.tableTextContenido);

        if(numeroDeClaves.equals("null")){
            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) tableTextTitulo.getLayoutParams();
            titleParams.weight = 1;
            tableTextTitulo.setLayoutParams(titleParams);

            LinearLayout.LayoutParams contentParams = (LinearLayout.LayoutParams) tableTextContenido.getLayoutParams();
            contentParams.weight = 1;
            tableTextContenido.setLayoutParams(contentParams);

            setTextConfiguration(tableTextTitulo, tableTextContenido);

        }else{
            tableTextTitulo.setVisibility(View.GONE);
            tableTextContenido.setVisibility(View.GONE);

            LinearLayout.LayoutParams tableParams = (LinearLayout.LayoutParams) tableTextContenido.getLayoutParams();
            tableParams.weight = 1;
            tableLayout.setLayoutParams(tableParams);

            setTableConfiguration(tableLayout, numeroDeClaves);
        }
    }

    private void setTextConfiguration(TextView titulo, TextView contenido){
        titulo.setText("¡Lo sentimos! :(\n");
        contenido.setText("Aún no has guardado ninguna contraseña para ningún WiFi. Prueba a guardar una en:\n\n "
                + "Menú principal ➔ GUARDAR CONTRASEÑA");
    }

    private void setTableConfiguration(TableLayout tableLayout, String numeroDeCLaves){
        int numeroTotalClaves = Integer.parseInt(numeroDeCLaves);

        for(i=1 ; i <= numeroTotalClaves ; i++){
            elementoRef = database.getReference(userPath + "/clavesGuardadas/" + i);

            elementoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lugarWifi = dataSnapshot.child("lugarWifi").getValue().toString();
                    nombreWifi = dataSnapshot.child("nombreWifi").getValue().toString();
                    claveWifi = dataSnapshot.child("claveWifi").getValue().toString();
                    addFilaALaTabla(lugarWifi, nombreWifi, claveWifi, filaTabla);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    private void addFilaALaTabla(String lugar, String nombre, String clave, TableRow filaTabla){
        filaTabla = new TableRow(this);

        filaTabla.setLayoutParams(layoutFila);
        filaTabla.setGravity(Gravity.CENTER);

        this.claveWifi = clave;
        this.filaTabla = filaTabla;

        List<String> elementos = new ArrayList<>();
        elementos.add(lugar);
        elementos.add(nombre);
        elementos.add(clave);

        insertarElementosFila(elementos);

        System.out.println("Añadiendo fila...");
        if(filaTabla.getParent() != null)
            ((ViewGroup)filaTabla.getParent()).removeView(filaTabla);

        filaTabla.setGravity(Gravity.CENTER);

        System.out.println(filaTabla.toString());
        tableLayout.addView(filaTabla);

    }

    private void insertarElementosFila(List<String> elementos){
        for(int i = 0; i< elementos.size(); i++)
        {
            TextView texto = new TextView(this);
            texto.setText(String.valueOf(elementos.get(i)));
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setLayoutParams(layoutElemento);
            texto.setTextSize(15);
            texto.setPadding(20,0,0,20);

            filaTabla.addView(texto);
        }
        iconoCopiar = new ImageView(this);
        iconoCopiar.setImageResource(R.drawable.icono_copiar2);
        iconoCopiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Clave copiada", claveWifi);
                clipboard.setPrimaryClip(clip);

                Toast toast = Toast.makeText(getApplicationContext(), "¡Clave copiada al portapapeles! :D", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        /**
         * A LA HORA DE COPIAR LAS CLAVES SIEMPRE COPIA LA ULTIMA AGREGADA. ARREGLAR EL TEMA DE LA RESOLUCION DE LA LETRA Y DE
         * LA IMAGEN
         */

        filaTabla.addView(iconoCopiar);
    }
}
