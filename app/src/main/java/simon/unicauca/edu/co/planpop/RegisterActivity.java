package simon.unicauca.edu.co.planpop;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import simon.unicauca.edu.co.planpop.Fragments.Register_cypFragment;

public class RegisterActivity extends AppCompatActivity {

    Register_cypFragment reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reg = new Register_cypFragment();

        putFragment(R.id.container,reg);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void putFragment(int idContainer, Fragment fragment){
        FragmentTransaction fT = getSupportFragmentManager()
                .beginTransaction();
        fT.replace(idContainer, fragment);
        fT.commit();
    }
}
