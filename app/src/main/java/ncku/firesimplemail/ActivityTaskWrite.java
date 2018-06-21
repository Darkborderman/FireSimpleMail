package ncku.firesimplemail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import FSMServer.*;

import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static ncku.firesimplemail.ActivityLogin.account;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityTaskWrite extends AppCompatActivity implements NewOptionDialog.Callback, DropdownList.Callback{


    TextView titleTextBox,toTextBox;
    String title,from,to;
    Button saveButton, addButton,deleteTaskButton;
    LinearLayout linearLayout;
    String operation,ID;
    Switch scheduleButton, durationButton;
    EditText dateTextBox, durationTextBox;
    Calendar calendar = Calendar.getInstance();
    boolean schedule, duration;
    boolean result;
    private ArrayList<DropdownList> dropdownlists = new ArrayList<>();
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_write);

        operation=getIntent().getStringExtra("Operation");
        linearLayout = findViewById(R.id.linearLayout);

        deleteTaskButton=findViewById(R.id.deleteTaskButton);
        titleTextBox=findViewById(R.id.titleTextBox);
        toTextBox=findViewById(R.id.toTextBox);

        title=titleTextBox.getText().toString();
        from=account+"@mail.FSM.com";
        to=toTextBox.getText().toString();


        dateTextBox = findViewById(R.id.dateTextBox);
        dateTextBox.setInputType(InputType.TYPE_NULL);
        dateTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTimeAndDate();
            }
        });
        dateTextBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectTimeAndDate();
                }
            }
        });
        durationTextBox = findViewById(R.id.durationTextBox);


        durationButton = findViewById(R.id.durationButton);
        durationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                durationTextBox.setEnabled(isChecked);
                duration = isChecked;
            }
        });

        scheduleButton = findViewById(R.id.scheduleButton);
        scheduleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateTextBox.setEnabled(isChecked);
                schedule = isChecked;
            }
        });

        if(operation.equals("update")){

            ID=getIntent().getStringExtra("ID");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    task=client.getTask(ID);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            titleTextBox.setEnabled(false);
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

            Date d = task.getSendDate();
            calendar.setTime(d);
            if (d.after(new Date())) {
                scheduleButton.setChecked(true);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm");
                dateTextBox.setHint(formatter.format(d));
            }

            if (task.getInterval() != 0) {
                durationButton.setChecked(true);
                durationTextBox.setText(Integer.toString(task.getInterval()));
            }

        } else if(operation.equals("create")){
            titleTextBox.setEnabled(true);
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

                title=titleTextBox.getText().toString();
                from=account+"@mail.FSM.com";
                to=toTextBox.getText().toString();

                Date date;
                if (schedule) {
                    date = calendar.getTime();
                } else {
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.SECOND, 10);
                    date = c.getTime();
                }

                int interval = 0;
                if (duration) {
                    String val_str = durationTextBox.getText().toString();
                    if (!val_str.equals(""))
                        interval = Integer.valueOf(val_str);
                }

                task = new Task(from,to,title, texts.toArray(new Text[texts.size()]), date, interval);

                Thread thread = new Thread(connect);
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(result) {
                    Toast.makeText(ActivityTaskWrite.this,operation + " success", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                    ActivityTaskWrite.this.startActivity(myIntent);
                } else {
                    Toast.makeText(ActivityTaskWrite.this,operation + " failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result=client.deleteTask(ID);
                    }
                });
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(result)
                {
                    Toast.makeText(ActivityTaskWrite.this,"success",Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                    ActivityTaskWrite.this.startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(ActivityTaskWrite.this,"failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void selectTimeAndDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(ActivityTaskWrite.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(ActivityTaskWrite.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm");
                        dateTextBox.setHint(formatter.format(calendar.getTime()));
                    }
                }, hour, minute, false).show();
            }
        }, year, month, day).show();
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
}
