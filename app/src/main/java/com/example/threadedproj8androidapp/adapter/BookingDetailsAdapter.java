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

/**
 * Defines behaviour for BookingDetailActivity's Recyclerview and its items.
 * Code done by Dexter.
 */

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.BookingDetailsViewHolder> {
    private ArrayList<BookingDetailsEntity> mBookingDetails = new ArrayList<>();
    private OnBookingDetailListener mOnBookingDetailListener;

    public BookingDetailsAdapter(ArrayList<BookingDetailsEntity> bookings, OnBookingDetailListener onBookingDetailListener) {
        this.mBookingDetails = bookings;
        this.mOnBookingDetailListener = onBookingDetailListener;

    }

    @NonNull
    @Override
    public BookingDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_list_item, parent, false);
        return new BookingDetailsViewHolder(view, mOnBookingDetailListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailsViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mBookingDetails.size();
    }

    public void updateData(ArrayList<BookingDetailsEntity> items) {
        mBookingDetails = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    private BookingDetailsEntity getItem(int position) {
        if (position >= 0 && position < mBookingDetails.size())
            return mBookingDetails.get(position);

        return null;
    }

    public interface OnBookingDetailListener{
        void onBookingDetailClick(int position);
    }

    class BookingDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView destination;
        TextView description;
        OnBookingDetailListener onBookingDetailListener;

        BookingDetailsViewHolder(View itemView, OnBookingDetailListener onBookingDetailListener) {
            super(itemView);
            destination = itemView.findViewById(R.id.destination);
            description = itemView.findViewById(R.id.description);
            this.onBookingDetailListener = onBookingDetailListener;

            itemView.setOnClickListener(this);
        }


        void bindData(BookingDetailsEntity bookingDetailsEntity) {
            if (bookingDetailsEntity == null)
                return;

            destination.setText(bookingDetailsEntity.getDestination());
            description.setText(bookingDetailsEntity.getDescription());
        }

        @Override
        public void onClick(View v) {
            onBookingDetailListener.onBookingDetailClick(getAdapterPosition());
        }
    }
}