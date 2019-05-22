package com.example.learnmaps.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.learnmaps.R;
import com.example.learnmaps.models.Kategori;

import java.util.ArrayList;

public class KategoriRecyclerAdapter extends RecyclerView.Adapter<KategoriRecyclerAdapter.ViewHolder>{

    private ArrayList<Kategori> mKategori = new ArrayList<>();
    private KategoriRecyclerClickListener mKategoriRecyclerClickListener;

    public KategoriRecyclerAdapter(ArrayList<Kategori> kategoris, KategoriRecyclerClickListener kategoriRecyclerClickListener) {
        this.mKategori = kategoris;
        mKategoriRecyclerClickListener = kategoriRecyclerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kategori_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view, mKategoriRecyclerClickListener);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder)holder).kategoriTitle.setText(mKategori.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        TextView kategoriTitle;
        KategoriRecyclerClickListener clickListener;

        public ViewHolder(View itemView, KategoriRecyclerClickListener clickListener) {
            super(itemView);
            kategoriTitle = itemView.findViewById(R.id.kategori_title);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onKategoriSelected(getAdapterPosition());
        }
    }

    public interface KategoriRecyclerClickListener {

        void onKategoriSelected(int position);
    }
}