package com.example.pierolpw10.serviexpress.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pierolpw10.serviexpress.Adapters.HistorialAdapter;
import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Managers.PreferenceManager;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.Models.Work;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NriKe on 22/10/2017.
 */

public class HistorialFragment extends Fragment {

    RecyclerView rv_historial;
    TextView tv_empty;
    HistorialAdapter adapter;
    FirebaseManager manager;
    PreferenceManager p_manager;
    List<Work> data_ls = new ArrayList<>();
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_historial,container,false);

        rv_historial = (RecyclerView) v.findViewById(R.id.rv_historial);
        tv_empty = (TextView) v.findViewById(R.id.tv_empty);

        manager = FirebaseManager.getInstance();

        p_manager = PreferenceManager.getInstance(getActivity());

        getData();
        
        return v;
    }

    private void getData() {
        Gson gson = new Gson();
        final User user = gson.fromJson(p_manager.getPreferenceSession(), User.class);
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF) != null) {
            tv_empty.setVisibility(View.GONE);
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    data_ls = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        if(data.child("username").getValue(String.class).equals(user.getUsername())){
                            String worker = data.child("worker").getValue(String.class);
                            String date = data.child("date").getValue(String.class);
                            Long especialidad_long = (Long) data.child("work").getValue();
                            int especialidad = especialidad_long.intValue();

                            Work w = new Work();
                            w.setWork(especialidad);
                            w.setWorker(worker);
                            w.setUsername(user.getUsername());
                            w.setDate(date);

                            data_ls.add(w);
                        }
                    }
                    setDataIntoRv();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            tv_empty.setVisibility(View.VISIBLE);
        }
    }

    private void setDataIntoRv(){
        adapter = new HistorialAdapter(data_ls,getActivity());
        rv_historial.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_historial.setLayoutManager(mLayoutManager);
    }
}
