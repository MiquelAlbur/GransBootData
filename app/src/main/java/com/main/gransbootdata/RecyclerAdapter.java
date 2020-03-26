package com.main.gransbootdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<Keko> _kekos;
    private OnNoteListener mOnNoteListener;
    private Context _context;

    public RecyclerAdapter(ArrayList<Keko> keko, OnNoteListener mOnNoteListener, Context context) {
        this._kekos = keko;
        this.mOnNoteListener = mOnNoteListener;
        this._context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_context);
        View v = li.inflate(R.layout.charrow, parent, false);
        return new ViewHolder(v,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(_kekos.get(position));
    }

    @Override
    public int getItemCount() {
        return _kekos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private OnNoteListener onNoteListener;
        private TextView _tv1;
        private ImageView _im;

        ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            this._tv1 = itemView.findViewById(R.id.charName);
            this._im = itemView.findViewById(R.id.imageChar);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Keko k){
            this._tv1.setText(k.get_name());
            int id = _context.getResources().getIdentifier(k.get_imagename(), "drawable", _context.getPackageName());
            _im.setImageResource(id);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    public ArrayList<Keko> get_data() {
        return _kekos;
    }

}