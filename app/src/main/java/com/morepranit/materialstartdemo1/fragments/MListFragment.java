package com.morepranit.materialstartdemo1.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.morepranit.materialstartdemo1.AppController;
import com.morepranit.materialstartdemo1.R;
import com.morepranit.materialstartdemo1.adapter.PhotoAdapter;
import com.morepranit.materialstartdemo1.models.PhotoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MListFragment extends Fragment {
    private ArrayList<PhotoModel> photoModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PhotoAdapter(getContext(), photoModels);
        recyclerView.setAdapter(adapter);

        getPhotos();

        return view;
    }

    private void getPhotos() {
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=ff2c27b56a8c4af4861d4f4124d7668d&per_page=100&page=1&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            parseJSON(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSON(JSONObject jsonObject) throws JSONException {
        JSONObject photosObject = jsonObject.getJSONObject("photos");
        JSONArray photosArray = photosObject.getJSONArray("photo");

        for (int i = 0; i < photosArray.length(); i++) {
            JSONObject photoObject = photosArray.getJSONObject(i);

            String id = photoObject.has("id") ? photoObject.getString("id") : "";
            String secret = photoObject.has("secret") ? photoObject.getString("secret") : "";
            String server = photoObject.has("server") ? photoObject.getString("server") : "";
            String title = photoObject.has("title") ? photoObject.getString("title") : "";
            int farm = photoObject.has("farm") ? photoObject.getInt("farm") : 0;

            String photoUrl = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_q.jpg";

            PhotoModel model = new PhotoModel();
            model.setId(id);
            model.setTitle(title.length() > 10 ? title.substring(0, 10) : title);
            model.setUrl(photoUrl);
            Log.e("parseJSON: ", model.getTitle());

            photoModels.add(model);
        }
        adapter.notifyDataSetChanged();
    }
}
