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

public class obPMatscanDetail extends AppCompatActivity implements View.OnClickListener {
    TextView matnum, matdesc, bin, qty;
    Button qrcode;
    String Id, Rid, Itemno, Material, Batch, Bin, Quantity, Wno, Sled, Status, Uom, Description;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ob_matscan_detail);
        Intent intent = getIntent();
        Id = intent.getStringExtra("ID");
        Rid = intent.getStringExtra("RID");
        Quantity = intent.getStringExtra("QUANTITY");
        Itemno = intent.getStringExtra("ITEMNO");
        Material = intent.getStringExtra("MATERIAL");
        Description = intent.getStringExtra("DESCRIPTION");
        Wno = intent.getStringExtra("WNO");
        Batch = intent.getStringExtra("BATCH");
        Sled = intent.getStringExtra("SLED");
        Uom = intent.getStringExtra("UOM");
        Status = intent.getStringExtra("STATUS");
        Bin = intent.getStringExtra("BIN");

        matnum = findViewById(R.id.matnum10);
        matdesc = findViewById(R.id.matdes10);
        bin = findViewById(R.id.stbin10);
        qty = findViewById(R.id.qty510);

        matnum.setText("Item no " + Itemno + " : " + Material);
        matdesc.setText("Name : " + Description);
        bin.setText("Storage Bin : " + Bin);
        qty.setText("Quantity : " + Quantity + " " + Uom);

        qrcode = findViewById(R.id.button610);
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
        System.out.println(Rid + "-" + Itemno + "-" + Material);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                if (result.getContents().equals(Rid + "-" + Itemno + "-" + Material)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(result.getContents() + " - " + Description);
                    builder.setTitle("Material Search");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new GetMethodPut().execute("https://simpson.thinkawm.com/api/TOOB/update.php");
                            Intent intent = new Intent(obPMatscanDetail.this, queueoto.class);
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
                jsonObjectFinal.put("rid", Rid);
                jsonObjectFinal.put("wno", Wno);
                jsonObjectFinal.put("description", Description);
                jsonObjectFinal.put("itemno", Itemno);
                jsonObjectFinal.put("sled", Sled);
                jsonObjectFinal.put("material", Material);
                jsonObjectFinal.put("batch", Batch);
                jsonObjectFinal.put("quantity", Quantity);
                jsonObjectFinal.put("uom", Uom);
                jsonObjectFinal.put("bin", Bin);
                jsonObjectFinal.put("status", "confirmed");


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