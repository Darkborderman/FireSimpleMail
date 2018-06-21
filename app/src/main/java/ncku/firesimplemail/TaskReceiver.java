package ncku.firesimplemail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class TaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //client;
        Bundle b = intent.getExtras();
        Intent mIntent  = new Intent(context, TaskRunner.class);
        mIntent.putExtras(b);
        context.startService(mIntent);
    }


}
