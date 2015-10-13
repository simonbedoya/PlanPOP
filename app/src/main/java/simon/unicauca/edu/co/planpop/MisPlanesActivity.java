package simon.unicauca.edu.co.planpop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.parse.ParseGeoPoint;

import java.util.List;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.Fragments.AddPlanMapsFragment;
import simon.unicauca.edu.co.planpop.Fragments.EditarMisPlanesFragment;
import simon.unicauca.edu.co.planpop.Fragments.VerMisPlanesFragment;
import simon.unicauca.edu.co.planpop.models.Plan;
import simon.unicauca.edu.co.planpop.parse.PlanParse;

public class MisPlanesActivity extends AppCompatActivity implements VerMisPlanesFragment.ActionVerMisPlanes,EditarMisPlanesFragment.ActionEditarMisPlanesFragment,AddPlanMapsFragment.OnLugarSelected, PlanParse.PlanParseInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_planes);

        VerMisPlanesFragment verMisPlanesFragment = new VerMisPlanesFragment();
        putFragment(R.id.mis_planes_contairner, verMisPlanesFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void putFragment(int idContainer, Fragment fragment){
        FragmentTransaction fT = getSupportFragmentManager()
                .beginTransaction();
        fT.replace(idContainer, fragment);
        fT.commit();
    }

    @Override
    public void Actualizado(Boolean exito) {
        if(exito) {
            VerMisPlanesFragment verMisPlanesFragment = new VerMisPlanesFragment();
            putFragment(R.id.mis_planes_contairner, verMisPlanesFragment);
        }
        else{
            Log.i("Error","Error");
        }
    }

    @Override
    public void editarInformacion(Boolean informacion, Boolean maps) {
        if(informacion){
            EditarMisPlanesFragment editarMisPlanesFragment = new EditarMisPlanesFragment();
            putFragment(R.id.mis_planes_contairner, editarMisPlanesFragment);
        }
        else if(maps){
            AddPlanMapsFragment addPlanMapsFragment = new AddPlanMapsFragment();
            putFragment(R.id.mis_planes_contairner, addPlanMapsFragment);
        }
    }

    @Override
    public void eliminarPlan(Boolean exito) {
        if(exito){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onLugarSelected(double latitud, double longitud, String direccion, String nombreLugar) {
        Plan p = AppUtil.data_misPlanes.get(AppUtil.positionSelectedMisPlanes);
        ParseGeoPoint posicion = new ParseGeoPoint(latitud,longitud);
        p.setLugar(posicion);
        p.setDireccion(nombreLugar);
        PlanParse planParse = new PlanParse(this);
        planParse.updatePlan(p);


        VerMisPlanesFragment verMisPlanesFragment = new VerMisPlanesFragment();
        putFragment(R.id.mis_planes_contairner, verMisPlanesFragment);

    }

    @Override
    public void done(boolean exito) {

    }

    @Override
    public void resultPlan(boolean exito, Plan plan) {

    }

    @Override
    public void resultListPlans(boolean exito, List<Plan> planes) {

    }
}
