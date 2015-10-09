package simon.unicauca.edu.co.planpop.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import simon.unicauca.edu.co.planpop.R;
import java.io.IOException;
import java.util.List;

/**
 * Created by Frank on 3/10/2015.
 */
public class MapsFragment extends TitleFragment implements View.OnClickListener, GoogleMap.OnMapLongClickListener {

    public interface OnLugarSelected{
        public void onLugarSelected(double latitud, double longitud);
    }

    OnLugarSelected onLugarSelected;

    GoogleMap mMap;
    Context context;

    EditText edt_buscar;
    Button btn_buscar_mapa;

    public MapsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        onLugarSelected = (OnLugarSelected) context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        edt_buscar = (EditText) v.findViewById(R.id.edit_buscar);
        btn_buscar_mapa = (Button) v.findViewById(R.id.btn_buscar_mapa);

        btn_buscar_mapa.setOnClickListener(this);

        setUpMapIfNeeded();
        mMap.setOnMapLongClickListener(this);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public String getTitle() {
        return "Mapa";
    }

    @Override
    public void onClick(View v) {

        String localizacion = edt_buscar.getText().toString();
        List<Address> addressList = null;

        if(localizacion != null && localizacion != ""){
            Geocoder geocoder = new Geocoder(context);
            try {
                addressList = geocoder.getFromLocationName(localizacion, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));


            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        else{

        }

    }

    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        onLugarSelected.onLugarSelected(latLng.latitude, latLng.longitude);
    }
}


