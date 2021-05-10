package com.example.threadedproj8androidapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.R;

import java.util.ArrayList;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.BookingDetailsViewHolder> {
    private ArrayList<BookingDetailsEntity> bookingDetails = new ArrayList<>();

    @NonNull
    @Override
    public BookingDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_list_item, parent, false);
        return new BookingDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailsViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return bookingDetails.size();
    }

    public void updateData(ArrayList<BookingDetailsEntity> items) {
        bookingDetails = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    private BookingDetailsEntity getItem(int position) {
        if (position >= 0 && position < bookingDetails.size())
            return bookingDetails.get(position);

        return null;
    }

    class BookingDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView destination;
        TextView description;

        BookingDetailsViewHolder(View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destination);
            description = itemView.findViewById(R.id.description);
        }

        void bindData(BookingDetailsEntity bookingDetailsEntity) {
            if (bookingDetailsEntity == null)
                return;

            destination.setText(bookingDetailsEntity.getDestination());
            description.setText(bookingDetailsEntity.getDescription());
        }
    }
}