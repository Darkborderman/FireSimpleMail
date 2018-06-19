package ncku.firesimplemail;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import FSMServer.*;

import java.util.ArrayList;
import java.util.Date;


public class ActivityTaskList extends AppCompatActivity {

    Client client=new Client("140.116.245.100",6000);

    private ArrayList<TaskHead> tasks = new ArrayList<TaskHead>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_list);

        Text[] text=new Text[1];
        Date date=new Date();

        Task task = new Task( "from", "to", "title",text,date,1000);

        TaskHead[] th=client.getAllTask();

        if(th!=null)
        {
            for(int i=0;i<=th.length-1;i++)
            {
                tasks.add(th[i]);
            }

            ArrayAdapter<TaskHead> adapter = new ArrayAdapter<> (this,
                    android.R.layout.simple_list_item_1, tasks);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(newTaskClickedHandler);
        }
    }

    private AdapterView.OnItemClickListener newTaskClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent myIntent = new Intent(ActivityTaskList.this, ActivityTaskWrite.class);
            TaskHead selected = (TaskHead) parent.getItemAtPosition(position);
            myIntent.putExtra("selectedId",selected.getId());
            myIntent.putExtra("Operation","update");
            ActivityTaskList.this.startActivity(myIntent);
        }
    };
}
