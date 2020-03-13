package com.example.gransbootdata;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.IpSecManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterAttack extends RecyclerView.Adapter<RecyclerAdapterAttack.ViewHolder> {

    private ArrayList<Data> _attacks;
    private String _name;
    private Context _context;

    public RecyclerAdapterAttack(ArrayList<Data> _attacks, String _name, Context _context) {
        this._attacks = _attacks;
        this._name = _name;
        this._context = _context;
    }

    @NonNull
    @Override
    public RecyclerAdapterAttack.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(_context);
        View v = li.inflate(R.layout.row, parent, false);
        return new RecyclerAdapterAttack.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterAttack.ViewHolder holder, int position) {
        holder.bind(_attacks.get(position));
    }

    @Override
    public int getItemCount() {
        return _attacks.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView input,type,start,advb,advhit,desc;
        private ImageView im;
        private LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ll = itemView.findViewById(R.id.ll);
            this.im = itemView.findViewById(R.id.attackimage);
            this.input = itemView.findViewById(R.id.input);
            this.type = itemView.findViewById(R.id.type);
            this.start = itemView.findViewById(R.id.startframe);
            this.advb = itemView.findViewById(R.id.hit);
            this.advhit = itemView.findViewById(R.id.block);
            this.desc = itemView.findViewById(R.id.desc);
        }

        public void bind(Data at){
            if(!at.get_name().equals("")){
                this.im.setImageResource(_context.getResources().getIdentifier(_name.toLowerCase()+at.get_name(),"drawable",_context.getPackageName()));//Character name + attack input = imagename
            }else{
                this.im.setImageResource(0);
            }
            this.input.setText(at.get_input());
            this.type.setText(at.get_type());
            this.start.setText(at.get_start());
            this.desc.setText(at.get_desc());
            this.advb.setText(at.get_advb());
           //this.ll.getBackground().setColorFilter(Color.parseColor("#343434"), PorterDuff.Mode.SRC_IN);
            try {
               if (!at.get_advb().equals("-")) {
                    if (Integer.parseInt((at.get_advb())) < 0) {
                        this.advb.setBackgroundColor(_context.getResources().getColor(R.color.Negative));
                    } else {
                        this.advb.setBackgroundColor(_context.getResources().getColor(R.color.Positive));
                    }
                }else{
                   this.advb.setBackground(null);
               }
            }catch (NullPointerException | NumberFormatException e){
                e.printStackTrace();
                Log.e("Lacks info","Null framedata on hit");
            }
            this.advhit.setText(at.get_advhit());
            try {
                if(at.get_advhit().equals("Knockdown") || at.get_advhit().equals("HKD")){
                    this.advhit.setBackgroundColor(_context.getResources().getColor(R.color.Positive));
                }else if (!at.get_advhit().equals("-")) {
                    if (Integer.parseInt(at.get_advhit()) < 0) {
                        this.advhit.setBackgroundColor(_context.getResources().getColor(R.color.Negative));
                    } else {
                        this.advhit.setBackgroundColor(_context.getResources().getColor(R.color.Positive));
                    }
                }else{
                    this.advhit.setBackground(null);
                }
            }catch (NullPointerException | NumberFormatException e){
                e.printStackTrace();
                Log.e("Lacks info","Null framedata on block");
            }
        }
    }
}
