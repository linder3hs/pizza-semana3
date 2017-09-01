package com.linderhassinger.semana3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class OrdenActivity extends AppCompatActivity {
    public static final int TIME_NOTIFICION = 3000;
    private Spinner spinnerTipo;
    private Button ordenfinal;
    private EditText dire;
    private CheckBox c1, c2;
    private RadioButton r1, r2, r3;
    private String amer = "Americana";
    private String meet = "Meet Lover";
    private String hawa = "Hawaina";
    private String supe = "Super suprema";
    private String mgru = "masa gruesa";
    private String mdel = "masa delgada";
    private String mart = "masa artesanal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);









        spinnerTipo = (Spinner) findViewById(R.id.spinnertipo);
        ordenfinal = (Button) findViewById(R.id.ordenfinal);
        c1 = (CheckBox) findViewById(R.id.queso);
        c2 = (CheckBox) findViewById(R.id.jamon);
        r1 = (RadioButton) findViewById(R.id.mgruesa);
        r2 = (RadioButton) findViewById(R.id.mdelgada);
        r3 = (RadioButton) findViewById(R.id.martesanal);
        dire = (EditText) findViewById(R.id.direc);

        String[] tpizza = {amer, meet, hawa, supe};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tpizza);
        spinnerTipo.setAdapter(adapter);


        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String tpizza = parent.getItemAtPosition(pos).toString();

                Toast.makeText(parent.getContext(),
                        "Su tipo de masa es " + tpizza,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg) {

            }

        });


    }

    public void ordenPizza(View view) {

        //Caclulo de tipo de pizza
        String item = (String) spinnerTipo.getSelectedItem();
        int precio = 0;
        if (item == amer) {
            precio = 40;
        } else if (item == meet) {
            precio = 60;
        } else if (item == hawa) {
            precio = 45;
        } else if (item == supe) {
            precio = 85;
        }

        //Calculo de extras
        int extra = 0;
        if (c1.isChecked()) {
            extra = 8;
        }
        if (c2.isChecked()) {
            extra = 12;
        }
        if (c1.isChecked() && c2.isChecked()) {
            extra = 20;
        }
        //Tipo de masa
        String masa = "";
        if (r1.isChecked()) {
            masa = mgru;
        } else if (r2.isChecked()) {
            masa = mdel;
        } else if (r3.isChecked()) {
            masa = mart;
        }
        int total = 0;
        total = precio + extra;
        if (dire.getText().toString().isEmpty()) {
            dire.setError("Ingrese una dirección");
        } else {

            //Notificacion
            Intent intent = new Intent(this, OrdenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            // Notification
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Su pedido esta en camino")
                    .setContentText("Gracias por su preferencia")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.pizza_icon)
                    .build();

            // Notification manager
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);


            //Alerta
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación de pedido");
            alertDialog.setMessage("Su pedido de pizza " + item + " con " + masa + " a " + "$" + total + " +IGV esta en proceso de envio");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}
