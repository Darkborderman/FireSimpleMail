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

import java.util.ArrayList;


public class ActivityTaskList extends AppCompatActivity {
    
    //public ArrayList<String> tasks = new ArrayList<>();


    private ArrayList<Task> tasks = new ArrayList<Task>();

    //public static final String INTENT_TASK_TITLE = "ncku.firesimplemail.TASK_TITLE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_list);

        Task task = new Task( "from", "to", "title");

        tasks.add(task);

        ArrayAdapter<Task> adapter = new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1, tasks);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(newTaskClickedHandler);
    }

    private AdapterView.OnItemClickListener newTaskClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent myIntent = new Intent(ActivityTaskList.this, ActivityTaskWrite.class);
            //myIntent.putExtra(INTENT_TASK_TITLE, parent.getItemAtPosition(position).toString());
            myIntent.putExtra("Operation","update");

            // Note that tasks will not be flushed after navigation
            ActivityTaskList.this.startActivity(myIntent);
        }
    };

}
