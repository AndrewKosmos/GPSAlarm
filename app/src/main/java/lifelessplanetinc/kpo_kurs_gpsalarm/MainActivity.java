package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lifelessplanetinc.kpo_kurs_gpsalarm.Adapter.AlarmListAdapter;
import lifelessplanetinc.kpo_kurs_gpsalarm.Classes.Alarm;

public class MainActivity extends AppCompatActivity {
    public List<Alarm> alarms_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
        setContentView(R.layout.activity_main);
        RecyclerView rv = (RecyclerView) findViewById(R.id.alarms_list_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        AlarmListAdapter adapter = new AlarmListAdapter(alarms_list);
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
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    private void initializeData() {
        alarms_list = new ArrayList<>();
        alarms_list.add(new Alarm("Alarm 1","Ul'yanovsk ul. Severny Venec,32",true));
        alarms_list.add(new Alarm("Alarm 2","Ul'yanovsk ul. Severny Venec,32",true));
        alarms_list.add(new Alarm("Alarm 3","Ul'yanovsk ul. Severny Venec,32",false));
        alarms_list.add(new Alarm("Alarm 4","Ul'yanovsk ul. Severny Venec,32",false));
        alarms_list.add(new Alarm("Alarm 5","Ul'yanovsk ul. Severny Venec,32",false));
        alarms_list.add(new Alarm("Alarm 6","Ul'yanovsk ul. Severny Venec,32",false));
        alarms_list.add(new Alarm("Alarm 7","Ul'yanovsk ul. Severny Venec,32",false));
        alarms_list.add(new Alarm("Alarm 8","Ul'yanovsk ul. Severny Venec,32",false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
