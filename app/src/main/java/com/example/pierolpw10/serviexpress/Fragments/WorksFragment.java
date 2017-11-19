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
import com.example.pierolpw10.serviexpress.Adapters.WorksAdapter;
import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Managers.PreferenceManager;
import com.example.pierolpw10.serviexpress.Models.Work;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NriKe on 18/11/2017.
 */

public class WorksFragment extends Fragment {

    RecyclerView rv_works;
    WorksAdapter adapter;
    TextView tv_empty;

    FirebaseManager manager;
    PreferenceManager p_manager;

    List<Work> works = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_works,container,false);

        rv_works = (RecyclerView) v.findViewById(R.id.rv_works);
        tv_empty = (TextView) v.findViewById(R.id.tv_empty);

        manager = FirebaseManager.getInstance();

        p_manager = PreferenceManager.getInstance(getActivity());

        getData();

        return v;
    }

    private void getData() {
        manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                works = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.child("username").getValue(String.class).equals(p_manager.getPreferenceUsername()) && !data.child("end").getValue(Boolean.class)){
                        Work w = new Work(data.child("username").getValue(String.class),data.child("worker").getValue(String.class),data.child("date").getValue(String.class),data.child("work").getValue(Integer.class),"",data.child("rated").getValue(Boolean.class), data.child("work_rate").getValue(Integer.class),data.child("end").getValue(Boolean.class));
                        works.add(w);
                    }
                }

                setDataIntoRv();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setDataIntoRv() {
        if(works.size() != 0) {
            tv_empty.setVisibility(View.GONE);
            adapter = new WorksAdapter(getActivity(),works);
            rv_works.setAdapter(adapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rv_works.setLayoutManager(mLayoutManager);
        }else{
            tv_empty.setVisibility(View.VISIBLE);
        }
    }
}
