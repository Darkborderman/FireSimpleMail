package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import FSMServer.*;
import java.util.ArrayList;

import static ncku.firesimplemail.ActivityLogin.client;


public class ActivityTaskList extends AppCompatActivity {

    Button writeTaskButton;
    TaskHead[] th;

    private ArrayList<TaskHead> tasks = new ArrayList<>();
    private ArrayList<String> taskTitles = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_list);

        Thread thread = new Thread(connect);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(th!=null)
        {
            for(int i=0;i<=th.length-1;i++)
            {
                tasks.add(th[i]);
                taskTitles.add(th[i].getTitle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1, taskTitles);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(newTaskClickedHandler);
        }
        //write task button
        writeTaskButton=findViewById(R.id.writeTaskButton);
        writeTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ActivityTaskList.this, ActivityTaskWrite.class);
                myIntent.putExtra("Operation","create");
                ActivityTaskList.this.startActivity(myIntent);
            }
        });

    }

    private AdapterView.OnItemClickListener newTaskClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent myIntent = new Intent(ActivityTaskList.this, ActivityTaskWrite.class);
            TaskHead selected = tasks.get(position);
            myIntent.putExtra("ID",selected.getId());
            myIntent.putExtra("Operation","update");
            ActivityTaskList.this.startActivity(myIntent);
        }
    };

    private Runnable connect = new Runnable() {
        public void run() {
            th=client.getAllTask();
        }
    };
}
