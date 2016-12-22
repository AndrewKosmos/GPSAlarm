package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lifelessplanetinc.kpo_kurs_gpsalarm.Adapter.AlarmListAdapter;
import lifelessplanetinc.kpo_kurs_gpsalarm.Classes.Alarm;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public List<Alarm> alarms_list;
    private RecyclerView rv;
    private AlarmListAdapter adapter;
    private GoogleApiClient mGoogleApiClient;
    private Location lastLocation;
    private LatLng pos;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1;
    public boolean Activate = false;

    public ScheduledExecutorService sheScheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniData();
        rv = (RecyclerView) findViewById(R.id.alarms_list_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new AlarmListAdapter(alarms_list);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        createLocationRequest();
        adapter.setOnItemClickListener(new AlarmListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String mess = alarms_list.get(position).getTitle();
                Toast.makeText(itemView.getContext(), mess + "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
                String mess = alarms_list.get(position).getTitle();
                Toast.makeText(itemView.getContext(), mess + "long clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemIconClick(View itemView, int position) {
                String mess = alarms_list.get(position).getTitle();
                Toast.makeText(itemView.getContext(), mess + "icon", Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);

        FloatingActionButton add_alarm_fab = (FloatingActionButton) findViewById(R.id.add_new_alarm_fab);
        add_alarm_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddNewAlarm.class);
                startActivityForResult(intent, 1);
            }
        });

        sheScheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture = sheScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                checkLocation();
                Log.d("VARUABLE POS:",pos.toString());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this, "Work " + pos.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                checkAlarms();
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    private void iniData() {
        alarms_list = new ArrayList<>();
    }

    private void checkLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        pos = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        LatLng c;
        String d;
        String Name;

        c = (LatLng) data.getExtras().get("coords");
        d = data.getStringExtra("destination");
        Name = data.getStringExtra("Name");

        if (Name != "") {
            Alarm a = new Alarm(Name, d, c, true);
            alarms_list.add(a);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        sheScheduledExecutorService.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocation();
        checkLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        pos = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
    }

    public void checkLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void checkAlarms(){
        if(!alarms_list.isEmpty())
        {
            for(int i = 0; i < alarms_list.size(); i++)
            {
                float[] results = new float[1];
                Location.distanceBetween(pos.latitude,pos.longitude,alarms_list.get(i).getDest_coords().latitude,alarms_list.get(i).getDest_coords().longitude,results);
                float distanceInMeters = results[0];
                if(distanceInMeters <= 100)
                {
                    Activate = true;
                    break;
                }
            }
        }
    }
}
