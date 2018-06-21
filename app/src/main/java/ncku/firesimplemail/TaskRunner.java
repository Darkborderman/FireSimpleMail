package ncku.firesimplemail;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import FSMServer.*;

import static ncku.firesimplemail.ActivityLogin.client;

public class TaskRunner extends Service {

    private Client client = null;
    private String account;
    private String password;
    private Random rand = new Random();
    private String title, to, from, body;
    private int interval;
    private boolean result;
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        try {
            FileInputStream is = openFileInput("session");
            Scanner scanner = new Scanner(is);
            String session = scanner.next();
            is.close();
            client = new Client("140.116.245.100", 6000, session);
        } catch (Exception e) {
            client = new Client("140.116.245.100", 6000);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
            return Service.START_REDELIVER_INTENT;
        String type = intent.getStringExtra("type");
        if (type.equals("auth")) {
            account = intent.getStringExtra("account");
            password = intent.getStringExtra("password");

            Thread thread = new Thread(auth);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            password = "";
            try {
                FileOutputStream os = openFileOutput("session", Activity.MODE_PRIVATE);
                PrintStream printStream = new PrintStream(os);
                printStream.print(client.getSession());
                printStream.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type.equals("sendMail")) {
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
                Toast.makeText(TaskRunner.this.getApplicationContext(), "Send email successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TaskRunner.this.getApplicationContext(), "Failed to send email", Toast.LENGTH_SHORT).show();
            }
        }

        return Service.START_REDELIVER_INTENT;
    }

    private Runnable auth = new Runnable() {
        @Override
        public void run() {
            client.authenticate(account, password);
        }
    };

    private Runnable sendMail = new Runnable() {
        @Override
        public void run() {
            Mail mail = new Mail(from, to, title, body, new Date());
            result = client.sendMail(mail);
        }
    };
}
