package simon.unicauca.edu.co.planpop.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.R;
import simon.unicauca.edu.co.planpop.adapters.PlanAdapter;
import simon.unicauca.edu.co.planpop.models.Plan;
import simon.unicauca.edu.co.planpop.parse.PlanParse;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaFragment extends TitleFragment implements AdapterView.OnItemClickListener, PlanParse.PlanParseInterface {

    static final int PAGE=10;
/*
    public static final int TYPE_RECENTS=0;
    public static final int TYPE_PAGE=1;
    public static final int TYPE_ALL=2;
*/
    int type;


    public interface OnItemSelectedList {
        void onItemSelectedList(int position);
    }

    OnItemSelectedList onItemSelectedList;

    ListView list;
    PlanAdapter adapter;
    String planes[];
    Context context;

    PlanParse planParse;

  //  SwipeRefreshLayout refreshLayout;



    public ListaFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        onItemSelectedList = (OnItemSelectedList) context;
        super.onAttach(context);
        planes = getResources().getStringArray(R.array.planes);
        AppUtil.data = new ArrayList<>();
        AppUtil.positionSelected = -1;
        adapter = new PlanAdapter(context, AppUtil.data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista, container, false);
/*
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        int color1 = ContextCompat.getColor(context, R.color.colorPrimary);
        int color2 = ContextCompat.getColor(context,R.color.primary_dark_material_dark);
        int color3 =  ContextCompat.getColor(context, R.color.accent_material_light);

        refreshLayout.setColorSchemeColors(color1,color2,color3);
*/

        list = (ListView) v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

        planParse = new PlanParse(this);

        planParse.getPlanByPages(PAGE, null);
 //       type = PAGE;
        planParse.getPlanById("YMjI8yzg5B");

        //list.setOnScrollListener(this);

        return v;
    }


    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public String getTitle() {
        return "Planes";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemSelectedList.onItemSelectedList(position);
    }


    //region ParsePlan
    @Override
    public void done(boolean exito) {

    }

    @Override
    public void resultPlan(boolean exito, Plan plan) {
        if (exito == true)
            Log.i("resultPLan exito:", "es verdadero");

        else
            Log.i("resultPLan exito:", "es falso");

    }

    @Override
    public void resultListPlans(boolean exito, List<Plan> planes) {
        if (exito == true) {
            //if(type == TYPE_PAGE){
                for(int i=0; i<planes.size(); i++){
                    AppUtil.data.add(planes.get(i));
                }
            //}
            /*else if(type == TYPE_RECENTS) {
                refreshLayout.setRefreshing(false);
                if (planes.size() > 0) {
                    for (int i = planes.size() - 1; i >= 0; i--) {
                        AppUtil.data.add(0, planes.get(i));
                    }
                } else {
                    Toast.makeText(context, R.string.Datos_cargados, Toast.LENGTH_SHORT);
                }
            } */
            adapter.notifyDataSetChanged();
        }
        else
            Log.i("resultPLan exito:", "es falso");


    }
    //endregion

/*
    //region Scroll final
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount == totalItemCount && AppUtil.data.size() > 0){
       //     type = TYPE_PAGE;
            planParse.getPlanByPages(PAGE, AppUtil.data.get(totalItemCount-1).getCreatedAt());
        }
    }
    //endregion

    @Override
    public void onRefresh() {
        type=TYPE_RECENTS;
        planParse.getRecentPLanes(AppUtil.data.get(0).getCreatedAt());
    }*/

}
