package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TaskListActivity  extends AppCompatActivity {

    Button taskButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //taskButton
        taskButton=findViewById(R.id.taskButton);
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TaskListActivity.this, TaskContentActivity.class);
                TaskListActivity.this.startActivity(myIntent);
            }
        });

    }
}
