package com.example.MusicPlayer.Adapter;

import android.app.Activity;
import android.os.Build;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MusicPlayer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    private Set<String> mStrings = new ArraySet<>();
    private List<String> mStringList ;
    private Activity mActivity;
    public ListAdapter(List<String> stringList, Activity activity) {
        mStringList = stringList ;
        mActivity = activity ;
        mStrings.addAll(mStringList);
        mStringList.clear();
        mStringList.addAll(mStrings);
    }

    public List<String> getStrings() {
        return mStringList;
    }

    public void setStrings(List<String> strings) {
        mStringList = strings;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.album_artist_task,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
    String string = mStringList.get(position);
    holder.bindList(string);
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder{

        private TextView mTextView ;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.name);
        }
        public void bindList(String string){
            mTextView.setText(string);
        }
    }
}
