package lifelessplanetinc.kpo_kurs_gpsalarm;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AlarmScreen extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);
        TextView name = (TextView) findViewById(R.id.AlarmScreenNameET);
        TextView dest = (TextView) findViewById(R.id.AlarmScreenDestET);
        ImageButton stop = (ImageButton)findViewById(R.id.AlarmOFFImb);

        mediaPlayer = MediaPlayer.create(this,R.raw.alarm);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });

        long[] pattern = {600,700};
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern,0);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("Name"));
        dest.setText(intent.getStringExtra("dest"));
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                vibrator.cancel();
                finish();
            }
        });
    }
}
