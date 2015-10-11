package simon.unicauca.edu.co.planpop.parse;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by sbv23 on 11/10/2015.
 */
public class SearchParse {

    public void Search(String searching){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Plan");
        query.whereStartsWith("nombre",searching);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    Log.d("busqueda", "encontrados " + objects.size());
                }else{
                    Log.d("Error", e.getMessage());
                }
            }
        });

    }

}
