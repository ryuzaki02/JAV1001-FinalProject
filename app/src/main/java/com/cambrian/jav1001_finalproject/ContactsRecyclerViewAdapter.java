package com.cambrian.jav1001_finalproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;
import java.util.Random;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ContactModel> contactModels;

    public ContactsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_recycler_view_item, parent, false));
    }

    public void setContactModels(List<ContactModel> contactModels) {
        this.contactModels = contactModels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
        ContactModel model = contactModels.get(position);
        holder.contactTextView.setText(model.getName());
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        // below text drawable is a circular.
        TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                .width(100)  // width in px
                .height(100) // height in px
                .endConfig()
                .buildRound(model.getName().substring(0, 1), color);
        // setting image to our image view on below line.
        holder.contactImageView.setImageDrawable(textDrawable);
        // on below line we are adding on click listener to our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening a new activity and passing data to it.
//                Intent i = new Intent(context, ContactDetailActivity.class);
//                i.putExtra("name", modal.getUserName());
//                i.putExtra("contact", modal.getContactNumber());
//                // on below line we are starting a new activity,
//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactModels == null ? 0 : contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line creating a variable
        // for our image view and text view.
        private ImageView contactImageView;
        private TextView contactTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our image view and text view.
            contactImageView = itemView.findViewById(R.id.idContactItemImageView);
            contactTextView = itemView.findViewById(R.id.idContactItemTextView);
        }
    }
}
