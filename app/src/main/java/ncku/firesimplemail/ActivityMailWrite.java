package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import FSMServer.*;

import static ncku.firesimplemail.ActivityLogin.account;
import static ncku.firesimplemail.ActivityLogin.client;


public class ActivityMailWrite extends AppCompatActivity{

    Button sendButton;
    TextView titleTextBox,toTextBox,contextTextBox;
    String title,from,to,context;
    boolean result;
    Date date=new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_write);

        sendButton=findViewById(R.id.sendButton);
        titleTextBox=findViewById(R.id.taskTitleTextBox);
        toTextBox=findViewById(R.id.toTextBox);
        contextTextBox=findViewById(R.id.contextTextBox);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from=account+"@mail.FSM.com";
                to=toTextBox.getText().toString();
                title=titleTextBox.getText().toString();
                context=contextTextBox.getText().toString();

                Thread thread = new Thread(connect);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(result){
                    Toast toast = Toast.makeText(ActivityMailWrite.this,"Success", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent myIntent = new Intent(ActivityMailWrite.this, ActivityFacilityList.class);
                    ActivityMailWrite.this.startActivity(myIntent);
                }
                else{
                    Toast toast = Toast.makeText(ActivityMailWrite.this,"Fail", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private Runnable connect = new Runnable() {
        public void run() {
            Mail mail=new Mail(from,to,title,context,date);
            result=client.sendMail(mail);
        }
    };
}
