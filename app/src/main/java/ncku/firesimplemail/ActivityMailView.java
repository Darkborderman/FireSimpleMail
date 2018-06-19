package ncku.firesimplemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityMailView extends AppCompatActivity{

    Mail mail;
    TextView titleLabel,senderLabel,receiverLabel,bodyLabel;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_view);

        ID=getIntent().getExtras().getString("ID");
        Thread thread = new Thread(connect);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        titleLabel=findViewById(R.id.titleLabel);
        titleLabel.setText(mail.getTitle());
        senderLabel=findViewById(R.id.senderLabel);
        senderLabel.setText(mail.getSender());
        receiverLabel=findViewById(R.id.receiverLabel);
        receiverLabel.setText(mail.getReceiver());
        bodyLabel=findViewById(R.id.bodyLabel);
        bodyLabel.setText(mail.getBody());

    }

    private Runnable connect = new Runnable() {
        public void run() {
            mail=client.getMail(ID);
        }
    };
}
