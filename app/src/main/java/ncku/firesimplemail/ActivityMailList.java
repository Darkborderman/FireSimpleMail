package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import FSMServer.*;


public class ActivityMailList extends AppCompatActivity{

    private ArrayList<MailHead> mails = new ArrayList<MailHead>();
    Client client=new Client("localhost",1111);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_list);

        MailHead[] mh=client.getAllMail();
        for(int i=0;i<=mh.length-1;i++)
        {
            mails.add(mh[i]);
        }

        ArrayAdapter<MailHead> adapter = new ArrayAdapter<> (this,
            android.R.layout.simple_list_item_1, mails);
        ListView listView = findViewById(R.id.listView2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(newMailClickedHandler);

    }

    private AdapterView.OnItemClickListener newMailClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent myIntent = new Intent(ActivityMailList.this, ActivityMailView.class);
            MailHead selected = (MailHead)parent.getItemAtPosition(position);
            myIntent.putExtra("selectedId", selected.getId());
            ActivityMailList.this.startActivity(myIntent);
        }
    };
}
