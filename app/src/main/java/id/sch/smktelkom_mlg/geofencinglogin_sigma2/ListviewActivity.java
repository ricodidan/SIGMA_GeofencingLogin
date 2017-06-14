package id.sch.smktelkom_mlg.geofencinglogin_sigma2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListviewActivity extends AppCompatActivity {

    public static String DATA_ID = "facilityID";
    public static String DATA_NAMA = "facilityName";
    public static String DATA_ADDRESS = "address";
    public static String DATA_LONG = "c_lng";
    public static String DATA_LAT = "c_lat";
    // URL to get contacts JSON
    public static String url = "http://180.250.19.90/web/scmp/city/facility/get/LLG?key=1234";
    ArrayList<HashMap<String, String>> dataList;
    private String TAG = ListviewActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        dataList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetData().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    public class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListviewActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray data = jsonObj.getJSONArray("Data");

                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);

                        String id = c.getString(DATA_ID);
                        String name = c.getString(DATA_NAMA);
                        String address = c.getString(DATA_ADDRESS);
                        String lang = c.getString(DATA_LONG);
                        String lat = c.getString(DATA_LAT);

                        /*// Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                        // tmp hash map for single dataview
                        HashMap<String, String> dataview = new HashMap<>();

                        // adding each child node to HashMap key => value
                        dataview.put(DATA_ID, id);
                        dataview.put(DATA_NAMA, name);
                        dataview.put(DATA_ADDRESS, address);
                        dataview.put(DATA_LONG, lang);
                        dataview.put(DATA_LAT, lat);

                        // adding dataview to dataview list
                        dataList.add(dataview);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ListviewActivity.this, dataList,
                    R.layout.list_item, new String[]{DATA_NAMA, DATA_ADDRESS,
                    DATA_LONG, DATA_LAT}, new int[]{R.id.name,
                    R.id.address, R.id.lang, R.id.lat});

            lv.setAdapter(adapter);
        }

    }
}
