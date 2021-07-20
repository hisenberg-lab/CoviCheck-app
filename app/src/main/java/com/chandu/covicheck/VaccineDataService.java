package com.chandu.covicheck;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.Request.Method.GET;

import static android.R.layout.simple_spinner_dropdown_item;

public class VaccineDataService {

    Context context;

    public VaccineDataService(Context context){
        this.context = context;
    }

    public void getVaccineByPIN(String pinSearch, String formattedDate){

        // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pinSearch+"&date="+formattedDate;

        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getVaccineByDist(int distSearch, String formattedDate){

        // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id="+distSearch+"&date="+formattedDate;

        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }


    public void getStateDist(ArrayList<StateDistModel> stateDistrictModelArrayList, ArrayList<String> names, Spinner searchBtn,int dist){

        String url = "https://cdn-api.co-vin.in/api/v2/admin/location/states";

        if (dist != 999) {
            url = "https://cdn-api.co-vin.in/api/v2/admin/location/districts/" + dist;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray data = new JSONArray();

                if(dist != 999){
                    try {
                        data = response.getJSONArray("districts");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < data.length(); i++) {
                        StateDistModel stateModel = new StateDistModel();
                        JSONObject state = null;
                        try {
                            state = data.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String id = null;
                        try {
                            id = state.getString("district_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String state_name = null;
                        try {
                            state_name = state.getString("district_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        stateModel.setID(Integer.parseInt(id));
                        stateModel.setName(state_name);

                        stateDistrictModelArrayList.add(stateModel);
                    }
                    for (int i = 0; i< stateDistrictModelArrayList.size(); i++){
                        names.add(stateDistrictModelArrayList.get(i).getName().toString());
                    }

                }
                else
                {
                    try {
                        data = response.getJSONArray("states");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < data.length(); i++) {
                        StateDistModel stateModel = new StateDistModel();
                        JSONObject state = null;
                        try {
                            state = data.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String id = null;
                        try {
                            id = state.getString("state_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String state_name = null;
                        try {
                            state_name = state.getString("state_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        stateModel.setID(Integer.parseInt(id));
                        stateModel.setName(state_name);

                        stateDistrictModelArrayList.add(stateModel);
                    }

                    for (int i = 0; i< stateDistrictModelArrayList.size(); i++){
                        names.add(stateDistrictModelArrayList.get(i).getName().toString());
                    }
                }


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, simple_spinner_dropdown_item, names);
                spinnerArrayAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
                searchBtn.setAdapter(spinnerArrayAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

        }
    }


