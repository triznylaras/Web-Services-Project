package com.example.triznylarasati.retrofit;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Users.UserItem> users = new ArrayList<>();
    private int rowLayout;
    private Context simpan;

    //
    public MyAdapter(Context simpan) {
        super();
        this.simpan = simpan;
    }

    //
    public void add(final Users.UserItem users){
        this.users.add(users); //masukin list lagi
        notifyItemInserted(this.users.size());
    }

    //
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_content_product,
                        parent, false));
    }

    //
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindPostText(holder, position);
    }
    private void bindPostText(ViewHolder holder, int position) {
        holder.tv_id.setText(Integer.toString(users.get(position).getId()));
        holder.tv_email.setText(users.get(position).getEmail());
        holder.tv_password.setText(users.get(position).getPassword());
        holder.tv_tokenauth.setText(users.get(position).getToken_auth());
    }

    //
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_id, tv_email, tv_password, tv_tokenauth;

        public ViewHolder(View v) {
            super(v);
            tv_id = (TextView) v.findViewById(R.id.tv_id);
            tv_email = (TextView) v.findViewById(R.id.tv_email);
            tv_password = (TextView) v.findViewById(R.id.tv_password);
            tv_tokenauth = (TextView) v.findViewById(R.id.tv_tokenauth);
        }
    }
}