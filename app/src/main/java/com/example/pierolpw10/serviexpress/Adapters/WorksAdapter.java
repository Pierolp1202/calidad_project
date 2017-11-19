package com.example.pierolpw10.serviexpress.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Managers.PreferenceManager;
import com.example.pierolpw10.serviexpress.Models.Work;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NriKe on 18/11/2017.
 */

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.MyHolder>{

    Context context;
    List<Work> data;
    FirebaseManager manager;
    PreferenceManager p_manager;
    private final LayoutInflater inflater;

    public WorksAdapter(Context context, List<Work> data) {
        this.context = context;
        this.data = data;
        manager = new FirebaseManager();
        inflater = LayoutInflater.from(context);
        p_manager = PreferenceManager.getInstance(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_work;
        View view = inflater.inflate(layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String esp_st = "";
        String worker = "";
        switch (data.get(position).getWorker()){
            case "Jorge Gonzales":
                esp_st += "Gasfitero";
                worker = "Jorge Gonzales";
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker1));
                break;
            case "Willy Wonka":
                esp_st += "Electricista";
                worker = "Willy Wonka";
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker2));
                break;
            case "Sheldon Cooper":
                esp_st += "Cerrajero";
                worker = "Sheldon Cooper";
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker3));
                break;
            case "Barry Allen":
                esp_st += "Carpintero";
                worker = "Barry Allen";
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker4));
                break;
            case "Slade Wilson":
                esp_st += "Jardinero";
                worker = "Slade Wilson";
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker5));
                break;
        }

        holder.tv_worker.setText(worker);

        holder.tv_especialidad.setText(esp_st);

        final String finalWorker = worker;
        holder.iv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endWork(finalWorker);
            }
        });
    }

    private void endWork(final String finalWorker) {
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF) != null) {
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String key = "";
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("username").getValue(String.class).equals(p_manager.getPreferenceUsername()) && data.child("worker").getValue(String.class).equals(finalWorker)) {
                            key = data.getKey();
                        }
                    }

                    DatabaseReference ref = manager.getWorksReference().child(key);
                    ref.child("end").setValue(true);

                    Toast.makeText(context,"Trabajo finalizado.",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView worker_image;
        TextView tv_worker;
        TextView tv_date;
        TextView tv_especialidad;
        ImageView iv_end;

        public MyHolder(View itemView) {
            super(itemView);

            worker_image = (CircleImageView) itemView.findViewById(R.id.worker_image);
            tv_worker = (TextView) itemView.findViewById(R.id.tv_worker);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_especialidad = (TextView) itemView.findViewById(R.id.tv_especialidad);
            iv_end = (ImageView) itemView.findViewById(R.id.iv_end);
        }
    }
}
