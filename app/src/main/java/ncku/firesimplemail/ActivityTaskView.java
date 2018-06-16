package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import FSMServer.*;



public class ActivityTaskView extends AppCompatActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_view);
        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra(ActivityTaskList.INTENT_TASK_TITLE);
        setTitle(taskTitle);
    }
}
