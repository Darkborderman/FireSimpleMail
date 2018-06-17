package ncku.firesimplemail;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import FSMServer.*;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static ncku.firesimplemail.ActivityLogin.account;
import static ncku.firesimplemail.ActivityLogin.password;

public class ActivityTaskWrite extends AppCompatActivity implements NewOptionDialog.Callback, DropdownList.Callback{

    TextView taskTitleTextBox;
    TextView titleTextBox,fromTextBox,toTextBox;
    String taskTitle,title,from,to;
    Button saveButton, addButton;
    LinearLayout linearLayout;
    String operation;
    boolean result;
    Client client=new Client("140.116.245.100",6000);
    private ArrayList<DropdownList> dropdownlists = new ArrayList<>();
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_write);

        operation=getIntent().getStringExtra("Operation");
        linearLayout = findViewById(R.id.linearLayout);

        taskTitleTextBox=findViewById(R.id.taskTitleTextBox);
        titleTextBox=findViewById(R.id.titleTextBox);
        fromTextBox=findViewById(R.id.fromTextBox);
        toTextBox=findViewById(R.id.toTextBox);

        taskTitle=taskTitleTextBox.getText().toString();
        title=titleTextBox.getText().toString();
        from=fromTextBox.getText().toString();
        to=toTextBox.getText().toString();

        if(operation.equals("update")){
            // get Text
            String selectedId=getIntent().getStringExtra("selectedId");
            Task task=client.getTask(selectedId);
            taskTitleTextBox.setEnabled(false);
            taskTitleTextBox.setText(task.getTitle());
            titleTextBox.setText(task.getTitle());
            fromTextBox.setText(task.getSender());
            toTextBox.setText(task.getReceiver());
            // TODO: Spinner binding
        } else if(operation.equals("create")){
            taskTitleTextBox.setEnabled(true);
        }

        addButton = findViewById(R.id.button);


        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(operation.equals("update")){
                    String selectedId=getIntent().getStringExtra("selectedId");
                    Task task=client.getTask(selectedId);

                    //boolean result=client.updateTask(selectedId,task);
                    if(result){
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"update success", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                        ActivityTaskWrite.this.startActivity(myIntent);
                    }
                    else{
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"update failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else if(operation.equals("create")){

                    Text[] text= new Text[] {
                        new SingleText("Sorry, "),
                        new MultiText(new String[] {
                            "Someone fuck up the server.",
                            "working on routing.",
                            "ZZZzzzz."
                        })
                    };
                    task=new Task(from,to,title,text,new Date(),1000);

                    Thread thread = new Thread(connect);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(result){
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"create success", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                        ActivityTaskWrite.this.startActivity(myIntent);
                    }
                    else{
                        Toast toast = Toast.makeText(ActivityTaskWrite.this,"create failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });

    }

    public void addDropdownList(View view) {
        DropdownList ddt = new DropdownList(ActivityTaskWrite.this);
        dropdownlists.add(ddt);
        linearLayout.addView(ddt.spinner);
    }

    @Override
    public void onDialogPositiveClick() {
        //tasks.add(str);
        //debugLog("Dialog OK");
    }

    @Override
    public void onDialogNegativeClick() {
        //debugLog("Dialog Cancel");
    }

    @Override
    public void notifyDestruction(DropdownList list) {
        dropdownlists.remove(list);
        linearLayout.removeView(list.spinner);
    }

    private Runnable connect = new Runnable() {
        public void run() {
            client.authenticate(account,password);
            if(operation.equals("update")){

            }
            else if(operation.equals("create")){
                result=client.createTask(task);
            }

        }
    };
}
