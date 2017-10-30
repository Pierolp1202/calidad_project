package com.example.pierolpw10.serviexpress.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NriKe on 29/10/2017.
 */

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.MyHolder> {

    List<Work> data_ls = new ArrayList<>();
    private final LayoutInflater inflater;
    private final Context context;
    Dialog dialog;
    FirebaseManager manager;
    PreferenceManager p_manager;

    public HistorialAdapter(List<Work> data, Context context){
        this.context = context;
        this.data_ls = data;
        inflater = LayoutInflater.from(context);
        manager = new FirebaseManager();
        p_manager = PreferenceManager.getInstance(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_historial;
        View view = inflater.inflate(layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        String work = "Trabajo de ";
        switch (data_ls.get(position).getWork()){
            case 1:
                work += "Gasfitería";
                break;
            case 2:
                work = "Electricista";
                break;
            case 3:
                work += "Cerrajería";
                break;
            case 4:
                work += "Carpintería";
                break;
            case 5:
                work += "Jardinería";
                break;
            case 6:
                work += "";
                break;
        }

        switch (data_ls.get(position).getWorker()){
            case "Jorge Gonzales":
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker1));
                break;
            case "Willy Wonka":
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker2));
                break;
            case "Sheldon Cooper":
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker3));
                break;
            case "Barry Allen":
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker4));
                break;
            case "Slade Wilson":
                holder.worker_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.worker5));
                break;
        }

        holder.tv_work.setText(work);

        holder.tv_worker.setText("Sr(a): " + data_ls.get(position).getWorker());

        holder.tv_date.setText(data_ls.get(position).getDate());

        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF) != null) {
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data_ls.get(position).getWorker().equals(data.child("worker").getValue(String.class)) && data_ls.get(position).getDate().equals(data.child("date").getValue(String.class))) {
                            Long rate_long = (Long) data.child("work_rate").getValue();
                            int rate = rate_long.intValue();
                            holder.rating.setRating(rate);
                            cambiarEstadoWorker(data.child("worker").getValue(String.class));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        holder.cv_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verifyRated(position);
            }
        });
    }

    private void verifyRated(final int position) {
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF) != null) {
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if(data.child("username").getValue(String.class).equals(p_manager.getPreferenceUsername()) && data_ls.get(position).getDate().equals(data.child("date").getValue(String.class))){
                            if(!(Boolean) data.child("rated").getValue()){
                                openDialog(data_ls.get(position).getWorker(),position);
                            }else{
                                Toast.makeText(context,"Ya puntuaste este trabajo",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void cambiarEstadoWorker(String username) {
        DatabaseReference ref = manager.getWorkersReference();

        switch (username){
            case "Jorge Gonzales":
                ref.child("worker1").child("working").setValue(false);
                break;
            case "Willy Wonka":
                ref.child("worker2").child("working").setValue(false);
                break;
            case "Sheldon Cooper":
                ref.child("worker3").child("working").setValue(false);
                break;
            case "Barry Allen":
                ref.child("worker4").child("working").setValue(false);
                break;
            case "Slade Wilson":
                ref.child("worker5").child("working").setValue(false);
                break;
        }
    }

    private void openDialog(final String username, final int position){
        dialog = new Dialog(context, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rate_dialog);

        Button btn_accept = (Button) dialog.findViewById(R.id.bt_accept);
        Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);
        final RatingBar rb = (RatingBar) dialog.findViewById(R.id.rb);
        
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(rb.getRating(),username,position);
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void setRating(final float rating, final String username, final int position) {
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF) != null) {
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        if(username.equals(data.child("worker").getValue(String.class)) && data_ls.get(position).getDate().equals(data.child("date").getValue(String.class))){
                            String k = data.getKey();
                            DatabaseReference ref = manager.getWorksReference();
                            ref.child(k).child("work_rate").setValue(rating);
                            cambiarEstadoRated(username,position);
                            dialog.dismiss();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void cambiarEstadoRated(final String username, final int position) {

        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF) != null) {
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORK_REF).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        if(data.child("worker").getValue(String.class).equals(username) && data_ls.get(position).getDate().equals(data.child("date").getValue(String.class))){
                            String k = data.getKey();
                            DatabaseReference ref = manager.getWorksReference();
                            ref.child(k).child("rated").setValue(true);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return data_ls.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_work;
        TextView tv_worker;
        TextView tv_date;
        RatingBar rating;
        CircleImageView worker_image;
        CardView cv_work;

        public MyHolder(View itemView) {
            super(itemView);
            cv_work = (CardView) itemView.findViewById(R.id.cv_work);
            tv_work = (TextView) itemView.findViewById(R.id.tv_work);
            tv_worker = (TextView) itemView.findViewById(R.id.tv_worker);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            worker_image = (CircleImageView) itemView.findViewById(R.id.worker_image);
        }
    }
}
