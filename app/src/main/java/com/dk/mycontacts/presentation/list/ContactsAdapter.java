package com.dk.mycontacts.presentation.list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.dk.mycontacts.R;
import com.dk.mycontacts.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Contact> contacts;
    private OnItemClickListener onItemClickListener;
    private OnDelButtonClickListener onDelButtonClickListener;

    public ContactsAdapter(OnItemClickListener onItemClickListener, OnDelButtonClickListener onDelButtonClickListener) {
        this.contacts = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
        this.onDelButtonClickListener = onDelButtonClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        viewHolder = new ItemViewHolder(v, onItemClickListener, onDelButtonClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Contact item = contacts.get(position);
        String fullName = String.format("%s %s", item.getFirstName(), item.getLastName());
        itemViewHolder.name.setText(fullName);
        itemViewHolder.email.setText(item.getEmail());
        itemViewHolder.avatar.setImageDrawable(getDrawableFromName(item.getFirstName()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setItems(List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public Contact getItem(int position){
        return contacts.get(position);
    }

    private TextDrawable getDrawableFromName(String name){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
        String firstLetter = name.substring(0,1);
        return TextDrawable.builder().buildRect(firstLetter, color);
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView email;
        private ImageView avatar;
        private ImageButton buttonDel;
        private OnItemClickListener onItemClickListener;
        private OnDelButtonClickListener onDelButtonClickListener;

        private ItemViewHolder(View itemView, OnItemClickListener onItemClickListener,
                               OnDelButtonClickListener onDelButtonClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_name);
            email = itemView.findViewById(R.id.textView_email);
            avatar = itemView.findViewById(R.id.imageView_item_avatar);
            buttonDel = itemView.findViewById(R.id.imageButton_del);
            this.onItemClickListener = onItemClickListener;
            this.onDelButtonClickListener = onDelButtonClickListener;

            itemView.setOnClickListener(this);
            buttonDel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.constraintLayout_item:
                    this.onItemClickListener.onItemClick(getAdapterPosition());
                    break;
                case R.id.imageButton_del:
                    this.onDelButtonClickListener.onDelButtonClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnDelButtonClickListener {
        void onDelButtonClick(int position);
    }
}
