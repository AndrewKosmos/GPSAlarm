package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class AddNewAlarm extends AppCompatActivity {

    private TextView adress_tv;
    private EditText editText;

    public LatLng coords;
    public String destination;
    public String Name;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){return;}
        coords = (LatLng) data.getExtras().get("coords");
        destination = data.getStringExtra("destination");
        Toast.makeText(getApplicationContext(),coords.toString(), Toast.LENGTH_SHORT).show();
        adress_tv.setText(destination);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_alarm);
        adress_tv = (TextView) findViewById(R.id.show_adress_tv);
        adress_tv.setSelected(true);
        editText = (EditText)findViewById(R.id.alarm_name_edt);
        ImageButton selectAddress_imb = (ImageButton) findViewById(R.id.choose_address_imb);
        selectAddress_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MapActivity.class);
                startActivityForResult(intent,1);
            }
        });
        Button add_alarm_btn = (Button) findViewById(R.id.add_alarm_btn);
        add_alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = editText.getText().toString();
                if(Name != "")
                {
                    Intent intent = new Intent();
                    intent.putExtra("coords",coords);
                    intent.putExtra("destination",destination);
                    intent.putExtra("Name",Name);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Name shouldn't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
