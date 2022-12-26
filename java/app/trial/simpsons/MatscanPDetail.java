package app.trial.simpsons;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class MatscanPDetail extends AppCompatActivity implements View.OnClickListener {
    TextView matnum, matdesc, bin, qty;
    Button qrcode;
    String Id, Wnumber, Tono, Itemno, Material, Matdes, Location, Batch, Qty, Uom, Perplt, peruom, Bincap, Bin, Availstock, Availuom, OBqty, OBuom, Exstk, Sldate, Status, Convqt, Excess;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matscan_detail);
        Intent intent = getIntent();

        Id = intent.getStringExtra("ID");
        Wnumber = intent.getStringExtra("WNUMBER");
        Tono = intent.getStringExtra("TONO");
        Itemno = intent.getStringExtra("ITEMNO");
        Material = intent.getStringExtra("MATERIAL");
        Matdes = intent.getStringExtra("MATDES");
        Location = intent.getStringExtra("STLOC");
        Batch = intent.getStringExtra("BATCH");
        Qty = intent.getStringExtra("GRQTY");
        Uom = intent.getStringExtra("GRUOM");
        Perplt = intent.getStringExtra("PERPLT");
        peruom = intent.getStringExtra("PERUOM");
        Bincap = intent.getStringExtra("BINCAP");
        Bin = intent.getStringExtra("STKBIN");
        Availstock = intent.getStringExtra("AVAILSTOCK");
        Availuom = intent.getStringExtra("AVAILUOM");
        OBqty = intent.getStringExtra("OBQTY");
        OBuom = intent.getStringExtra("OBUOM");
        Exstk = intent.getStringExtra("EXTSTK");
        Sldate = intent.getStringExtra("SLD");
        Status = intent.getStringExtra("STATUS");
        Convqt = intent.getStringExtra("CONVQT");
        Excess = intent.getStringExtra("EXCESS");

        matnum = findViewById(R.id.matnum1);
        matdesc = findViewById(R.id.matdes1);
        bin = findViewById(R.id.stbin1);
        qty = findViewById(R.id.qty51);

        matnum.setText("Item no " + Itemno + " : " + Material);
        matdesc.setText("Name : " + Matdes);
        bin.setText("Storage Bin : " + Bin);
        qty.setText("Quantity : " + Qty + " " + Uom);

        qrcode = findViewById(R.id.button61);
        qrcode.setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scancode();
            }
        }, 100);
    }


    @Override
    public void onClick(View v) {
        scancode();
    }

    public void scancode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Material");
        integrator.initiateScan();
        System.out.println(Tono + "-" + Material);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                if (result.getContents().equals(Tono + "-" + Itemno)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Transfer Order No : " + Tono + "\n" + "Item No : " + Itemno + "\n" + "Material : " + Material + "\n" + "Description : " + Matdes);
                    builder.setTitle("Material Search");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new GetMethodPut().execute("https://simpson.thinkawm.com/api/TO/update.php");
                            Intent intent = new Intent(MatscanPDetail.this, queueito.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    Toast.makeText(this, "Material Mismatch", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class GetMethodPut extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection1 = null;

            try {
                url = new URL(strings[0]);
                urlConnection1 = (HttpURLConnection) url.openConnection();

                urlConnection1.setRequestMethod("PUT");
                urlConnection1.setDoInput(true);
                urlConnection1.setDoOutput(true);
                urlConnection1.connect();

                JSONObject jsonObjectFinal = new JSONObject();

                jsonObjectFinal.put("id", Id);
                jsonObjectFinal.put("wnumber", Wnumber);
                jsonObjectFinal.put("tono", Tono);
                jsonObjectFinal.put("itemno", Itemno);
                jsonObjectFinal.put("material", Material);
                jsonObjectFinal.put("matdes", Matdes);
                jsonObjectFinal.put("stloc", Location);
                jsonObjectFinal.put("batch", Batch);
                jsonObjectFinal.put("grqty", Qty);
                jsonObjectFinal.put("gruom", Uom);
                jsonObjectFinal.put("perplt", "");
                jsonObjectFinal.put("peruom", "");
                jsonObjectFinal.put("bincap", Bincap);
                jsonObjectFinal.put("stkbin", Bin);
                jsonObjectFinal.put("availstock", Perplt);
                jsonObjectFinal.put("availuom", peruom);
                jsonObjectFinal.put("obqty", OBqty);
                jsonObjectFinal.put("obuom", OBuom);
                jsonObjectFinal.put("extstk", Exstk);
                jsonObjectFinal.put("sld", Sldate);
                jsonObjectFinal.put("status", "confirmed");
                jsonObjectFinal.put("convqt", Convqt);
                jsonObjectFinal.put("excess", Excess);

                OutputStream os = urlConnection1.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());
                writer.flush();
                writer.close();
                os.close();
                urlConnection1.connect();

                int responseCode = urlConnection1.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream1(urlConnection1.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("Response", "" + server_response);


        }
    }

    private String readStream1(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }


}