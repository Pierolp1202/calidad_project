package com.example.pierolpw10.serviexpress.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pierolpw10.serviexpress.R;

/**
 * Created by NriKe on 22/10/2017.
 */

public class HistorialFragment extends Fragment {

    RecyclerView rv_historial;
    TextView tv_empty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_historial,container,false);

        rv_historial = (RecyclerView) v.findViewById(R.id.rv_historial);
        tv_empty = (TextView) v.findViewById(R.id.tv_empty);

        return v;
    }
}
