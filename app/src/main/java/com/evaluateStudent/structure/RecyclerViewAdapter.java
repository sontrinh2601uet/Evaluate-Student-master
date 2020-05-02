package com.evaluateStudent.structure;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evaluateStudent.EvaluateStudentActivity;
import com.evaluateStudent.R;
import com.evaluateStudent.fragment.StandardDetailFragment;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private FragmentManager fragmentManager;
    private Context mContext ;
    private List<Standard> mData ;


    public RecyclerViewAdapter(Context mContext, List<Standard> mData, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.standard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_standard_name.setText(mData.get(position).getContent());
        holder.img_std_thumbnail.setImageResource(mData.get(position).getImageViewId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Standard standard = mData.get(position);
                ((EvaluateStudentActivity) mContext).changeStatus();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_container, StandardDetailFragment.createInstance(ConnectToData.listStandard.indexOf(standard)), "detail")
                        .addToBackStack("list")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_standard_name;
        ImageView img_std_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_standard_name = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_std_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }


}
