package com.example.learnmaps.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.learnmaps.R;
import com.example.learnmaps.models.Detail;
import com.example.learnmaps.models.UserPedagang;

import java.util.ArrayList;

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>{

    private ArrayList<Detail> mDetail = new ArrayList<>();
    private DetailListRecyclerClickListener mClickListener;

    public DetailRecyclerAdapter(ArrayList<Detail> details, DetailListRecyclerClickListener clickListener) {
        mDetail = details;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_list, parent, false);
        final ViewHolder holder = new ViewHolder(view, mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder)holder).deskripsi.setText(mDetail.get(position).getDeskripsi());
        ((ViewHolder)holder).nohp.setText(mDetail.get(position).getNo_hp());
    }

    @Override
    public int getItemCount() {
        return mDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        TextView deskripsi, nohp;
        DetailListRecyclerClickListener mClickListener;

        public ViewHolder(View itemView, DetailListRecyclerClickListener clickListener) {
            super(itemView);
            deskripsi = itemView.findViewById(R.id.descPedagang);
            nohp = itemView.findViewById(R.id.noPedagang);

            mClickListener = clickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onPedagangClicked(getAdapterPosition());
        }
    }

    public interface DetailListRecyclerClickListener{
        void onPedagangClicked(int position);
    }
}
