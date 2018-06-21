package ncku.firesimplemail;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static ncku.firesimplemail.ActivityLogin.account;
import static ncku.firesimplemail.ActivityLogin.client;

public class ActivityTaskWrite extends AppCompatActivity implements NewOptionDialog.Callback, DropdownList.Callback{


    TextView titleTextBox,toTextBox;

    String title,from,to;
    Button saveButton, addButton,deleteTaskButton, startButton;
    LinearLayout linearLayout;
    String operation,ID;
    Switch scheduleButton, durationButton;
    EditText dateTextBox, durationTextBox;
    Calendar calendar = Calendar.getInstance();
    Random rand = new Random();
    boolean schedule = false, duration = false;
    boolean result;
    String body = "";
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

        from = account + "@mail.FSM.com";

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

            titleTextBox.setText(task.getTitle());
            String to = task.getReceiver();
            int idx;
            if ((idx = to.indexOf("@mail.FSM.com")) > -1) {
                to = to.substring(0, idx);
            }
            toTextBox.setText(to);

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
                if (!to.contains("@mail.FSM.com"))
                    to += "@mail.FSM.com";

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

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) { // Run task or stop
                    if (!schedule && !duration) { // Send mail immediately

                        Thread thread = new Thread(sendMail);
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (result) {
                            Toast toast = Toast.makeText(ActivityTaskWrite.this, "successful", Toast.LENGTH_SHORT);
                            toast.show();
                            //Intent myIntent = new Intent(ActivityTaskWrite.this, ActivityFacilityList.class);
                            //ActivityTaskWrite.this.startActivity(myIntent);
                        } else {
                            Toast toast = Toast.makeText(ActivityTaskWrite.this, " failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {

                        title = titleTextBox.getText().toString();
                        to = toTextBox.getText().toString();
                        if (!to.contains("@mail.FSM.com"))
                            to += "@mail.FSM.com";
                        body = "";
                        //Text [] texts = new Text[12];

                        //ArrayList<Text> texts = new ArrayList<>();
                        ArrayList <String []> texts = new ArrayList<>();
                        for (int i = 0; i < dropdownlists.size(); i++) {
                            DropdownList ddt = dropdownlists.get(i);
                            int index = ddt.spinner.getSelectedItemPosition();

                                List<String> list = ddt.options.subList(1, ddt.options.size() - 2);
                                String [] strs = list.toArray(new String[list.size()]);
                                //texts.add(new MultiText(strs));
                            if (strs.length > 0) {
                                if (index == 0 || index == -1) {
                                    texts.add(strs);
                                } else {
                                    texts.add(Arrays.copyOf(strs, 1));
                                }
                            }
                        }

                        //Text [] textData = texts.toArray(new Text[texts.size()]);

                        int interval = 0;
                        if (duration) {
                            String val_str = durationTextBox.getText().toString();
                            if (!val_str.equals(""))
                                interval = Integer.valueOf(val_str);
                        }

                        //Calendar cal = Calendar.getInstance();
                        // 設定於 3 分鐘後執行
                        //cal.add(Calendar.MINUTE, 3);
                        if (!schedule)
                            calendar = Calendar.getInstance();

                        Intent intent = new Intent(ActivityTaskWrite.this, TaskReceiver.class);
                        //intent.putExtra("msg", "play_hskay");
                        intent.putExtra("title", title);
                        intent.putExtra("to", to);
                        intent.putExtra("from", from);
                        intent.putExtra("interval", interval);
                        intent.putExtra("texts", texts);
                        intent.putExtra("type", "sendMail");

                        PendingIntent pi = PendingIntent.getBroadcast(ActivityTaskWrite.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                        // Alarm...
                        debugLog("Make an alarm...");
                    }
                } else {
                    // Stop the alarm task
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

    }

    @Override
    public void onDialogNegativeClick() {

    }

    @Override
    public void notifyDestruction(DropdownList list) {
        dropdownlists.remove(list);
        linearLayout.removeView(list.spinner);
    }

    private Runnable sendMail = new Runnable() {
        public void run() {

            title = titleTextBox.getText().toString();
            to = toTextBox.getText().toString();
            if (!to.contains("@mail.FSM.com"))
                to += "@mail.FSM.com";
            body = "";
            for (int i = 0; i < dropdownlists.size(); i++) {
                DropdownList ddt = dropdownlists.get(i);
                if (ddt.options.size() == 3) // Empty dropdown list
                    continue;

                int index = ddt.spinner.getSelectedItemPosition();
                if (index == 0 || index == -1) {// <random> or not selected
                    index = (rand.nextInt(ddt.options.size() - 3)) + 1;
                }
                body += ddt.options.get(index);
            }
            Mail mail=new Mail(from, to, title, body, new Date());
            result=client.sendMail(mail);
        }
    };

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



    private void debugLog(String str) {
        Toast.makeText(ActivityTaskWrite.this, str, Toast.LENGTH_SHORT).show();
    };

}