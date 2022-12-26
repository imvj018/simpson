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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class pendingoto extends AppCompatActivity implements obPAdapter.OnItemClickListener {

    public static final String EXTRA_CID = "cid";
    public static final String EXTRA_RID = "rid";
    public static final String EXTRA_DORDERNO = "dorderno";
    public static final String EXTRA_CREATEDATE = "createdate";
    public static final String EXTRA_REFER = "refer";
    public static final String EXTRA_CHANGEDATE = "changedate";
    public static final String EXTRA_VAL = "val";
    private RecyclerView mRecyclerView;
    private obPAdapter mobPAdapter;
    private ArrayList<obPItem> mobPList;
    private RequestQueue mRequestQueue;
    String dateee, timeee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingoto);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mobPList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss a");
        dateee = sdate.format(calendar.getTime());
        timeee = stime.format(calendar.getTime());

    }

    private void parseJSON() {
        String url = "https://simpson.thinkawm.com/api/TOOBheader/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String cid = hit.getString("cid");
                        String rid = hit.getString("rid");
                        String dorderno = hit.getString("dorderno");
                        String createdate = hit.getString("createdate");
                        String refer = hit.getString("refer");
                        String changedate = hit.getString("changedate");
                        String val = hit.getString("val");

                        if ((!val.equals("confirmed")) && (!createdate.equals(dateee))) {
                            mobPList.add(new obPItem(cid, rid, dorderno, createdate, refer, changedate, val));
                        }


                    }
                    mobPAdapter = new obPAdapter(pendingoto.this, mobPList);
                    mRecyclerView.setAdapter(mobPAdapter);
                    mobPAdapter.setOnItemClickListener(pendingoto.this);
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
        Intent detailIntent = new Intent(this, otoMaterial.class);
        obPItem clickedItem = mobPList.get(position);
        detailIntent.putExtra(EXTRA_CID, clickedItem.getcid());
        detailIntent.putExtra(EXTRA_RID, clickedItem.getrid());
        detailIntent.putExtra(EXTRA_DORDERNO, clickedItem.getdorderno());
        detailIntent.putExtra(EXTRA_CREATEDATE, clickedItem.getcreatedate());
        detailIntent.putExtra(EXTRA_REFER, clickedItem.getrefer());
        detailIntent.putExtra(EXTRA_CHANGEDATE, clickedItem.getchangedate());
        detailIntent.putExtra(EXTRA_VAL, clickedItem.getval());
        startActivity(detailIntent);
    }
    public void onBackPressed(){
        Intent inten =  new Intent(this, outbound.class);
        startActivity(inten);
    }}