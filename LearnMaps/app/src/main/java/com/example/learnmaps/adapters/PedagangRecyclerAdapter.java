package com.example.learnmaps.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.learnmaps.R;
import com.example.learnmaps.models.User;
import com.example.learnmaps.models.UserPedagang;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PedagangRecyclerAdapter extends RecyclerView.Adapter<PedagangRecyclerAdapter.ViewHolder>{

    private ArrayList<UserPedagang> mPedagangs = new ArrayList<>();
    private PedagangListRecyclerClickListener mClickListener;

    public PedagangRecyclerAdapter(ArrayList<UserPedagang> pedagangs, PedagangListRecyclerClickListener clickListener) {
        mPedagangs = pedagangs;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pedagang_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view, mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder)holder).username.setText(mPedagangs.get(position).getUsername());
        ((ViewHolder)holder).email.setText(mPedagangs.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mPedagangs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        TextView username, email;
        PedagangListRecyclerClickListener mClickListener;

        public ViewHolder(View itemView, PedagangListRecyclerClickListener clickListener) {
            super(itemView);
            username = itemView.findViewById(R.id.usernamePedagang);
            email = itemView.findViewById(R.id.emailPedagang);

            mClickListener = clickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onUserPedagangClicked(getAdapterPosition());
        }
    }

    public interface PedagangListRecyclerClickListener{
        void onUserPedagangClicked(int position);
    }
}
















