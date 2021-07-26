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
import java.util.List;

import static com.android.volley.Request.Method.GET;

import static android.R.layout.simple_spinner_dropdown_item;

public class VaccineDataService {

    Context context;

    public VaccineDataService(Context context) {
        this.context = context;
    }

    public  VaccineDataService(){}


    public interface VaccineByPIN {
        void onError(String message);

        void onResponse(List<VaccineSlotModel> vaccineSlotModels);
    }
    public void getVaccineByPIN(String pinSearch, String formattedDate, VaccineByPIN vaccineByPIN) {

        // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        List<VaccineSlotModel> slots = new ArrayList<>();

        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + pinSearch + "&date=" + formattedDate;

        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray sessions_list = response.getJSONArray("sessions");

                    JSONObject single_center_from_api;
                    for(int i=0; i < sessions_list.length(); i++) {

                        single_center_from_api = (JSONObject) sessions_list.get(i);
                        VaccineSlotModel single_center = new VaccineSlotModel();

                        single_center.setCenter_id(Integer.parseInt(single_center_from_api.getString("center_id")));
                        single_center.setName(single_center_from_api.getString("name"));
                        single_center.setAddress(single_center_from_api.getString("address"));
                        single_center.setState_name(single_center_from_api.getString("state_name"));
                        single_center.setDistrict_name(single_center_from_api.getString("district_name"));
                        single_center.setBlock_name(single_center_from_api.getString("block_name"));
                        single_center.setPincode(Integer.parseInt(single_center_from_api.getString("pincode")));
                        single_center.setFrom(single_center_from_api.getString("from"));
                        single_center.setTo(single_center_from_api.getString("to"));
                        single_center.setLat(Integer.parseInt(single_center_from_api.getString("lat")));
                        single_center.setLongi(Integer.parseInt(single_center_from_api.getString("long")));
                        single_center.setFee_type(single_center_from_api.getString("fee_type"));
                        single_center.setSession_id(single_center_from_api.getString("session_id"));
                        single_center.setDate(single_center_from_api.getString("date"));
                        single_center.setAvailable_capacity(Integer.parseInt(single_center_from_api.getString("available_capacity")));
                        single_center.setAvailable_capacity_dose1(Integer.parseInt(single_center_from_api.getString("available_capacity_dose1")));
                        single_center.setAvailable_capacity_dose2(Integer.parseInt(single_center_from_api.getString("available_capacity_dose2")));
                        if(!single_center_from_api.isNull("fee")) {
                            single_center.setFee(single_center_from_api.getString("fee"));
                        }
                        if(!single_center_from_api.isNull("min_age_limit")) {
                            single_center.setMin_age_limit(Integer.parseInt(single_center_from_api.getString("min_age_limit")));
                        }
                        if(!single_center_from_api.isNull("max_age_limit")){
                            single_center.setMax_age_limit(Integer.parseInt(single_center_from_api.getString("max_age_limit")));
                        }
                        if(!single_center_from_api.isNull("allow_all_age")) {
                            single_center.setAllow_all_age(single_center_from_api.getString("allow_all_age"));
                        }
                        single_center.setVaccine(single_center_from_api.getString("vaccine"));
//                    if(!single_center_from_api.isNull("slot")) {
//                        first_center.setSlots(single_center_from_api.getJSONObject("slots"));
//                      }

                        slots.add(single_center);
                    }


                    vaccineByPIN.onResponse(slots);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


    public interface VaccineByDist {
        void onError(String message);

        void onResponse(List<VaccineSlotModel> vaccineSlotModels);
    }
    public void getVaccineByDist(int distSearch, String formattedDate, VaccineByDist vaccineByDist) {

        // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        List<VaccineSlotModel> slots = new ArrayList<>();
        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=" + distSearch + "&date=" + formattedDate;

        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray sessions_list = response.getJSONArray("sessions");

                    JSONObject single_center_from_api;
                    for(int i=0; i < sessions_list.length(); i++) {

                        single_center_from_api = (JSONObject) sessions_list.get(i);
                        VaccineSlotModel single_center = new VaccineSlotModel();

                        single_center.setCenter_id(Integer.parseInt(single_center_from_api.getString("center_id")));
                        single_center.setName(single_center_from_api.getString("name"));
                        single_center.setAddress(single_center_from_api.getString("address"));
                        single_center.setState_name(single_center_from_api.getString("state_name"));
                        single_center.setDistrict_name(single_center_from_api.getString("district_name"));
                        single_center.setBlock_name(single_center_from_api.getString("block_name"));
                        single_center.setPincode(Integer.parseInt(single_center_from_api.getString("pincode")));
                        single_center.setFrom(single_center_from_api.getString("from"));
                        single_center.setTo(single_center_from_api.getString("to"));
                        single_center.setLat(Integer.parseInt(single_center_from_api.getString("lat")));
                        single_center.setLongi(Integer.parseInt(single_center_from_api.getString("long")));
                        single_center.setFee_type(single_center_from_api.getString("fee_type"));
                        single_center.setSession_id(single_center_from_api.getString("session_id"));
                        single_center.setDate(single_center_from_api.getString("date"));
                        single_center.setAvailable_capacity(Integer.parseInt(single_center_from_api.getString("available_capacity")));
                        single_center.setAvailable_capacity_dose1(Integer.parseInt(single_center_from_api.getString("available_capacity_dose1")));
                        single_center.setAvailable_capacity_dose2(Integer.parseInt(single_center_from_api.getString("available_capacity_dose2")));
                        if(!single_center_from_api.isNull("fee")) {
                            single_center.setFee(single_center_from_api.getString("fee"));
                        }
                        if(!single_center_from_api.isNull("min_age_limit")) {
                            single_center.setMin_age_limit(Integer.parseInt(single_center_from_api.getString("min_age_limit")));
                        }
                        if(!single_center_from_api.isNull("max_age_limit")){
                            single_center.setMax_age_limit(Integer.parseInt(single_center_from_api.getString("max_age_limit")));
                        }
                        if(!single_center_from_api.isNull("allow_all_age")) {
                            single_center.setAllow_all_age(single_center_from_api.getString("allow_all_age"));
                        }
                        single_center.setVaccine(single_center_from_api.getString("vaccine"));
//                    if(!single_center_from_api.isNull("slot")) {
//                        first_center.setSlots(single_center_from_api.getJSONObject("slots"));
//                      }

                        slots.add(single_center);
                    }


                    vaccineByDist.onResponse(slots);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }


    public void getStateDist(ArrayList<StateDistModel> stateDistrictModelArrayList, ArrayList<String> names, Spinner searchBtn, int dist) {

        String url = "https://cdn-api.co-vin.in/api/v2/admin/location/states";

        if (dist != 999) {
            url = "https://cdn-api.co-vin.in/api/v2/admin/location/districts/" + dist;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray data = new JSONArray();

                if (dist != 999) {
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
                    for (int i = 0; i < stateDistrictModelArrayList.size(); i++) {
                        names.add(stateDistrictModelArrayList.get(i).getName().toString());
                    }

                } else {
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

                    for (int i = 0; i < stateDistrictModelArrayList.size(); i++) {
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

//
//        public void getVaccineSlotsByPIN(String pinSearch, String formattedDate) {
//
//        List<VaccineSlotModel> slots = new ArrayList<>();
//
//            String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pinSearch+"&date="+formattedDate;
//
//        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//            MySingleton.getInstance(context).addToRequestQueue(request);
//
//        }


