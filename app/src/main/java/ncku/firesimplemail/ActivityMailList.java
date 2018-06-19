package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityMailList extends AppCompatActivity{

    private ArrayList<MailHead> mails = new ArrayList<>();
    MailHead[] mh;
    Button writeMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_list);

        Thread thread = new Thread(connect);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(mh!=null)
        {
            for(int i=0;i<=mh.length-1;i++) mails.add(mh[i]);

            ArrayAdapter<MailHead> adapter = new ArrayAdapter<> (this,
                    android.R.layout.simple_list_item_1, mails);
            ListView listView = findViewById(R.id.listView2);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(newMailClickedHandler);
        }
        //write mail button
        writeMailButton=findViewById(R.id.writeMailButton);
        writeMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ActivityMailList.this, ActivityMailWrite.class);
                ActivityMailList.this.startActivity(myIntent);
            }
        });
    }

    private AdapterView.OnItemClickListener newMailClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent myIntent = new Intent(ActivityMailList.this, ActivityMailView.class);
            MailHead selected = (MailHead)parent.getItemAtPosition(position);
            myIntent.putExtra("ID", selected.getId());
            ActivityMailList.this.startActivity(myIntent);
        }
    };

    private Runnable connect = new Runnable() {
        public void run() {
            mh=client.getAllMail();
        }
    };
}


