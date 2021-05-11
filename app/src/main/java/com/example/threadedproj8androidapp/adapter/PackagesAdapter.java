package com.example.threadedproj8androidapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.example.threadedproj8androidapp.model.PackageEntity;
import com.example.threadedproj8androidapp.util.PurchaseActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder> {
    ArrayList<PackageEntity> packages;
    CustomerEntity customer = new CustomerEntity();

    public PackagesAdapter(ArrayList<PackageEntity> packages, CustomerEntity customer)
        {this.packages = packages;
        this.customer = customer;
        }
    @NonNull
    @Override
    public PackagesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_list_item, parent, false);
        PackagesViewHolder viewHolder = new PackagesViewHolder(view);
        viewHolder.customer = customer;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull PackagesViewHolder holder, int position) {
        PackageEntity packageEntity = packages.get(position);
        holder.lblPackageDescription.setText(packageEntity.getPkgDesc());
        holder.lblPackageStartDate.setText(String.valueOf(packageEntity.getPkgStartDate()));
        holder.packageEntity = packageEntity;
        holder.position = position;
    }
    public CustomerEntity getCustomer() {
        return customer;
    }

    @Override
    public int getItemCount() {
        return this.packages.size();
    }

    public static class PackagesViewHolder extends RecyclerView.ViewHolder {
        TextView lblPackageDescription;
        TextView lblPackageStartDate;
        View rootView;
        Spinner ddlNoOfTravelers;
        Integer[] travellers = new Integer[]{0, 1, 2, 3, 4, 5};
        int position;
        PackageEntity packageEntity;
        CustomerEntity customer;

        public PackagesViewHolder(@NotNull View itemView) {
            super(itemView);
            rootView = itemView;
            lblPackageDescription = itemView.findViewById(R.id.lblPackageDescription);
            lblPackageStartDate = itemView.findViewById(R.id.lblPackageStartDate);
            ddlNoOfTravelers = itemView.findViewById(R.id.ddlNoOfTravellers);
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(itemView.getContext(), android.R.layout.simple_spinner_dropdown_item, travellers);
            ddlNoOfTravelers.setAdapter(adapter);
            itemView.findViewById(R.id.btnPurchase).setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Integer numberOfTravellers = (Integer) ddlNoOfTravelers.getSelectedItem();
                    if (numberOfTravellers.toString() != "0")
                    {
                        Intent intent = new Intent(itemView.getContext(), PurchaseActivity.class);
                        intent.putExtra("package", (Serializable) packageEntity);
                        intent.putExtra("customer", customer);
                        intent.putExtra("numberOfTravellers", numberOfTravellers);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        itemView.getContext().startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(itemView.getContext(), "You much travel with at least one traveller!", Toast.LENGTH_LONG);
                    }
                }
            });
        }
    }
}