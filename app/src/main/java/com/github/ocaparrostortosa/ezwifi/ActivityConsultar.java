package com.github.ocaparrostortosa.ezwifi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.DatosWifiDAO;
import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.UsuarioDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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
    //
    private DatabaseReference clavesTotales;
    private UsuarioDAO userDao;
    private String username;
    private String userPath;
    private String numeroDeClaves = "0";
    private TableLayout tableLayout;
    private TableRow filaTabla;
    private TextView primeraColumna;
    private TextView segundaColumna;
    private TextView terceraColumna;
    private CheckBox checkBox;
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
            filaTabla = new TableRow(this);
            filaTabla.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            lugarRef = database.getReference(userPath + "/clavesGuardadas/" + i + "/lugarWifi");
            nombreRef = database.getReference(userPath + "/clavesGuardadas/" + i + "/nombreWifi");
            claveRef = database.getReference(userPath + "/clavesGuardadas/" + i + "/claveWifi");

            lugarRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lugarWifi = dataSnapshot.getValue().toString();
                    obtenerValorDelLugar(lugarWifi);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            nombreRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nombreWifi = dataSnapshot.getValue().toString();
                    obtenerValorDelNombre(nombreWifi);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            claveRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    claveWifi = dataSnapshot.getValue().toString();
                    obtenerValorDeLaClave(claveWifi);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if(i == numeroTotalClaves)
                tableLayout.addView(filaTabla);
        }

    }

    private void obtenerValorDelLugar(String lugarWifi){
        primeraColumna = new TextView(getApplicationContext());
        primeraColumna.setText(lugarWifi);
        primeraColumna.setTextColor(Color.BLACK);
        primeraColumna.setTextSize(14);

        filaTabla.addView(primeraColumna);
        System.out.println("Primera columna añadida");
    }

    private void obtenerValorDelNombre(String nombreWifi){
        segundaColumna = new TextView(getApplicationContext());
        segundaColumna.setText(nombreWifi);
        segundaColumna.setTextColor(Color.BLACK);
        segundaColumna.setTextSize(14);

        filaTabla.addView(segundaColumna);
        System.out.println("Ter columna añadida");
    }

    private void obtenerValorDeLaClave(String claveWifi){
        terceraColumna = new TextView(getApplicationContext());
        terceraColumna.setText(claveWifi);
        terceraColumna.setTextColor(Color.BLACK);
        terceraColumna.setTextSize(14);

        filaTabla.addView(terceraColumna);

        checkBox = new CheckBox(getApplicationContext());
        filaTabla.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        filaTabla.addView(checkBox);
        System.out.println("Seg columna añadida");

    }
}
