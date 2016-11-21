package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddNewAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_alarm);
        TextView adress_tv = (TextView) findViewById(R.id.show_adress_tv);
        adress_tv.setSelected(true);
    }
}
