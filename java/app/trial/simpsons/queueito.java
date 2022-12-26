package app.trial.simpsons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class queueito extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

    public static final String EXTRA_TONO = "tono";
    public static final String EXTRA_STORE = "store";
    public static final String EXTRA_TODATE = "todate";
    public static final String EXTRA_REFDOC = "refdoc";
    public static final String EXTRA_REFER = "refer";
    public static final String EXTRA_VAL = "val";
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    String dateee, timeee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queueito);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss a");
        dateee = sdate.format(calendar.getTime());
        timeee = stime.format(calendar.getTime());

    }


    private void parseJSON() {
        String url = "https://simpson.thinkawm.com/api/TOheader/read.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String tono = hit.getString("tono");
                        String store = hit.getString("store");
                        String todate = hit.getString("todate");
                        String refdoc = hit.getString("refdoc");
                        String refer = hit.getString("refer");
                        String val = hit.getString("val");

                        if ((!tono.equals("0")) && (val.equals("create")) && (todate.equals(dateee))) {
                            mExampleList.add(new ExampleItem(tono, store, todate, refdoc, refer, val));
                        }

                    }
                    mExampleAdapter = new ExampleAdapter(queueito.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(queueito.this);
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
        Intent detailIntent = new Intent(this, itoMaterial.class);
        ExampleItem clickedItem = mExampleList.get(position);
        detailIntent.putExtra(EXTRA_TONO, clickedItem.gettono());
        detailIntent.putExtra(EXTRA_STORE, clickedItem.getstore());
        detailIntent.putExtra(EXTRA_TODATE, clickedItem.gettodate());
        detailIntent.putExtra(EXTRA_REFDOC, clickedItem.getrefdoc());
        detailIntent.putExtra(EXTRA_REFER, clickedItem.getrefer());
        detailIntent.putExtra(EXTRA_VAL, clickedItem.getval());
        startActivity(detailIntent);
    }
    public void onBackPressed() {
        Intent inten = new Intent(this, inbound.class);
        startActivity(inten);
    }
}