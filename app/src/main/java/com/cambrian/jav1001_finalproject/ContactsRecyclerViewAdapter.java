package com.cambrian.jav1001_finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private MainActivity context;
    private List<ContactModel> contactModels;
    private ContactsRecyclerViewAdapterInterface adapterInterface;

    /**
     * Constructor to initialise ContactsRecyclerViewAdapter class
     * @param context: MainActivity
     * @return ContactsRecyclerViewAdapter
     */
    public ContactsRecyclerViewAdapter(MainActivity context) {
        this.context = context;
        adapterInterface = context;
    }

    /**
     * Creates view holder for Contacts Recycler view
     * @param viewType: Integer
     * @param parent: ViewGroup
     * @return ContactsRecyclerViewAdapter.ViewHolder
     */
    @NonNull
    @Override
    public ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_recycler_view_item, parent, false));
    }

    /**
     * Sets contact models to Recycler view and updates
     * @param contactModels: List of contact models
     * @return nothing
     */
    public void setContactModels(List<ContactModel> contactModels) {
        this.contactModels = contactModels;
        notifyDataSetChanged();
    }

    /**
     * Deletes item from recycler view for a position
     * @param position: Integer
     * @return nothing
     */
    public void deleteItem(int position) {
        final ContactModel deletedContact = contactModels.get(position);
        contactModels.remove(position);
        adapterInterface.deleteContact(deletedContact);
        notifyItemRemoved(position);
    }

    /**
     * Listener method for Recycler view
     * @param holder: ContactsRecyclerViewAdapter.ViewHolder
     * @param position: Integer
     * @return nothing
     */
    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
        ContactModel model = contactModels.get(position);
        holder.contactTextView.setText(model.getFirstName() + " " + model.getLastName());
        // Checks if image is null or not
        // If image is null then creates TextDrawable
        if (model.getContactImage() == null || model.getContactImage().length == 0) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            // below text drawable is a circular.
            TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                    .width(100)  // width in px
                    .height(100) // height in px
                    .endConfig()
                    .buildRound((model.getFirstName().substring(0, 1) + model.getLastName().substring(0, 1)), color);
            // setting image to our image view on below line.
            holder.contactImageView.setImageDrawable(textDrawable);
        } else {
            Bitmap bmp = BitmapFactory.decodeByteArray(model.getContactImage(), 0, model.getContactImage().length);
            holder.contactImageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 1000, 1000, false));
        }
        // on below line we are adding on click listener to our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterInterface.contactDidTap(model);
            }
        });
    }

    /**
     * Gets item count for recycler view
     * @return Integer
     */
    @Override
    public int getItemCount() {
        return contactModels == null ? 0 : contactModels.size();
    }

    /**
     * This class is of view item of recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line creating a variable
        // for our image view and text view.
        private ImageView contactImageView;
        private TextView contactTextView;

        /**
         * Constructor for view holder
         * @param itemView: View
         * @return ViewHolder
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our image view and text view.
            contactImageView = itemView.findViewById(R.id.idContactItemImageView);
            contactTextView = itemView.findViewById(R.id.idContactItemTextView);
        }
    }
}
