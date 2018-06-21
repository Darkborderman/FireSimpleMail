package ncku.firesimplemail;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityFacilityList extends AppCompatActivity{

    FloatingActionButton writeMailButton,writeTaskButton;
    TabControl mainTabControl;
    ViewPager viewPager;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_facility_list);
        setContentView(R.layout.tab_control);

        // tab layout
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mainTabControl = new TabControl(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mainTabControl);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // get mail button
        writeMailButton = findViewById(R.id.writeMailButton);
        writeMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ActivityFacilityList.this, ActivityMailWrite.class);
                ActivityFacilityList.this.startActivity(myIntent);
            }
        });


        //get task button
        writeTaskButton=findViewById(R.id.writeTaskButton);
        writeTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ActivityFacilityList.this, ActivityTaskWrite.class);
                myIntent.putExtra("Operation","create");
                startActivity(myIntent);
            }
        });

    }
    private Runnable connect = new Runnable() {
        public void run() {
            result=client.logout();
        }
    };
}
