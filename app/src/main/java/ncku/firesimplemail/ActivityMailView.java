package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityMailView extends AppCompatActivity{

    Button deleteButton;
    Mail mail;
    TextView titleLabel,senderLabel,receiverLabel,bodyLabel;
    String ID;
    boolean result=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_view);

        ID=getIntent().getExtras().getString("ID");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mail=client.getMail(ID);
            }
        });
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
        deleteButton=findViewById(R.id.deleteButton);

        //get mail button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result=client.deleteMail(ID);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(result){
                    Intent myIntent = new Intent(ActivityMailView.this, ActivityMailList.class);
                    ActivityMailView.this.startActivity(myIntent);
                }
                else{

                }
            }
        });

    }
}
