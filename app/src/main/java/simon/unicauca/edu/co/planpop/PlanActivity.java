package simon.unicauca.edu.co.planpop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class PlanActivity extends AppCompatActivity {

    TextView nombre, descripcion,fecha, hora;
    Button ver_mapa, ir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        nombre = (TextView) findViewById(R.id.plan_nombre);
        descripcion = (TextView) findViewById(R.id.plan_descripcion);
        fecha = (TextView) findViewById(R.id.plan_fecha);
        hora = (TextView) findViewById(R.id.plan_Hora);

        ver_mapa = (Button) findViewById(R.id.btn_ver_lugar);
        ir = (Button) findViewById(R.id.btn_asistir);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
