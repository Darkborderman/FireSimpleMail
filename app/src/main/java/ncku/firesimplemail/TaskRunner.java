package ncku.firesimplemail;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import FSMServer.*;

public class TaskRunner extends Service {

    private Client client;
    private String account;
    private String password;
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        client = new Client("140.116.245.100",6000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
        } else {

        }

        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable auth = new Runnable() {
        @Override
        public void run() {
            client.authenticate(account, password);
        }
    };
}
