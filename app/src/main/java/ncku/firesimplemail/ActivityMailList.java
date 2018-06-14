package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMailList extends AppCompatActivity{

    Button getMailButton;
    Client client=new Client("localhost",1111);

    //fake data
    Id id=new Id(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mail_list);

        MailHead[] mailHeads=(MailHead[]) getIntent().getSerializableExtra("Class");

        getMailButton=findViewById(R.id.button);
        getMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ActivityMailList.this, ActivityMailView.class);
                Mail mail=client.getMail(id);
                myIntent.putExtra("Class",mail);
                ActivityMailList.this.startActivity(myIntent);
            }
        });
    }
}
