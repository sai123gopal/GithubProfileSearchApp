package com.saigopal.githubprofil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.view>
{

    private ArrayList<ProfileModel> data;
    public recycleViewAdapter(ArrayList<ProfileModel> data){
        this.data = data;
    }

    @NonNull
    @Override
    public view onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_items,parent,false);
        return new view(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull view holder, int position)
    {

        try{
            holder.userID.setText(data.get(position).UserID);
                Picasso.get()
                        .load(data.get(position).AvatarURL)
                        .into(holder.UserImage);
        }
        catch(Exception e)
        {
            Log.d("Exception Picasso : ", e+"");
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class view extends RecyclerView.ViewHolder
    {
        ImageView UserImage;
        TextView userID;
        view(@NonNull View itemView)
        {
            super(itemView);

            UserImage = itemView.findViewById(R.id.avatar_img);
             userID = itemView.findViewById(R.id.userId);


        }
    }
}
