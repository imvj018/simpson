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

import static app.trial.simpsons.queueito.EXTRA_STORE;
import static app.trial.simpsons.queueito.EXTRA_TODATE;
import static app.trial.simpsons.queueito.EXTRA_REFDOC;
import static app.trial.simpsons.queueito.EXTRA_REFER;
import static app.trial.simpsons.queueito.EXTRA_VAL;

public class itoPMaterial extends AppCompatActivity implements itoPAdapter.OnItemClickListener {


    public static final String EXTRA_ID = "id";
    public static final String EXTRA_WNUMBER = "wnumber";
    public static final String EXTRA_TONO = "tono";
    public static final String EXTRA_ITEMNO = "itemno";
    public static final String EXTRA_MATERIAL = "material";
    public static final String EXTRA_MATDES = "matdes";
    public static final String EXTRA_STLOC = "stloc";
    public static final String EXTRA_BATCH = "batch";
    public static final String EXTRA_GRQTY = "grqty";
    public static final String EXTRA_GRUOM = "gruom";
    public static final String EXTRA_PERPLT = "perplt";
    public static final String EXTRA_PERUOM = "peruom";
    public static final String EXTRA_BINCAP = "bincap";
    public static final String EXTRA_STKBIN = "stkbin";
    public static final String EXTRA_AVAILSTOCK = "availstock";
    public static final String EXTRA_AVAILUOM = "availuom";
    public static final String EXTRA_OBQTY = "obqty";
    public static final String EXTRA_OBUOM = "obuom";
    public static final String EXTRA_EXTSTK = "extstk";
    public static final String EXTRA_SLD = "sld";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_CONVQT = "convqt";
    public static final String EXTRA_EXCESS = "excess";
    private RecyclerView mRecyclerView;
    private itoPAdapter mitoPAdapter;
    private ArrayList<itoPItem> mitoPList;
    private RequestQueue mRequestQueue;

    String Tono, Store, Todate, Refdoc, Refer, Val;
    Button confirm;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_material);
        mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mitoPList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Intent intent = getIntent();
        Tono = intent.getStringExtra(EXTRA_TONO);
        Store = intent.getStringExtra(EXTRA_STORE);
        Todate = intent.getStringExtra(EXTRA_TODATE);
        Refdoc = intent.getStringExtra(EXTRA_REFDOC);
        Refer = intent.getStringExtra(EXTRA_REFER);
        Val = intent.getStringExtra(EXTRA_VAL);
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetMethodPut().execute("https://simpson.thinkawm.com/api/TOheader/update.php");
                Intent intent = new Intent(itoPMaterial.this, queueito.class);
                startActivity(intent);

            }
        });
    }

    private void parseJSON() {

//        Intent intent = getIntent();
//        String url = "https://simpson.thinkawm.com/api/TO/zzznew_single_read.php?tono=" + intent.getStringExtra(EXTRA_TONO);

        String url = "https://simpson.thinkawm.com/api/TO/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Intent intent = getIntent();
                    String tonum = intent.getStringExtra(EXTRA_TONO);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//                    connection.connect();
                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
//                        if (response.getJSONArray("body").length() != 0) {

                        String id = hit.getString("id");
                        String wnumber = hit.getString("wnumber");
                        String tono = hit.getString("tono");
                        String itemno = hit.getString("itemno");
                        String material = hit.getString("material");
                        String matdes = hit.getString("matdes");
                        String stloc = hit.getString("stloc");
                        String batch = hit.getString("batch");
                        String grqty = hit.getString("grqty");
                        String gruom = hit.getString("gruom");
                        String perplt = hit.getString("perplt");
                        String peruom = hit.getString("peruom");
                        String bincap = hit.getString("bincap");
                        String stkbin = hit.getString("stkbin");
                        String availstock = hit.getString("availstock");
                        String availuom = hit.getString("availuom");
                        String obqty = hit.getString("obqty");
                        String obuom = hit.getString("obuom");
                        String extstk = hit.getString("extstk");
                        String sld = hit.getString("sld");
                        String status = hit.getString("status");
                        String convqt = hit.getString("convqt");
                        String excess = hit.getString("excess");

                        if ((tono.equals(tonum)) && status.equals("")) {
                            mitoPList.add(new itoPItem(id, wnumber, tono, itemno, material, matdes, stloc,
                                    batch, grqty, gruom, perplt, peruom, bincap, stkbin, availstock, availuom,
                                    obqty, obuom, extstk, sld, status, convqt, excess
                            ));


                        }


                    }
                    mitoPAdapter = new itoPAdapter(itoPMaterial.this, mitoPList);
                    mRecyclerView.setAdapter(mitoPAdapter);
                    mitoPAdapter.setOnItemClickListener(itoPMaterial.this);
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
        Intent detailIntent = new Intent(this, MatCDetail.class);
        itoPItem clickedItem = mitoPList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickedItem.getid());
        detailIntent.putExtra(EXTRA_WNUMBER, clickedItem.getwnumber());
        detailIntent.putExtra(EXTRA_TONO, clickedItem.gettono());
        detailIntent.putExtra(EXTRA_ITEMNO, clickedItem.getitemno());
        detailIntent.putExtra(EXTRA_MATERIAL, clickedItem.getmaterial());
        detailIntent.putExtra(EXTRA_MATDES, clickedItem.getmatdes());
        detailIntent.putExtra(EXTRA_STLOC, clickedItem.getstloc());
        detailIntent.putExtra(EXTRA_BATCH, clickedItem.getbatch());
        detailIntent.putExtra(EXTRA_GRQTY, clickedItem.getgrqty());
        detailIntent.putExtra(EXTRA_GRUOM, clickedItem.getgruom());
        detailIntent.putExtra(EXTRA_PERPLT, clickedItem.getperplt());
        detailIntent.putExtra(EXTRA_PERUOM, clickedItem.getperuom());
        detailIntent.putExtra(EXTRA_BINCAP, clickedItem.getbincap());
        detailIntent.putExtra(EXTRA_STKBIN, clickedItem.getstkbin());
        detailIntent.putExtra(EXTRA_AVAILSTOCK, clickedItem.getavailstock());
        detailIntent.putExtra(EXTRA_AVAILUOM, clickedItem.getavailuom());
        detailIntent.putExtra(EXTRA_OBQTY, clickedItem.getobqty());
        detailIntent.putExtra(EXTRA_OBUOM, clickedItem.getobuom());
        detailIntent.putExtra(EXTRA_EXTSTK, clickedItem.getextstk());
        detailIntent.putExtra(EXTRA_SLD, clickedItem.getsld());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getstatus());
        detailIntent.putExtra(EXTRA_CONVQT, clickedItem.getconvqt());
        detailIntent.putExtra(EXTRA_EXCESS, clickedItem.getexcess());
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

                jsonObjectFinal.put("tono", Tono);
                jsonObjectFinal.put("store", Store);
                jsonObjectFinal.put("todate", Todate);
                jsonObjectFinal.put("refdoc", Refdoc);
                jsonObjectFinal.put("refer", Refer);
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
        Intent inten = new Intent(this, pendingito.class);
        startActivity(inten);
    }
}