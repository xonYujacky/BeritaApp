package com.dc.artikel.Adapter;

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

import com.dc.artikel.Detail;
import com.dc.artikel.Model.Artikel;
import com.dc.artikel.R;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    //Context
    Context context;
    List<Artikel> artikels;

    public Adapter(Context context, List<Artikel> artikels) {
        this.context = context;
        this.artikels = artikels;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        final Artikel a = artikels.get(position);

        holder.title_tv.setText(a.getTitle());
        holder.source_tv.setText(a.getSource().getName());
        holder.date_tv.setText("\u2022"+dateTime(a.getPublishedAt()));

        String imageUrl = a.getUrlToImage();
        String url = a.getUrl();
        Picasso.with(context).load(imageUrl).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("title",a.getTitle());
                intent.putExtra("source",a.getSource().getName());
                intent.putExtra("time",dateTime(a.getPublishedAt()));
                intent.putExtra("desc",a.getDescription());
                intent.putExtra("imageUrl",a.getUrlToImage());
                intent.putExtra("url",a.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return artikels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_tv,source_tv,date_tv;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_tv = itemView.findViewById(R.id.title_tv);
            source_tv = itemView.findViewById(R.id.source_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            imageView = itemView.findViewById(R.id.image_item);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale((getCountry())));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);

        }catch (ParseException e){
            e.printStackTrace();
        }
        return time;

    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }



}
