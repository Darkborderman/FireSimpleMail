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


public class ActivityMailWrite extends AppCompatActivity{

    Button sendButton;
    TextView titleTextBox,fromTextBox,toTextBox,contextTextBox;
    String title,from,to,context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_write);

        sendButton=findViewById(R.id.sendButton);
        titleTextBox=findViewById(R.id.taskTitleTextBox);
        fromTextBox=findViewById(R.id.fromTextBox);
        toTextBox=findViewById(R.id.toTextBox);
        contextTextBox=findViewById(R.id.contextTextBox);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from=fromTextBox.getText().toString();
                to=toTextBox.getText().toString();
                title=titleTextBox.getText().toString();
                context=contextTextBox.getText().toString();
                Date date=new Date(System.currentTimeMillis());

                Mail mail=new Mail(from,to,title,date);
                Client client=new Client("localhost",1111);

                boolean result=client.sendMail(mail);

                if(result==true){

                    Toast toast = Toast.makeText(ActivityMailWrite.this,"Success", Toast.LENGTH_LONG);
                    toast.show();
                    Intent myIntent = new Intent(ActivityMailWrite.this, ActivityFacilityList.class);
                    ActivityMailWrite.this.startActivity(myIntent);
                }
                else{
                    Toast toast = Toast.makeText(ActivityMailWrite.this,"Failed.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }
}
