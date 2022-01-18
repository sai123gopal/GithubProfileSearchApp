package com.saigopal.githubprofil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.view>
{

    private final ArrayList<ProfileModel> data;
    private final Context context;

    public recycleViewAdapter(ArrayList<ProfileModel> data,Context context){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public view onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_items,parent,false);
        return new view(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull view holder, @SuppressLint("RecyclerView") final int position)
    {
        holder.UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MoreDetails(data.get(position).URL,context).execute();
            }
        });
        holder.userID.setText(data.get(position).UserID);
        try{
            Picasso.get()
                    .load(data.get(position).AvatarURL)
                    .into(holder.UserImage);
        }
        catch(Exception e) {
            Log.d("Exception Picasso : ", e+"");
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class view extends RecyclerView.ViewHolder {
        ImageView UserImage;
        TextView userID;
        view(@NonNull View itemView) {
            super(itemView);
            UserImage = itemView.findViewById(R.id.avatar_img);
            userID = itemView.findViewById(R.id.userId);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class MoreDetails extends AsyncTask<String, Void, String> {
        String Url;
        Context context;
        public MoreDetails(String url,Context context) {
            Url = url;
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            return Functions.Get(Url);
        }

        @Override
        protected void onPostExecute(String xml) {
            try {
                JSONObject jsonResponse = new JSONObject(xml);
                Dialog(jsonResponse.optString("avatar_url"),jsonResponse.optString("login"),
                        jsonResponse.optString("name"),jsonResponse.optString("followers"),
                        jsonResponse.optString("following"),jsonResponse.optString("bio"),
                        jsonResponse.optString("location"),jsonResponse.optString("public_repos"),
                        jsonResponse.optString("html_url")
                );
            }
            catch (JSONException ex){
                Log.d("JsonException : ", ex + "");
            } catch (Exception e) {
                Log.d("Exception : ", e + "");
            }

        }

    }

    @SuppressLint("SetTextI18n")
    private void Dialog(String ImageURl, String UserId, String name, String followers, String following,
                        String bio, String location, String repos, final String url) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.more_details_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView UserID = dialog.findViewById(R.id.user_id);
        TextView UserName = dialog.findViewById(R.id.name);
        TextView Email = dialog.findViewById(R.id.email);
        TextView Bio = dialog.findViewById(R.id.bio);
        TextView Repos = dialog.findViewById(R.id.public_repos);
        TextView Followers = dialog.findViewById(R.id.followers_number);
        TextView Following = dialog.findViewById(R.id.following_number);
        ImageView Profile = dialog.findViewById(R.id.profile_image);
        Button Open = dialog.findViewById(R.id.open);
        ImageButton close = dialog.findViewById(R.id.close_btn);

        Picasso.get().load(ImageURl).into(Profile);

        UserID.setText(UserId);
        UserName.setText(name);
        Email.setText("Location : "+location);
        Bio.setText("Bio : "+bio);
        Repos.setText("Public Repos : "+repos);
        Followers.setText(followers);
        Following.setText(following);

        Open.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));

        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }



}
