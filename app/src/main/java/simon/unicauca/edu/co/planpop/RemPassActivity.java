package simon.unicauca.edu.co.planpop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.text.ParseException;

public class RemPassActivity extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem_pass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logout = (Button) findViewById(R.id.btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.requestPasswordResetInBackground("simonbedoyavalencia@gmail.com", new RequestPasswordResetCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                        }
                    }
                });

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rem_pass, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(RemPassActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
