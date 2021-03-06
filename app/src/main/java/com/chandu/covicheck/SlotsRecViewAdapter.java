package com.chandu.covicheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlotsRecViewAdapter extends RecyclerView.Adapter<SlotsRecViewAdapter.ViewHolder>{

    private List<VaccineSlotModel> slots = new ArrayList<>();

    public SlotsRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slots_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SlotsRecViewAdapter.ViewHolder holder, int position) {
        holder.name.setText(slots.get(position).getName());
        holder.address.setText(slots.get(position).getAddress());
        holder.date.setText(slots.get(position).getDate());
        holder.feeType.setText(slots.get(position).getFee_type());
        holder.fee.setText("Rs "+slots.get(position).getFee()+"/-");
        if(slots.get(position).getFee() == null){
            holder.fee.setText("");

        }
        holder.vaccine.setText(slots.get(position).getVaccine());
        holder.doseOne.setText("D1 "+String.valueOf(slots.get(position).getAvailable_capacity_dose1()));
        holder.doseTwo.setText("D2 "+String.valueOf(slots.get(position).getAvailable_capacity_dose2()));
        holder.doseTotal.setText("T "+String.valueOf(slots.get(position).getAvailable_capacity()));
        if(slots.get(position).getAvailable_capacity() == 0)
        {
            holder.doseTotal.setText(" Booked ");
            holder.doseTotal.setBackgroundResource(R.color.redBackground);

        }
        else {
            holder.doseTotal.setBackgroundResource(R.color.green_a700);
        }
        holder.age.setText(String.valueOf(slots.get(position).getMin_age_limit())+" - "+String.valueOf(slots.get(position).getMax_age_limit()));

        if(slots.get(position).getMin_age_limit() == 0){
            holder.age.setText(String.valueOf(slots.get(position).getMax_age_limit())+ " & Below");
        }
        else if(slots.get(position).getMax_age_limit() == 0){
            holder.age.setText(String.valueOf(slots.get(position).getMin_age_limit())+ " & Above");
        }
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public void setSlots(List<VaccineSlotModel> slots) {
        this.slots = slots;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,address,date,feeType,fee,vaccine,doseOne,doseTotal,doseTwo,age;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            feeType = itemView.findViewById(R.id.feeType);
            fee = itemView.findViewById(R.id.fee);
            vaccine = itemView.findViewById(R.id.vaccine);
            doseOne = itemView.findViewById(R.id.doseOne);
            doseTotal = itemView.findViewById(R.id.doseTotal);
            doseTwo = itemView.findViewById(R.id.doseTwo);
            age = itemView.findViewById(R.id.age);
        }
    }
}
