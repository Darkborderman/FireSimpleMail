package ncku.firesimplemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import static ncku.firesimplemail.ActivityLogin.client;


public class ActivityFacilityList extends AppCompatActivity{

    Button getMailButton,writeMailButton;
    Button getTaskButton,writeTaskButton;
    Button logoutButton;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_facility_list);

        //get mail button
        getMailButton=findViewById(R.id.getMailButton);
        getMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ActivityFacilityList.this, ActivityMailList.class);
                ActivityFacilityList.this.startActivity(myIntent);
            }
        });

        //write mail button
        writeMailButton=findViewById(R.id.writeMailButton);
        writeMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent myIntent = new Intent(ActivityFacilityList.this, ActivityMailWrite.class);
                 ActivityFacilityList.this.startActivity(myIntent);
            }
        });

        //get task button
        getTaskButton=findViewById(R.id.getTaskButton);
        getTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent myIntent = new Intent(ActivityFacilityList.this, ActivityTaskList.class);
                 ActivityFacilityList.this.startActivity(myIntent);
            }
        });

        //write task button
        writeTaskButton=findViewById(R.id.writeTaskButton);
        writeTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent myIntent = new Intent(ActivityFacilityList.this, ActivityTaskWrite.class);
                 myIntent.putExtra("Operation","create");
                 ActivityFacilityList.this.startActivity(myIntent);
            }
        });

        //logout button
        logoutButton=findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(connect);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(result){
                    Intent myIntent = new Intent(ActivityFacilityList.this, ActivityLogin.class);
                    ActivityFacilityList.this.startActivity(myIntent);
                }
                else{
                    Toast toast = Toast.makeText(ActivityFacilityList.this,"Logout Failed", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
    private Runnable connect = new Runnable() {
        public void run() {
            result=client.logout();
        }
    };
}
