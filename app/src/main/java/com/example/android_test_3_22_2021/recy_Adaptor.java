package com.example.android_test_3_22_2021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class recy_Adaptor extends RecyclerView.Adapter<recy_Adaptor.ViewHolder> implements Filterable {
    Context context;
    ArrayList<items> itemss;
    ArrayList<items> itemssAll;
private OnItemClickListener mlistener;
public interface OnItemClickListener
{
    void Remove_item(int position);

    void Edit_item(int position);
}
public  void  setOnClickListener(OnItemClickListener listener)
{
    this.mlistener = listener;
}
    public recy_Adaptor(Context context, ArrayList<items> itemss) {
        this.context = context;
        this.itemss = itemss;
        this.itemssAll = new ArrayList<>(itemss);

    }

    @NonNull
    @Override
    public recy_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hello, parent, false);
        return new ViewHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull recy_Adaptor.ViewHolder holder, int position) {
        items item = itemss.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImg());

    }

    @Override
    public int getItemCount() {
        return itemss.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.my_txt);
            imageView = itemView.findViewById(R.id.my_img);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION)
                        {
                            listener.Edit_item(postion);
                        }
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int pos  =getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION)
                        {
                            listener.Remove_item(pos);
                        }
                    }
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        // background thread
        protected FilterResults performFiltering(CharSequence keyword)
        {
            ArrayList<items> filtereddata=new ArrayList<>();

            if(keyword.toString().isEmpty())
                filtereddata.addAll(itemssAll);
            else
            {
                for(items obj : itemssAll)
                {
                    if(obj.getText().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @Override  // main UI thread
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            itemss.clear();
            itemss.addAll((ArrayList<items>)results.values);
            notifyDataSetChanged();
        }
    };



}
