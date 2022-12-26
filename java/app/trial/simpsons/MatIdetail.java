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


public class MatIdetail extends AppCompatActivity implements View.OnClickListener {
    TextView matnum, matdesc, bin, qty;
    Button qrcode;
    String Id, Wnumber, Tono, Itemno, Material, Matdes, Location, Batch, Qty, Uom, Perplt, peruom, Bincap, Bin, Availstock, Availuom, OBqty, OBuom, Exstk, Sldate, Status, Convqt, Excess;
    String imtodate, imrefdoc, imrefer;
    int count = 1;

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
        imtodate = intent.getStringExtra("IMTODATE");
        imrefdoc = intent.getStringExtra("IMREFDOC");
        imrefer = intent.getStringExtra("IMREFER");

        matnum = findViewById(R.id.matnum1);
        matdesc = findViewById(R.id.matdes1);
        bin = findViewById(R.id.stbin1);
        qty = findViewById(R.id.qty51);

        matnum.setText("Item no " + Itemno + " : " + Material);
        matdesc.setText("Name : " + Matdes);
        bin.setText("Storage Bin : " + Bin);
        qty.setText("Quantity : " + Perplt + " " + Uom);

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                System.out.println(result.getContents());
                if (result.getContents().equals(Bin)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Transfer Order No : " + Tono + "\n" + "Item No : " + Itemno + "\n" + "Material : " + Material + " - " + Matdes + "\n" + "Scanned Quantity : " + count + " " + Uom + "\n" + "Storage Bin : " + Bin);
                    builder.setTitle("Material Search");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new GetMethodPut().execute("https://simpson.thinkawm.com/api/TO/update.php");
                            new GetMethodCheck().execute("https://simpson.thinkawm.com/api/TO/zzznew_single_read.php?tono=" + Tono);
                            Intent intent = new Intent(MatIdetail.this, queueito.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Scan Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            count++;
                            if ((count - 1) == Integer.parseInt(Perplt)) {
                                new GetMethodPut().execute("https://simpson.thinkawm.com/api/TO/update.php");
                                new GetMethodCheck().execute("https://simpson.thinkawm.com/api/TO/zzznew_single_read.php?tono=" + Tono);
                                Intent intent = new Intent(MatIdetail.this, queueito.class);
                                startActivity(intent);
                                Toast.makeText(MatIdetail.this, "Scanned All Items", Toast.LENGTH_LONG).show();
                            } else {
                                scancode();
                            }
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

    public class GetMethodCheck extends AsyncTask<String, Void, String> {
        String server_response;

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection1 = null;

            try {
                url = new URL(strings[0]);
                urlConnection1 = (HttpURLConnection) url.openConnection();

                urlConnection1.setRequestMethod("GET");
                urlConnection1.setDoInput(true);
                urlConnection1.setDoOutput(true);
                urlConnection1.connect();

//                JSONObject jsonObjectFinal = new JSONObject();
//
//                jsonObjectFinal.put("tono", Tono);
//                jsonObjectFinal.put("store", Store);
//                jsonObjectFinal.put("todate", Todate);
//                jsonObjectFinal.put("refdoc", Refdoc);
//                jsonObjectFinal.put("refer", Refer);
//                jsonObjectFinal.put("val", "confirmed");
//
//                OutputStream os = urlConnection1.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
//                writer.write(jsonObjectFinal.toString());
//                writer.flush();
//                writer.close();
//                os.close();
//                urlConnection1.connect();

                int responseCode = urlConnection1.getResponseCode();

                if (responseCode == 404) {
                    new GetCheckPut().execute("https://simpson.thinkawm.com/api/TOheader/update.php");
                    Intent intent = new Intent(MatIdetail.this, queueito.class);
//                    Toast.makeText(MatIdetail.this, "Transfer Order Confirmed", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    System.out.println("In queue");
                }
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection1.getInputStream());
                    Log.v("CatalogClient", server_response);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

    private String readStream(InputStream in) {
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

    public class GetCheckPut extends AsyncTask<String, Void, String> {
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

                jsonObjectFinal.put("tono", Tono);
                jsonObjectFinal.put("store", Wnumber);
                jsonObjectFinal.put("todate", imtodate);
                jsonObjectFinal.put("refdoc", imrefdoc);
                jsonObjectFinal.put("refer", imrefer);
                jsonObjectFinal.put("val", "confirmed");

                OutputStream os = urlConnection1.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());
                writer.flush();
                writer.close();
                os.close();
                urlConnection1.connect();

                int responseCode = urlConnection1.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream3(urlConnection1.getInputStream());
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

    private String readStream3(InputStream in) {
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