package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import FSMServer.*;

public class ActivityLogin extends AppCompatActivity {

    Button loginButton,registerButton;
    TextView accountTextbox,passwordTextbox;
    String password;
    public static String account;
    String operation;
    boolean result;

    public static Client client=new Client("140.116.245.100",6000);

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

                operation="login";
                Thread thread = new Thread(connect);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(result){
                    Intent serviceIntent = new Intent(ActivityLogin.this, TaskRunner.class);
                    serviceIntent.putExtra("type", "auth");
                    serviceIntent.putExtra("account", account);
                    serviceIntent.putExtra("password", password);
                    //stopService(serviceIntent);
                    startService(serviceIntent);
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

                operation="register";
                Thread thread = new Thread(connect);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(result) {
                    Intent serviceIntent = new Intent(ActivityLogin.this, TaskRunner.class);
                    serviceIntent.putExtra("type", "auth");
                    serviceIntent.putExtra("account", account);
                    serviceIntent.putExtra("password", password);
                    //stopService(serviceIntent);
                    startService(serviceIntent);
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

    private Runnable connect = new Runnable() {
        public void run() {
            if(operation.equals("login")) result=client.authenticate(account,password);
            else if(operation.equals("register")) result=client.regist(account,password);

        }
    };
}
