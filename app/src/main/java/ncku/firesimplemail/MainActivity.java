package ncku.firesimplemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginButton,registerButton,testButton;
    TextView accountTextbox,passwordTextbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //login Button
        loginButton=findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, TaskListActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //Register Button
        registerButton=findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this,"Not available", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        //Test Button
        testButton=findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                accountTextbox=findViewById(R.id.accountTextbox);
                String account=accountTextbox.getText().toString();
                passwordTextbox=findViewById(R.id.passwordTextbox);
                String password=passwordTextbox.getText().toString();

                if(account!=null||password!=null){
                    Toast toast = Toast.makeText(MainActivity.this,account+"\n"+password, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
