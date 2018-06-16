package ncku.firesimplemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import FSMServer.*;


public class ActivityFacilityList extends AppCompatActivity{

    Button getMailButton,writeMailButton;
    Button getTaskButton,writeTaskButton;
    Client client=new Client("140.116.245.100",6000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_facility_list);

        //get mail button
        getMailButton=findViewById(R.id.getMailButton);
        getMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailHead[] mailHeads=client.getAllMail();
                Intent myIntent = new Intent(ActivityFacilityList.this, ActivityMailList.class);
                myIntent.putExtra("Class",mailHeads);
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
    }
}
