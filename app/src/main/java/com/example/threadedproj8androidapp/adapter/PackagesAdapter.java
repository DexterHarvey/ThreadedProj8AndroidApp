package com.example.threadedproj8androidapp.adapter;

import android.content.Context;
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
    private Context context;

    //declare interface
    private OnItemClicked onClick;
    private PackagesViewHolder viewHolder;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }



    public PackagesAdapter(Context context, ArrayList<PackageEntity> packages, CustomerEntity customer)
        {
        this.context = context;
        this.packages = packages;
        this.customer = customer;
        }
    @NonNull
    @Override
    public PackagesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_list_item, parent, false);
        viewHolder = new PackagesViewHolder(view, onClick);
        viewHolder.customer = customer;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull PackagesViewHolder holder, int position) {
        PackageEntity packageEntity = packages.get(position);
        holder.lblPackageName.setText(packageEntity.getPkgName());
        holder.lblPackageDescription.setText(packageEntity.getPkgDesc());

        // Do some date formatting
        String startDate = packageEntity.getNiceDateFormat(
                packageEntity.getPkgStartDate(), context.getApplicationContext());
        String endDate = packageEntity.getNiceDateFormat(
                packageEntity.getPkgEndDate(), context.getApplicationContext());
        holder.lblPackageStartDate.setText("Runs from " + startDate + " - " + endDate);

        holder.packageEntity = packageEntity;
        holder.position = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }
    public CustomerEntity getCustomer() {
        return customer;
    }

    public ArrayList<PackageEntity> getPackages() {
        return packages;
    }

    @Override
    public int getItemCount() {
        return this.packages.size();
    }

    public PackagesViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

    public class PackagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView lblPackageDescription;
        TextView lblPackageStartDate;
        TextView lblPackageName;
        View rootView;
        Integer[] travellers = new Integer[]{0, 1, 2, 3, 4, 5};
        int position;
        PackageEntity packageEntity;
        CustomerEntity customer;
        OnItemClicked onItemClicked;

        public PackagesViewHolder(@NotNull View itemView, OnItemClicked onItmCLk) {
            super(itemView);
            rootView = itemView;
            lblPackageName = itemView.findViewById(R.id.lblPackageName);
            lblPackageDescription = itemView.findViewById(R.id.lblPackageDescription);
            lblPackageStartDate = itemView.findViewById(R.id.lblPackageStartDate);
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(itemView.getContext(), android.R.layout.simple_spinner_dropdown_item, travellers);
            onItemClicked = onItmCLk;
            itemView.findViewById(R.id.btnPurchase).setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), PurchaseActivity.class);
                        intent.putExtra("package", (Serializable) packageEntity);
                        intent.putExtra("customer", customer);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        itemView.getContext().startActivity(intent);
                }
            });

        }

        public View getRootView() {
            return rootView;
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onItemClick(position);
        }
    }
}
