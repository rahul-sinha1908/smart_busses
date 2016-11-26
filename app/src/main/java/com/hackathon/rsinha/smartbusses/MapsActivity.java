package com.hackathon.rsinha.smartbusses;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    /*public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnPoiClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        //mMap.addMarker(new MarkerOptions().position().title("Marker in Sydney"));
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {

        Log.i("Check", "Working");
        Toast.makeText(getApplicationContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();

                        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
             
                        StringBuffer sb =new StringBuffer();
             
                        String line = "";
                        while ((line = br.readLine()) !=null){
                                sb.append(line);
                            }
             
                        data = sb.toString();
             
                        br.close();
             
                    } catch (Exception e) {
                        Log.d("Exception while downloading url", e.toString());
                    } finally {
                        iStream.close();
                        urlConnection.disconnect();
                    }
         
                return data;
            }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.hackathon.rsinha.smartbusses/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.hackathon.rsinha.smartbusses/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

     
                /** A class, to download Google Places */
                

    private class PlacesTask extends AsyncTask<String, Integer, String> {
         
                        
        String data = null;
         
                        // Invoked by execute() method of this object
                        
        @Override
                

        protected String doInBackground(String... url) {
                        try {
                                data = downloadUrl(url[0]);
                            } catch (Exception e) {
                                Log.d("Background Task", e.toString());
                            }
                        return data;
                    }

         
                        // Executed after the complete execution of doInBackground() method
                        
        @Override
                

        protected void onPostExecute(String result) {
                        ParserTask parserTask = new ParserTask();
             
                        // Start parsing the Google places in JSON format
                        // Invokes the "doInBackground()" method of the class ParseTask
                        parserTask.execute(result);
                    }

         
                    
    }

     
                /** A class to parse the Google Places in JSON format */
                

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
         
                        
        JSONObject jObject;
         
                        // Invoked by execute() method of this object
                        
        @Override
                

        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
             
                        List<HashMap<String, String>> places = null;
                        PlaceJSONParser placeJsonParser = new PlaceJSONParser();
             
                        try {
                                jObject = new JSONObject(jsonData[0]);
                 
                                /** Getting the parsed data as a List construct */
                                places = placeJsonParser.parse(jObject);
                 
                            } catch (Exception e) {
                                Log.d("Exception", e.toString());
                            }
                        return places;
                    }

         
                        // Executed after the complete execution of doInBackground() method
                        
        @Override
                

        protected void onPostExecute(List<HashMap<String, String>> list) {
             
                        // Clears all the existing markers
                        mGoogleMap.clear();
             
                        for (int i = 0; i < list.size(); i++) {
                 
                                // Creating a marker
                                MarkerOptions markerOptions = new MarkerOptions();
                 
                                // Getting a place from the places list
                                HashMap<String, String> hmPlace = list.get(i);
                 
                                // Getting latitude of the place
                                double lat = Double.parseDouble(hmPlace.get("lat"));
                 
                                // Getting longitude of the place
                                double lng = Double.parseDouble(hmPlace.get("lng"));
                 
                                // Getting name
                                String name = hmPlace.get("place_name");
                 
                                // Getting vicinity
                                String vicinity = hmPlace.get("vicinity");
                 
                                LatLng latLng = new LatLng(lat, lng);
                 
                                // Setting the position for the marker
                                markerOptions.position(latLng);
                 
                                // Setting the title for the marker.
                                //This will be displayed on taping the marker
                                markerOptions.title(name + " : " + vicinity);
                 
                                // Placing a marker on the touched position
                                mGoogleMap.addMarker(markerOptions);
                            }
                    }

            
    }

     
