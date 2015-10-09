package simon.unicauca.edu.co.planpop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import simon.unicauca.edu.co.planpop.Fragments.AddFragment;
import simon.unicauca.edu.co.planpop.Fragments.MapsFragment;


public class AddActivity extends AppCompatActivity implements AddFragment.ActionListenerAddFragment, MapsFragment.OnLugarSelected {


    AddFragment addFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addFragment= new AddFragment();

        MapsFragment mapsFragment = new MapsFragment();

        putFragment(R.id.add_container1,addFragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getItemId() == android.R.id.home){
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void actionLoad(Boolean maps, Boolean finishAdd) {
        if(maps){

        }
    }

    @Override
    public void terminarFragment(Boolean finish) {

    }

    public void putFragment(int idContainer, Fragment fragment){
        android.support.v4.app.FragmentTransaction fT = getSupportFragmentManager()
                .beginTransaction();
        fT.replace(idContainer, fragment);
        fT.commit();
    }

    @Override
    public void onLugarSelected(double latitud, double longitud) {

    }
}
