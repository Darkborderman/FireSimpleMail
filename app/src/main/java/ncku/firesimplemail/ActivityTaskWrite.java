package ncku.firesimplemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityTaskWrite extends AppCompatActivity{

    TextView taskTitleTextBox;
    TextView titleTextBox,fromTextBox,toTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_write);

        taskTitleTextBox=findViewById(R.id.taskTitleTextBox);
        titleTextBox=findViewById(R.id.titleTextBox);

        String operation=getIntent().getStringExtra("Operation");

        if(operation.equals("update")) taskTitleTextBox.setEnabled(false);
        else if(operation.equals("create")) taskTitleTextBox.setEnabled(true);

    }
}
