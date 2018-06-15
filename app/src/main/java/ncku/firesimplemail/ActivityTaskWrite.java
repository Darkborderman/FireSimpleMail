package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTaskWrite extends AppCompatActivity{

    TextView taskTitleTextBox;
    TextView titleTextBox,fromTextBox,toTextBox;
    String taskTitle,title,from,to;
    Button saveButton;
    Client client=new Client("localhost",1111);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_write);

        final String operation=getIntent().getStringExtra("Operation");

        taskTitleTextBox=findViewById(R.id.taskTitleTextBox);
        titleTextBox=findViewById(R.id.titleTextBox);
        fromTextBox=findViewById(R.id.fromTextBox);
        toTextBox=findViewById(R.id.toTextBox);

        taskTitle=taskTitleTextBox.getText().toString();
        title=titleTextBox.getText().toString();
        from=fromTextBox.getText().toString();
        to=toTextBox.getText().toString();

        Task task=new Task("this","to","here");

        if(operation.equals("update")){
            taskTitleTextBox.setEnabled(false);
            taskTitleTextBox.setText(task.getTitle());
            titleTextBox.setText(task.getTitle());
            fromTextBox.setText(task.getFrom());
            toTextBox.setText(task.getTo());
        }
        else if(operation.equals("create")){
            taskTitleTextBox.setEnabled(true);
        }

        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(operation.equals("update")){

                    Task task=new Task(taskTitle,from,to);

                    boolean result=client.updataTask(task);
                    if(result==true){
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"update success", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                        ActivityTaskWrite.this.startActivity(myIntent);
                    }
                    else{
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"update failed", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                        ActivityTaskWrite.this.startActivity(myIntent);
                    }
                }
                else if(operation.equals("create")){

                    Task task=new Task(taskTitle,from,to);
                    boolean result=client.createTask(task);

                    if(result==true){
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"create success", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                        ActivityTaskWrite.this.startActivity(myIntent);
                    }
                    else{
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"create failed", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                        ActivityTaskWrite.this.startActivity(myIntent);
                    }

                }
            }
        });

    }
}
