package com.chandu.covicheck;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatewiseDataService {

    Context context;

    public StatewiseDataService(Context context) {
        this.context = context;
    }

    public interface Cases {
        void onError(String message);

        void onResponse(StatewiseModel statewiseModel);
    }
    public void getCases(Cases cases) {

//        List<StatewiseModel> states = new ArrayList<>();

        String covidUrl = "https://api.covid19india.org/data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, covidUrl,  null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray statewise_list = null;
                try {
                    statewise_list = response.getJSONArray("statewise");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject state_from_api = null;
                try {
                    state_from_api = (JSONObject) statewise_list.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StatewiseModel state = new StatewiseModel();

                try {
                    state.setActive(state_from_api.getString("active"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setConfirmed(state_from_api.getString("confirmed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setDeaths(state_from_api.getString("deaths"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setDeltaconfirmed(state_from_api.getString("deltaconfirmed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setDeltarecovered(state_from_api.getString("deltarecovered"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setDeltadeaths(state_from_api.getString("deltadeaths"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setLastupdatedtime(state_from_api.getString("lastupdatedtime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setMigratedother(state_from_api.getString("migratedother"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setRecovered(state_from_api.getString("recovered"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setState(state_from_api.getString("state"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setStatecode(state_from_api.getString("statecode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setStatecode(state_from_api.getString("statecode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state.setStatecode(state_from_api.getString("statenotes"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                states.add(state);
                cases.onResponse(state);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
