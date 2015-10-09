package simon.unicauca.edu.co.planpop.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import simon.unicauca.edu.co.planpop.R;
import simon.unicauca.edu.co.planpop.models.Plan;
import simon.unicauca.edu.co.planpop.parse.PlanParse;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener, PlanParse.PlanParseInterface {


    public  interface ActionListenerAddFragment{
        void actionLoad(Boolean maps, Boolean finishAdd);
        void terminarFragment(Boolean finish);

    }

    public static final int CAMERA=101;
    public static final int RESULT_OK = -1;

    private int mYear, mMonth, mDay, mHora,mMinuto;
    private String Sfecha,Shora;

    private File ImgF;

    ActionListenerAddFragment actionListenerAddFragment;

    ImageView img;
    EditText edit_nombre,edit_descripcion;
    TextView txt_fecha, txt_hora;
    Button btn_fecha,btn_hora,btn_lugar,btn_aceptar;

    Context context;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        actionListenerAddFragment = (ActionListenerAddFragment) context;
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_add, container, false);

        img = (ImageView) v.findViewById(R.id.image_add);
        edit_nombre = (EditText) v.findViewById(R.id.nombre);
        edit_descripcion = (EditText) v.findViewById(R.id.descripcion);
        txt_fecha = (TextView) v.findViewById(R.id.fecha);
        txt_hora = (TextView) v.findViewById(R.id.hora);
        btn_fecha = (Button) v.findViewById(R.id.btn_fecha);
        btn_hora = (Button) v.findViewById(R.id.btn_hora);
        btn_lugar = (Button) v.findViewById(R.id.btn_marcar_lugar);

        btn_aceptar = (Button) v.findViewById(R.id.btn_add);

        //<editor-fold desc="Botton lugar">
        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker =new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Sfecha = dayOfMonth+"/"+monthOfYear+"/"+year;
                                txt_fecha.setText(Sfecha);

                            }
                },mYear,mMonth,mDay);
                datePicker.show();
            }
        });
        //</editor-fold>

        //<editor-fold desc="Hora">
        btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mHora = calendar.get(calendar.HOUR_OF_DAY);
                mMinuto  = calendar.get(calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm;
                        if(hourOfDay < 12) {
                            am_pm = "AM";
                        } else {
                            am_pm = "PM";
                        }
                        Shora = hourOfDay+":"+minute+" "+am_pm;
                        txt_hora.setText(Shora);
                    }
                },mHora,mMinuto,false);
                timePicker.show();
            }
        });
        //</editor-fold>

        btn_lugar.setOnClickListener(this);
        btn_aceptar.setOnClickListener(this);

        img.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fT;
        switch (v.getId()){
            case R.id.image_add:

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/PLanes");

                if(!dir.isDirectory()){
                    dir.mkdir();
                }
                ImgF = new File(dir, edit_nombre.getText().toString()+".png");
                Uri uri = Uri.fromFile(ImgF);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA);

                break;

            case R.id.btn_marcar_lugar:

                android.support.v4.app.FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                MapsFragment mapsFragment = new MapsFragment();
                ft.replace(R.id.add_container1, mapsFragment);
                ft.commit();
                actionListenerAddFragment.actionLoad(true,false);
                break;

            case R.id.btn_add:
                Plan plan = new Plan();
                plan.setNombre(edit_nombre.getText().toString());
                plan.setDescripcion(edit_descripcion.getText().toString());
                plan.setFecha(txt_fecha.getText().toString());
                plan.setImgPath(ImgF.getPath());

                PlanParse planParse = new PlanParse(this);

                try {
                    planParse.insertPlan(plan);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                actionListenerAddFragment.actionLoad(false,true);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA){
            if(resultCode == RESULT_OK){
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                try {
                    scaleImage(800);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void scaleImage(int maxAxis) throws IOException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(ImgF.getPath(), o);

        int w = o.outWidth;
        int h = o.outHeight;

        int a = w>h?w:h;
        int sampleSize=1;

        while (a>maxAxis){
            sampleSize = sampleSize*2;
            a=a/2;
        }
        o.inJustDecodeBounds=false;
        o.inSampleSize = sampleSize;

        Bitmap b = BitmapFactory.decodeFile(ImgF.getPath(), o);

        ImgF.delete();

        FileOutputStream out = new FileOutputStream(ImgF);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,stream);

        out.write(stream.toByteArray());

        b.recycle();
        b=null;

        out.close();
        stream.close();

        Picasso.with(context).load(ImgF).into(img);
    }

    @Override
    public void done(boolean exito) {
        if(exito){
            actionListenerAddFragment.terminarFragment(exito);
        }
        else
            Toast.makeText(context,"Error al insertar el plan",Toast.LENGTH_SHORT);

    }

    @Override
    public void resultPlan(boolean exito, Plan plan) {

    }

    @Override
    public void resultListPlans(boolean exito, List<Plan> planes) {

    }


}
