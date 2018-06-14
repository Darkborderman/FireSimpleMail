package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    Button loginButton,registerButton,testButton;
    TextView accountTextbox,passwordTextbox;
    String account,password;

    Client client=new Client("localhost",1111);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        //Text boxes
        accountTextbox=findViewById(R.id.accountTextbox);
        passwordTextbox=findViewById(R.id.passwordTextbox);

        //login button
        loginButton=findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account=accountTextbox.getText().toString();
                password=passwordTextbox.getText().toString();

                boolean result=client.authenticate(account,password);

                if(result==true){
                    Intent myIntent = new Intent(ActivityLogin.this, ActivityFacilityList.class);
                    ActivityLogin.this.startActivity(myIntent);
                }
                else{
                    Toast toast = Toast.makeText(ActivityLogin.this,"Login failed.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        //register button
        registerButton=findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account=accountTextbox.getText().toString();
                password=passwordTextbox.getText().toString();

                boolean result=client.regist(account,password);

                if(result==true){
                    Intent myIntent = new Intent(ActivityLogin.this, ActivityFacilityList.class);
                    ActivityLogin.this.startActivity(myIntent);
                }
                else{
                    Toast toast = Toast.makeText(ActivityLogin.this,"Register failed.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
