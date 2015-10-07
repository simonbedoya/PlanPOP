package simon.unicauca.edu.co.planpop.Fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import simon.unicauca.edu.co.planpop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register_cypFragment extends Fragment implements View.OnClickListener {

    Button bnt_next;
    Register_IpFragment reg;

    public Register_cypFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_cyp, container, false);
        bnt_next = (Button) v.findViewById(R.id.btn_nextcyp);
        bnt_next.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        reg = new Register_IpFragment();
        android.support.v4.app.FragmentTransaction fT = getFragmentManager().beginTransaction();
        fT.replace(R.id.container, reg);
        fT.commit();
    }
}
