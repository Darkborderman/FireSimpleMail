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


public class ActivityTaskList extends AppCompatActivity implements DialogNewTask.Callback{

    Button taskButton;
    public ArrayList<String> tasks = new ArrayList<>();

    public static final String INTENT_TASK_TITLE = "ncku.firesimplemail.TASK_TITLE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_list);

        debugLog("ActivityTaskList - onCreated");

        tasks.add("Sample 1");
        tasks.add("Sample 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<> (this,
                android.R.layout.simple_list_item_1, tasks);
        // ListView is a legacy, but newer RecyclerView does not support ArrayAdapter
        // Another solution: https://github.com/passsy/ArrayAdapter
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(newTaskClickedHandler);
    }

    private AdapterView.OnItemClickListener newTaskClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            debugLog(parent.getItemAtPosition(position).toString());
            Intent myIntent = new Intent(ActivityTaskList.this, ActivityTaskView.class);
            myIntent.putExtra(INTENT_TASK_TITLE, parent.getItemAtPosition(position).toString());
            // Note that tasks will not be flushed after navigation
            // TODO: local storage
            ActivityTaskList.this.startActivity(myIntent);
        }
    };

    public void debugLog(String str) {
        Toast.makeText(ActivityTaskList.this, "Debug: " + str,
                Toast.LENGTH_SHORT).show();
    }

    public void namingNewTask(View view) {
        DialogNewTask dialog = new DialogNewTask();
        dialog.show(getFragmentManager(), "TBD");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String str) {
        tasks.add(str);
        debugLog("Dialog OK");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        debugLog("Dialog Cancel");
    }
}
