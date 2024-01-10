package com.example.myapplication9777;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> datalist;

    public MyAdapter(Context context, List<DataClass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(datalist.get(position).getDataImage()).into(holder.recImage);
        holder.recName.setText(datalist.get(position).getStdName());
        holder.recRegNo.setText(datalist.get(position).getStdRegNo());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", datalist.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Name", datalist.get(holder.getAdapterPosition()).getStdName());
                intent.putExtra("RegNo", datalist.get(holder.getAdapterPosition()).getStdRegNo());
                intent.putExtra("Age", datalist.get(holder.getAdapterPosition()).getStdAge());
                intent.putExtra("Gender", datalist.get(holder.getAdapterPosition()).getStdGender());
                intent.putExtra("ContactNo", datalist.get(holder.getAdapterPosition()).getStdTeleNo());
                intent.putExtra("ParentNo", datalist.get(holder.getAdapterPosition()).getStdParTeleNo());
                intent.putExtra("Key", datalist.get(holder.getAdapterPosition()).getKey());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        datalist = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recRegNo, recName;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recRegNo = itemView.findViewById(R.id.recRegNo);
        recName = itemView.findViewById(R.id.recName);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
