package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener{
    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    SearchView searchView;


    private LatLng coordinates;
    private String destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        FloatingActionButton add_coords_fab = (FloatingActionButton) findViewById(R.id.add_alarm_adress_fab);
        add_coords_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = ReverseGeocoding(coordinates);
                Intent intent = new Intent();
                intent.putExtra("coords",coordinates);
                intent.putExtra("destination",destination);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        searchView = (SearchView)findViewById(R.id.search_adr);
        searchView.setQueryHint("Enter address");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_address(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private  void search_address(String query)
    {
        double lat = 0.0,lng=0.0;
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        try{
            List<android.location.Address> Addresses = geocoder.getFromLocationName(query,1);
            if(Addresses.size() > 0)
            {
                lat = Addresses.get(0).getLatitude();
                lng = Addresses.get(0).getLongitude();
                LatLng search_coords = new LatLng(lat,lng);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(search_coords).draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(search_coords));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                coordinates = search_coords;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void initMap(){
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        coordinates = latLng;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        coordinates = marker.getPosition();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    private String ReverseGeocoding(LatLng coordinates)
    {
        String res = "";
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(coordinates.latitude,coordinates.longitude,1);
            res = addresses.get(0).getLocality() + "," + addresses.get(0).getFeatureName() + "," + addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  res;
    }
}
