package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import lifelessplanetinc.kpo_kurs_gpsalarm.Adapter.AlarmListAdapter;
import lifelessplanetinc.kpo_kurs_gpsalarm.Classes.Alarm;

public class MainActivity extends AppCompatActivity {
    public List<Alarm> alarms_list;
    private RecyclerView rv;
    private AlarmListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniData();
        rv = (RecyclerView) findViewById(R.id.alarms_list_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new AlarmListAdapter(alarms_list);
        adapter.setOnItemClickListener(new AlarmListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String mess = alarms_list.get(position).getTitle();
                Toast.makeText(itemView.getContext(),mess + "clicked",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
                String mess = alarms_list.get(position).getTitle();
                Toast.makeText(itemView.getContext(),mess + "long clicked",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemIconClick(View itemView, int position) {
                String mess = alarms_list.get(position).getTitle();
                Toast.makeText(itemView.getContext(),mess + "icon",Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);

        FloatingActionButton add_alarm_fab = (FloatingActionButton) findViewById(R.id.add_new_alarm_fab);
        add_alarm_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AddNewAlarm.class);
                startActivityForResult(intent,1);
            }
        });


    }

    private void iniData() {
        alarms_list = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null) {return;}
        LatLng c;
        String d;
        String Name;

        c = (LatLng) data.getExtras().get("coords");
        d = data.getStringExtra("destination");
        Name = data.getStringExtra("Name");

        if(Name != "")
        {
            Alarm a = new Alarm(Name,d,c,true);
            alarms_list.add(a);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
