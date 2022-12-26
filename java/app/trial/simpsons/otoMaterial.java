package app.trial.simpsons;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
import java.util.ArrayList;

import static app.trial.simpsons.queueoto.EXTRA_CID;
import static app.trial.simpsons.queueoto.EXTRA_DORDERNO;
import static app.trial.simpsons.queueoto.EXTRA_CREATEDATE;
import static app.trial.simpsons.queueoto.EXTRA_REFER;
import static app.trial.simpsons.queueoto.EXTRA_CHANGEDATE;
import static app.trial.simpsons.queueoto.EXTRA_VAL;

import static app.trial.simpsons.itoMaterial.EXTRA_WNUMBER;
import static app.trial.simpsons.itoMaterial.EXTRA_TONO;
import static app.trial.simpsons.itoMaterial.EXTRA_MATDES;
import static app.trial.simpsons.itoMaterial.EXTRA_STLOC;
import static app.trial.simpsons.itoMaterial.EXTRA_GRQTY;
import static app.trial.simpsons.itoMaterial.EXTRA_GRUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_PERPLT;
import static app.trial.simpsons.itoMaterial.EXTRA_PERUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_BINCAP;
import static app.trial.simpsons.itoMaterial.EXTRA_STKBIN;
import static app.trial.simpsons.itoMaterial.EXTRA_AVAILSTOCK;
import static app.trial.simpsons.itoMaterial.EXTRA_AVAILUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_OBQTY;
import static app.trial.simpsons.itoMaterial.EXTRA_OBUOM;
import static app.trial.simpsons.itoMaterial.EXTRA_EXTSTK;
import static app.trial.simpsons.itoMaterial.EXTRA_SLD;
import static app.trial.simpsons.itoMaterial.EXTRA_CONVQT;
import static app.trial.simpsons.itoMaterial.EXTRA_EXCESS;

public class otoMaterial extends AppCompatActivity implements otoAdapter.OnItemClickListener {


    public static final String EXTRA_ID = "id";
    public static final String EXTRA_RID = "rid";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_ITEMNO = "itemno";
    public static final String EXTRA_MATERIAL = "material";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_BIN = "bin";
    public static final String EXTRA_BATCH = "batch";
    public static final String EXTRA_QUANTITY = "quantity";
    public static final String EXTRA_UOM = "uom";
    public static final String EXTRA_WNO = "wno";
    public static final String EXTRA_SLED = "sled";

    private RecyclerView mRecyclerView;
    private otoAdapter motoAdapter;
    private ArrayList<otoItem> motoList;
    private RequestQueue mRequestQueue;
    String Id, Wnumber, Tono, Itemno, Material, Matdes, Location, Batch, Qty, Uom, Perplt, peruom, Bincap, Bin, Availstock, Availuom, OBqty, OBuom, Exstk, Sldate, Status, Convqt, Excess;
    String cid, rid, dorderno, createdate, refer, changedate, val;
    Button confirm;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oto_material);
        mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        motoList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
