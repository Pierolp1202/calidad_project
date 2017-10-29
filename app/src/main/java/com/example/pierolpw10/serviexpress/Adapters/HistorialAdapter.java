package com.example.pierolpw10.serviexpress.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pierolpw10.serviexpress.Models.Work;
import com.example.pierolpw10.serviexpress.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NriKe on 29/10/2017.
 */

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.MyHolder> {

    List<Work> data = new ArrayList<>();
    private final LayoutInflater inflater;
    private final Context context;

    public HistorialAdapter(List<Work> data, Context context){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_historial;
        View view = inflater.inflate(layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String work = "Trabajo de ";
        switch (data.get(position).getWork()){
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
        holder.tv_work.setText(work);

        holder.tv_worker.setText("Sr(a): " + data.get(position).getWorker());

        holder.tv_date.setText(data.get(position).getDate());

        holder.rating.setRating(data.get(position).getRating());

        Picasso.with(context).load(data.get(position).getWorker_image()).into(holder.worker_image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_work;
        TextView tv_worker;
        TextView tv_date;
        RatingBar rating;
        CircleImageView worker_image;

        public MyHolder(View itemView) {
            super(itemView);

            tv_work = (TextView) itemView.findViewById(R.id.tv_work);
            tv_worker = (TextView) itemView.findViewById(R.id.tv_worker);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            worker_image = (CircleImageView) itemView.findViewById(R.id.worker_image);
        }
    }
}
