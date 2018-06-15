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

public class ActivityMailList extends AppCompatActivity{

    private ArrayList<Mail> mails = new ArrayList<Mail>();
    Client client=new Client("localhost",1111);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_list);

        mails.add(client.getMail(new Id(10)));

        ArrayAdapter<Mail> adapter = new ArrayAdapter<> (this,
            android.R.layout.simple_list_item_1, mails);
        ListView listView = findViewById(R.id.listView2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(newMailClickedHandler);

    }

    private AdapterView.OnItemClickListener newMailClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent myIntent = new Intent(ActivityMailList.this, ActivityMailView.class);
            Mail mail = (Mail)parent.getItemAtPosition(position);
            myIntent.putExtra("Class", mail);
            ActivityMailList.this.startActivity(myIntent);
        }
    };
}
