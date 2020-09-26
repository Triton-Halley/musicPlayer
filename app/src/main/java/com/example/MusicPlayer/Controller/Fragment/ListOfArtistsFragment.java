package com.example.MusicPlayer.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MusicPlayer.Adapter.ListAdapter;
import com.example.MusicPlayer.R;
import com.example.MusicPlayer.Repository.MusicList;

import java.util.ArrayList;
import java.util.List;

public class ListOfArtistsFragment extends Fragment {
    private RecyclerView mRecyclerView ;
    private MusicList mRepository ;
    private ListAdapter mAdapter ;
    private static String mListType;
    public ListOfArtistsFragment() {
        // Required empty public constructor
    }

    public static ListOfArtistsFragment newInstance(String type) {
        ListOfArtistsFragment fragment = new ListOfArtistsFragment();
        Bundle args = new Bundle();
        args.putString("ARGS_TYPE",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository =MusicList.getInstance(getActivity());
        assert getArguments() != null;
        mListType = getArguments().getString("ARGS_TYPE");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_list_of_artists, container, false);
        findView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view ;
    }
    private void findView(View view){
        mRecyclerView = view.findViewById(R.id.recyclerview_artist);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateUI(){
        List<String> Artists =returnList();

        if (mAdapter == null){
            mAdapter = new ListAdapter(Artists,getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setStrings(Artists);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private List<String> returnList() {
        if (mListType.equalsIgnoreCase("Artist")){
            List<String> info = mRepository.getArtistList();
            return info;
        }else if (mListType.equalsIgnoreCase("Album")){
            List<String> info = mRepository.getAlbums();
            return info ;
        }else {
            return new ArrayList<String>();
        }
    }
}