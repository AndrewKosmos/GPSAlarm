package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AlarmScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);
        TextView name = (TextView) findViewById(R.id.AlarmScreenNameET);
        TextView dest = (TextView) findViewById(R.id.AlarmScreenDestET);
        ImageButton stop = (ImageButton)findViewById(R.id.AlarmOFFImb);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("Name"));
        dest.setText(intent.getStringExtra("dest"));
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
