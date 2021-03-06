package simon.unicauca.edu.co.planpop.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import simon.unicauca.edu.co.planpop.AddActivity;
import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.R;
import simon.unicauca.edu.co.planpop.models.Plan;
import simon.unicauca.edu.co.planpop.parse.PlanParse;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarMisPlanesFragment extends Fragment implements View.OnClickListener,DialogInterface.OnClickListener, PlanParse.PlanParseInterface {

    public static final int CAMERA=101;
    public static final int SELECT_IMAGE=102;
    public static final int RESULT_OK = -1;


    public interface ActionEditarMisPlanesFragment{
        void Actualizado(Boolean exito);

    }


    ActionEditarMisPlanesFragment actionEditarMisPlanesFragment;

    Context context;

    ImageView imagen;
    EditText nombre,descripcion;
    TextView fecha,hora;
    Button btn_fecha,btn_hora, btn_aceptar;


    File ImgF;

    CharSequence[] options = {"Camara", "Galeria", "Cancelar"};

    private int mYear, mMonth, mDay, mHora,mMinuto;
    private String Sfecha,Shora;

    ProgressDialog progressDialog;

    public EditarMisPlanesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        actionEditarMisPlanesFragment = (ActionEditarMisPlanesFragment) context;
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editar_mis_planes, container, false);;

        nombre = (EditText) v.findViewById(R.id.editNombre_MisPlanes);
        descripcion = (EditText) v.findViewById(R.id.editDescripcion_MisPlanes);
        fecha = (TextView) v.findViewById(R.id.txtFecha_MisPlanes);
        hora = (TextView) v.findViewById(R.id.txtHora_MisPlanes);


        btn_fecha = (Button) v.findViewById(R.id.btn_txtFechaMisPlanes);
        btn_hora = (Button) v.findViewById(R.id.btn_txtHoraMisPlanes);
        btn_aceptar = (Button) v.findViewById(R.id.btn_AceptarMisPlanes);

        imagen = (ImageView) v.findViewById(R.id.image_MisPlanes);

        loadDatos();

        //<editor-fold desc="Botton fecha">
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
                                fecha.setText(Sfecha);

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
                        hora.setText(Shora);
                    }
                },mHora,mMinuto,false);
                timePicker.show();
            }
        });
        //</editor-fold>

        imagen.setOnClickListener(this);
        btn_aceptar.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_MisPlanes:

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Elige una Opción");
                builder.setItems(options,this);
                builder.show();

                break;
            case R.id.btn_AceptarMisPlanes:

                Plan p = AppUtil.data_misPlanes.get(AppUtil.positionSelectedMisPlanes);
                p.setNombre(nombre.getText().toString());
                p.setDescripcion(descripcion.getText().toString());
                p.setFecha(fecha.getText().toString() + " " + hora.getText().toString());
                if(ImgF!=null){p.setImgPath(ImgF.getPath());
                }else{

                }

                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Cargando datos...");
                progressDialog.show();
                PlanParse planParse = new PlanParse(this);
                planParse.updatePlan(p);



                break;

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (options[which] == "Camara") {
            tomarFoto();

        }
        else if (options[which] == "Galeria") {
            elegirFoto();
        }
        else if (options[which] == "Cancelar"){
            dialog.dismiss();
        }
    }

    private void tomarFoto(){


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/PLanes");

        if(!dir.isDirectory()){
            dir.mkdir();
        }


        ImgF = new File(dir, AddActivity.name+ "P" + ".jpg");

        Uri uri = Uri.fromFile(ImgF);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA);


    }

    private void elegirFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecionar Imagen"),SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CAMERA:
                if(resultCode == RESULT_OK){
                    imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    try {

                        scaleImage(800);
                        Uri uri = Uri.fromFile(ImgF);
                        imagen.setImageURI(uri);
                        Log.i("el path es:", ImgF.getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK){

                    Uri uri = data.getData();
                    //ImgF = Uri.;
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = context.getContentResolver().query(uri,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    ImgF = new File(picturePath);
                    imagen.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    Log.i("el path es:", picturePath);

                }
                break;
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
        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        out.write(stream.toByteArray());

        b.recycle();
        b=null;

        out.close();
        stream.close();

        //Picasso.with(context).load(ImgF).into(img);
    }

    private void loadDatos() {
        if(AppUtil.positionSelectedMisPlanes!=-1){

            nombre.setText(AppUtil.data_misPlanes.get(AppUtil.positionSelectedMisPlanes).getNombre());
            descripcion.setText(AppUtil.data_misPlanes.get(AppUtil.positionSelectedMisPlanes).getDescripcion());

            String[] formatDate = AppUtil.data_misPlanes.get(AppUtil.positionSelectedMisPlanes).getFecha().split(" ");
            String date = formatDate[0];
            String hour = formatDate[1];
            String am_pm = formatDate[2];

            fecha.setText(date);
            hora.setText(hour+" "+am_pm);



            Picasso.with(context)
                    .load(Uri.parse(AppUtil.data_misPlanes.get(AppUtil.positionSelectedMisPlanes).getImagen()))
                    .into(imagen);

        }

    }



    @Override
    public void done(boolean exito) {
        progressDialog.cancel();
        progressDialog.hide();
        actionEditarMisPlanesFragment.Actualizado(exito);
    }

    @Override
    public void resultPlan(boolean exito, Plan plan) {

    }

    @Override
    public void resultListPlans(boolean exito, List<Plan> planes) {

    }

}
