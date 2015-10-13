package simon.unicauca.edu.co.planpop.parse;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.adapters.PagerAdapter;
import simon.unicauca.edu.co.planpop.adapters.PlanAdapter;
import simon.unicauca.edu.co.planpop.models.Plan;

/**
 * Created by sbv23 on 11/10/2015.
 */
public class SearchParse {

    Plan p = new Plan();



    public void Search(String searching){

        AppUtil.data = new ArrayList<Plan>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Plan");
        query.whereStartsWith("nombre", searching);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("busqueda", "encontrados " + objects.size());
                    for (int i=0; i<objects.size();i++){
                        Log.d("encontrados", objects.get(i).getString("nombre"));
                        p.setId(objects.get(i).getString("objectId"));
                        p.setNombre(objects.get(i).getString("nombre"));
                        p.setDescripcion(objects.get(i).getString("descripcion"));
                        p.setFecha(objects.get(i).getString("fecha"));
                        p.setLugar(objects.get(i).getParseGeoPoint("lugar"));
                        AppUtil.data.add(p);


                    }

                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });

    }

}
