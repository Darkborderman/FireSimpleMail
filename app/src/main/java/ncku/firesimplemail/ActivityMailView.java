package ncku.firesimplemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import FSMServer.*;

import static ncku.firesimplemail.ActivityLogin.account;
import static ncku.firesimplemail.ActivityLogin.client;


public class ActivityMailView extends AppCompatActivity{

    Mail mail;
    TextView titleLabel,senderLabel,receiverLabel,bodyLabel;
    String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_view);

        x=getIntent().getExtras().getString("ID");
        Thread thread = new Thread(connect);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String title=mail.getTitle();
        titleLabel=findViewById(R.id.titleLabel);
        titleLabel.setText(title);

        String sender=mail.getSender();
        senderLabel=findViewById(R.id.senderLabel);
        senderLabel.setText(sender);

        String receiver=mail.getReceiver();
        receiverLabel=findViewById(R.id.receiverLabel);
        receiverLabel.setText(receiver);

        bodyLabel=findViewById(R.id.bodyLabel);
        bodyLabel.setText(mail.getBody());

    }

    private Runnable connect = new Runnable() {
        public void run() {
            mail=client.getMail(x);
        }
    };
}
