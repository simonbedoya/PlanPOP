package simon.unicauca.edu.co.planpop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.Fragments.ListaFragment;
import simon.unicauca.edu.co.planpop.Fragments.MapsFragment;
import simon.unicauca.edu.co.planpop.Fragments.VerPlanFragment;
import simon.unicauca.edu.co.planpop.parse.PlanParse;
import simon.unicauca.edu.co.planpop.parse.UpdateParse;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener, VerPlanFragment.ActionVerPLanFragment, MapsFragment.OnLugarSelected{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);


        VerPlanFragment verPlanFragment = new VerPlanFragment();
        putFragment(R.id.container_ver_plan,verPlanFragment);


    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            ListaFragment list = new ListaFragment();
            list.Reload();

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ListaFragment list = new ListaFragment();
            list.Reload();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick (View v){


    }
    public void putFragment(int idContainer, Fragment fragment){
        FragmentTransaction fT = getSupportFragmentManager()
                .beginTransaction();
        fT.replace(idContainer, fragment);
        fT.commit();
    }

    @Override
    public void cargarMapa(double latitud, double longitud) {
        MapsFragment mapsFragment = new MapsFragment();
        mapsFragment.init(latitud, longitud, 18.0f);
        putFragment(R.id.container_ver_plan, mapsFragment);
    }

    @Override
    public void onLugarSelected(double latitud, double longitud) {

    }
}