//        checkJSON();
        Intent intent = getIntent();
        cid = intent.getStringExtra(EXTRA_CID);
        rid = intent.getStringExtra(EXTRA_RID);
        dorderno = intent.getStringExtra(EXTRA_DORDERNO);
        createdate = intent.getStringExtra(EXTRA_CREATEDATE);
        refer = intent.getStringExtra(EXTRA_REFER);
        changedate = intent.getStringExtra(EXTRA_CHANGEDATE);
        val = intent.getStringExtra(EXTRA_VAL);

        Id = intent.getStringExtra(EXTRA_ID);
        Wnumber = intent.getStringExtra(EXTRA_WNUMBER);
        Tono = intent.getStringExtra(EXTRA_TONO);
        Itemno = intent.getStringExtra(EXTRA_ITEMNO);
        Material = intent.getStringExtra(EXTRA_MATERIAL);
        Matdes = intent.getStringExtra(EXTRA_MATDES);
        Location = intent.getStringExtra(EXTRA_STLOC);
        Batch = intent.getStringExtra(EXTRA_BATCH);
        Qty = intent.getStringExtra(EXTRA_GRQTY);
        Uom = intent.getStringExtra(EXTRA_GRUOM);
        Perplt = intent.getStringExtra(EXTRA_PERPLT);
        peruom = intent.getStringExtra(EXTRA_PERUOM);
        Bincap = intent.getStringExtra(EXTRA_BINCAP);
        Bin = intent.getStringExtra(EXTRA_STKBIN);
        Availstock = intent.getStringExtra(EXTRA_AVAILSTOCK);
        Availuom = intent.getStringExtra(EXTRA_AVAILUOM);
        OBqty = intent.getStringExtra(EXTRA_OBQTY);
        OBuom = intent.getStringExtra(EXTRA_OBUOM);
        Exstk = intent.getStringExtra(EXTRA_EXTSTK);
        Sldate = intent.getStringExtra(EXTRA_SLD);
        Status = intent.getStringExtra(EXTRA_STATUS);
        Convqt = intent.getStringExtra(EXTRA_CONVQT);
        Excess = intent.getStringExtra(EXTRA_EXCESS);


        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetMethodPut().execute("https://simpson.thinkawm.com/api/TOOBheader/update.php");
                new GetMethod1Put().execute("https://simpson.thinkawm.com/api/TO/update.php");
                Intent intent = new Intent(otoMaterial.this, queueoto.class);
                startActivity(intent);

            }
        });
    }

    private void parseJSON() {
        String url = "https://simpson.thinkawm.com/api/TOOB/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Intent intent = getIntent();
                    String tonum = intent.getStringExtra(EXTRA_RID);

                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String id = hit.getString("id");
                        String rid = hit.getString("rid");
                        String itemno = hit.getString("itemno");
                        String material = hit.getString("material");
                        String description = hit.getString("description");
                        String quantity = hit.getString("quantity");
                        String uom = hit.getString("uom");
                        String wno = hit.getString("wno");
                        String batch = hit.getString("batch");
                        String bin = hit.getString("bin");
                        String sled = hit.getString("sled");
                        String status = hit.getString("status");

                        if ((tonum.equals(rid)) && (!status.equals("confirmed"))) {
                            motoList.add(new otoItem(id, rid, status, itemno, material, description,
                                    quantity, uom, wno, batch, bin, sled));
                        }

                    }
                    motoAdapter = new otoAdapter(otoMaterial.this, motoList);
                    mRecyclerView.setAdapter(motoAdapter);
                    motoAdapter.setOnItemClickListener(otoMaterial.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, obMatDetail.class);
        otoItem clickedItem = motoList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickedItem.getid());
        detailIntent.putExtra(EXTRA_RID, clickedItem.getrid());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getstatus());
        detailIntent.putExtra(EXTRA_ITEMNO, clickedItem.getitemno());
        detailIntent.putExtra(EXTRA_MATERIAL, clickedItem.getmaterial());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getdescription());
        detailIntent.putExtra(EXTRA_QUANTITY, clickedItem.getquantity());
        detailIntent.putExtra(EXTRA_UOM, clickedItem.getuom());
        detailIntent.putExtra(EXTRA_WNO, clickedItem.getwno());
        detailIntent.putExtra(EXTRA_BATCH, clickedItem.getbatch());
        detailIntent.putExtra(EXTRA_BIN, clickedItem.getbin());
        detailIntent.putExtra(EXTRA_SLED, clickedItem.getsled());
        detailIntent.putExtra("CID", cid);
        detailIntent.putExtra("DORDERNO", dorderno);
        detailIntent.putExtra("CREATEDATE", createdate);
        detailIntent.putExtra("CHANGEDATE", changedate);
        detailIntent.putExtra("REFER", refer);

        startActivity(detailIntent);
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

                jsonObjectFinal.put("cid", cid);
                jsonObjectFinal.put("rid", rid);
                jsonObjectFinal.put("dorderno", dorderno);
                jsonObjectFinal.put("createdate", createdate);
                jsonObjectFinal.put("refer", refer);
                jsonObjectFinal.put("changedate", changedate);
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

    public class GetMethod1Put extends AsyncTask<String, Void, String> {
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
                jsonObjectFinal.put("perplt", Perplt);
                jsonObjectFinal.put("peruom", peruom);
                jsonObjectFinal.put("bincap", Bincap);
                jsonObjectFinal.put("stkbin", Bin);
                jsonObjectFinal.put("availstock", Availstock);
                jsonObjectFinal.put("availuom", Availuom);
                jsonObjectFinal.put("obqty", "");
                jsonObjectFinal.put("obuom", "");
                jsonObjectFinal.put("extstk", Exstk);
                jsonObjectFinal.put("sld", Sldate);
                jsonObjectFinal.put("status", Status);
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

    public void onBackPressed() {
        Intent inten = new Intent(this, queueoto.class);
        startActivity(inten);
    }
}