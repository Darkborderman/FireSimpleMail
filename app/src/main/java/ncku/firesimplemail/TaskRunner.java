package ncku.firesimplemail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class TaskRunner extends BroadcastReceiver {

    private Random rand = new Random();
    private String title, to, from, body;
    private int interval;
    private boolean result;
    @Override
    public void onReceive(Context context, Intent intent) {
        //client;
        Bundle b = intent.getExtras();
        ArrayList<String[]> texts = (ArrayList<String[]>) b.get("texts");
        title = (String) b.get("title");
        to = (String) b.get("to");
        from = (String) b.get("from");
        interval = (int) b.get("interval");
        body = "";
        for (int i = 0; i < texts.size(); i++) {
            String[] strs = texts.get(i);
            int index = rand.nextInt(strs.length);
            body += strs[index];
        }

        Thread thread = new Thread(sendMail);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (result) {
            Toast.makeText(context, "Send email successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to send email", Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable sendMail = new Runnable() {
        @Override
        public void run() {
            Mail mail = new Mail(from, to, title, body, new Date());
            result = client.sendMail(mail);
        }
    };

}
