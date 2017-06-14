/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.sch.smktelkom_mlg.geofencinglogin_sigma2;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.internal.zzid.runOnUiThread;
import static id.sch.smktelkom_mlg.geofencinglogin_sigma2.ListviewActivity.DATA_ADDRESS;
import static id.sch.smktelkom_mlg.geofencinglogin_sigma2.ListviewActivity.DATA_ID;
import static id.sch.smktelkom_mlg.geofencinglogin_sigma2.ListviewActivity.DATA_LAT;
import static id.sch.smktelkom_mlg.geofencinglogin_sigma2.ListviewActivity.DATA_LONG;
import static id.sch.smktelkom_mlg.geofencinglogin_sigma2.ListviewActivity.DATA_NAMA;
import static id.sch.smktelkom_mlg.geofencinglogin_sigma2.ListviewActivity.url;

/**
 * Constants used in this sample.
 */
public final class Constants {

    public static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";
    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";
    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 10000; // 1 mile, 1.6 km / 100m
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        new GetData().execute();

        // BSD KCU Serpong.
        BAY_AREA_LANDMARKS.put("BSD KCU Serpong", new LatLng(-6.295556, 106.665639));


        // Bank BTPN1.
        BAY_AREA_LANDMARKS.put("Bank BTPN1", new LatLng(-3.30621427086171, 102.848132997642));

        // Bank BTPN12.
        //BAY_AREA_LANDMARKS.put("Bank BTPN12", new LatLng(-3.30621427086171, 102.848132997642));

        // Bank BTPN1.
        BAY_AREA_LANDMARKS.put("Bank BTPN1", new LatLng(-3.30621427086171, 102.848132997642));

        // Bank rs.
        BAY_AREA_LANDMARKS.put("rs", new LatLng(-3.29661004692395, 102.863874435425));
    }

    private Constants() {
    }

    /**
     * Map for  storing information about airports in the San Francisco bay area.
     */

    private static class GetData extends AsyncTask<Void, Void, Void> {

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
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           /* Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();*/
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       /* Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();*/
                    }
                });

            }
            return null;
        }
    }


}
