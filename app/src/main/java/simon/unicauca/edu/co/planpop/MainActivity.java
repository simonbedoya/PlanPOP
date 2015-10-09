package simon.unicauca.edu.co.planpop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;

import simon.unicauca.edu.co.planpop.adapters.PagerAdapter;
import simon.unicauca.edu.co.planpop.Fragments.ListaFragment;
import simon.unicauca.edu.co.planpop.Fragments.MapsFragment;
import simon.unicauca.edu.co.planpop.Fragments.TitleFragment;
import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelectedList, MapsFragment.OnLugarSelected {

    ViewPager pager;
    List<TitleFragment> data;
    PagerAdapter adapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        data = new ArrayList<>();


        ListaFragment list = new ListaFragment();
        data.add(list);

        MapsFragment mapa = new MapsFragment();
        data.add(mapa);

        adapter = new PagerAdapter(getSupportFragmentManager(), data);
        pager.setAdapter(adapter);



       // ParseQuery<ParseUser> parseQuery = ParseQuery.getQuery("User");
       // parseQuery.whereEqualTo("nombre","franklin");

    }

    /*@Override
    protected void onRestart() {
        adapter.notifyDataSetChanged();
        super.onRestart();
    }*/

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_add:

                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);

                break;

            case R.id.action_search:
                Toast.makeText(this,"Buscar",Toast.LENGTH_LONG);
                break;
            case R.id.action_logout:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();

                break;
            }

        return super.onOptionsItemSelected(item);
    }

    public void putFragment(int idContainer, Fragment fragment){
        FragmentTransaction fT = getSupportFragmentManager()
                .beginTransaction();
        fT.replace(idContainer, fragment);
        fT.commit();
    }


    @Override
    public void onItemSelectedList(int position) {
        AppUtil.positionSelected = position;
        Intent intent = new Intent(this,PlanActivity.class);
        startActivity(intent);
    }

    // metodo todavia no implementado.
    @Override
    public void onLugarSelected(double latitud, double longitud) {
        Toast.makeText(this,latitud+" "+longitud,Toast.LENGTH_LONG);
    }


}