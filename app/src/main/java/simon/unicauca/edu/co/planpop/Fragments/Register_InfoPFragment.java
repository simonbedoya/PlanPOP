package simon.unicauca.edu.co.planpop.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;


import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register_InfoPFragment extends Fragment {

    EditText birthdate,cmp_name, user, passw;
    private int mYear, mMonth, mDay;
    Button next;
    RadioButton ho,mu;



    public Register_InfoPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register__info, container, false);
        Register_IpFragment reg = new Register_IpFragment();

        next = (Button) v.findViewById(R.id.btn_nextip);
        cmp_name = (EditText) v.findViewById(R.id.edt_name);
        user = (EditText) v.findViewById(R.id.edt_user);
        passw = (EditText) v.findViewById(R.id.edt_pass);
        birthdate = (EditText) v.findViewById(R.id.edt_fechan);




        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Setear valor en editText
                                birthdate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.c_name = cmp_name.getText().toString();
                AppUtil.b_date = birthdate.getText().toString();
                AppUtil.username = user.getText().toString();
                AppUtil.pass = passw.getText().toString();
                String ma = AppUtil.email;
                String sexo = AppUtil.sex;

                ParseUser user = new ParseUser();
                user.setUsername(AppUtil.username);
                user.setPassword(AppUtil.pass);
                user.setEmail(AppUtil.email);
                user.put("name", AppUtil.c_name);
                user.put("b_date",AppUtil.b_date);
                user.put("sex",AppUtil.sex);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign","Correcto");
                        } else {
                            Log.i("Sign","Incorrecto");
                        }
                    }
                });
            }
        });

        return v;
    }






}
