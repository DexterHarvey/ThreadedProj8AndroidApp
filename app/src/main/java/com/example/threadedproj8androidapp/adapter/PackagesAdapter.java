package com.example.threadedproj8androidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.FormatHelper;
import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.example.threadedproj8androidapp.model.PackageEntity;
import com.example.threadedproj8androidapp.util.PurchaseActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Defines behaviour for PackageActivity's Recyclerview and its items.
 * Majority of code laid down by Dexter.
 * Additions to allow for extra onClick functionality and format date/ currency data by Eric.
 */


public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder> {
    ArrayList<PackageEntity> packages;
    CustomerEntity customer = new CustomerEntity();
    private Context context;
    private PackagesViewHolder viewHolder;

    //declare interface to allow for onItemClick behaviour to be coded
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // Cnostructor
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

    // As the viewholder is bound, set values for its individual items
    @Override
    public void onBindViewHolder(@NotNull PackagesViewHolder holder, int position) {
        PackageEntity packageEntity = packages.get(position);
        holder.lblPackageName.setText(packageEntity.getPkgName());
        holder.lblPackageDescription.setText(packageEntity.getPkgDesc());
        holder.lblPkgPrice.setText(FormatHelper.getNiceMoneyFormat(packageEntity.getPkgBasePrice()));

        // Do some date formatting
        String startDate = FormatHelper.getNiceDateFormat(
                packageEntity.getPkgStartDate());
        String endDate = FormatHelper.getNiceDateFormat(
                packageEntity.getPkgEndDate());
        holder.lblPackageStartDate.setText(startDate + " - " + endDate);

        holder.packageEntity = packageEntity;
        holder.position = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    public ArrayList<PackageEntity> getPackages() {
        return packages;
    }

    @Override
    public int getItemCount() {
        return this.packages.size();
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

    // Viewholder for the recyclerview [almost all Dexter]
    public class PackagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView lblPackageDescription;
        TextView lblPackageStartDate;
        TextView lblPackageName;
        TextView lblPkgPrice;
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
            lblPkgPrice = itemView.findViewById(R.id.lblPkgPrice);
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
