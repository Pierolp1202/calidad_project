package com.example.pierolpw10.serviexpress.Menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pierolpw10.serviexpress.R;

import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<NavDrawerItem> data;
    final private LayoutInflater inflater;
    final private Context context;
    private static OnItemClickListener clickListener;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv_icon.setImageResource(data.get(position).getResourceId());
        holder.tv_text.setText(data.get(position).getTitle().toString());
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_text;
        ImageView iv_icon;

        public MyViewHolder(View itemView , int viewType) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition(),v);
            }
        }
    }

}
