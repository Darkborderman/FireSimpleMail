package ncku.firesimplemail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import FSMServer.*;


public class ActivityMailView extends AppCompatActivity{

    Mail mail;
    TextView titleLabel,senderLabel,receiverLabel,bodyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mail=(Mail)getIntent().getSerializableExtra("Class");
        setContentView(R.layout.layout_mail_view);

        String title=mail.getTitle();
        titleLabel=findViewById(R.id.titleLabel);
        titleLabel.setText(title);

        String sender=mail.getSender();
        senderLabel=findViewById(R.id.senderLabel);
        senderLabel.setText(sender);

        String receiver=mail.getReceiver();
        receiverLabel=findViewById(R.id.receiverLabel);
        receiverLabel.setText(receiver);

        bodyLabel=findViewById(R.id.bodyLabel);
        bodyLabel.setText(mail.getBody());

    }
}
