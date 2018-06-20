package ncku.firesimplemail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static ncku.firesimplemail.ActivityLogin.client;

public class Scheduler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //client;
        Toast.makeText(context, "Yeah!", Toast.LENGTH_SHORT).show();
    }
}
