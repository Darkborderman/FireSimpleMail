package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import FSMServer.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ncku.firesimplemail.ActivityLogin.account;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityTaskWrite extends AppCompatActivity implements NewOptionDialog.Callback, DropdownList.Callback{

    TextView taskTitleTextBox;
    TextView titleTextBox,toTextBox;
    String taskTitle,title,from,to;
    Button saveButton, addButton;
    LinearLayout linearLayout;
    String operation,ID;
    boolean result;
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
        toTextBox=findViewById(R.id.toTextBox);

        taskTitle=taskTitleTextBox.getText().toString();
        title=titleTextBox.getText().toString();
        from=account+"@mail.FSM.com";
        to=toTextBox.getText().toString();

        if(operation.equals("update")){

            ID=getIntent().getStringExtra("ID");
            Thread thread = new Thread(fetchTask);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            taskTitleTextBox.setEnabled(false);
            taskTitleTextBox.setText(task.getTitle());
            titleTextBox.setText(task.getTitle());
            toTextBox.setText(task.getReceiver());

            Text[] texts = task.getText();
            String[] strs;
            for (int i = 0; i < texts.length; i++) {
                strs = texts[i].getAllText();
                DropdownList ddt = new DropdownList(ActivityTaskWrite.this, strs);
                dropdownlists.add(ddt);
                linearLayout.addView(ddt.spinner);
            }

        } else if(operation.equals("create")){
            taskTitleTextBox.setEnabled(true);
        }

        addButton = findViewById(R.id.button);

        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Text> texts = new ArrayList<>();
                for (int i = 0; i < dropdownlists.size(); i++) {
                    DropdownList ddt = dropdownlists.get(i);
                    // Note: we can't downcast Object[] to String[], use toArray(T [])
                    List<String> list = ddt.options.subList(1, ddt.options.size() - 2);
                    String [] strs = list.toArray(new String[list.size()]);
                    if (strs.length == 1) {
                        texts.add(new SingleText(strs[0]));
                    } else if (strs.length > 1) {
                        texts.add(new MultiText(strs));
                    }
                }

                taskTitle=taskTitleTextBox.getText().toString();
                title=titleTextBox.getText().toString();
                from=account+"@mail.FSM.com";
                to=toTextBox.getText().toString();

                task = new Task(from,to,title, texts.toArray(new Text[texts.size()]),new Date(),1000);

                Thread thread = new Thread(connect);
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(result) {
                    Toast toast = Toast.makeText(ActivityTaskWrite.this,operation + " success", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                    ActivityTaskWrite.this.startActivity(myIntent);
                } else {
                    Toast toast = Toast.makeText(ActivityTaskWrite.this,operation + " failed", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void addDropdownList(View view) {
        DropdownList ddt = new DropdownList(ActivityTaskWrite.this, null);
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
            if(operation.equals("update")){
                result=client.updateTask(ID,task);
            }
            else if(operation.equals("create")){
                result=client.createTask(task);
            }

        }
    };
    private Runnable fetchTask= new Runnable(){
        public void run(){
            task=client.getTask(ID);
        }
    };
}
