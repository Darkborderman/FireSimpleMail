package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FacilityListActivity extends AppCompatActivity{

    Button getTaskButton,writeTaskButton;
    Button getMailButton,writeMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_list);

        //get mail button
        getMailButton=findViewById(R.id.getMailButton);
        getMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent myIntent = new Intent(FacilityListActivity.this, TaskListActivity.class);
               // FacilityListActivity.this.startActivity(myIntent);
            }
        });
    }
}
