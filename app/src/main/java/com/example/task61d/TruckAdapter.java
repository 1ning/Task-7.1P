package com.example.task61d;

import static android.content.Intent.createChooser;
import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.MyViewHolder>{
    private List<Truck>list;
    private View inflater;
    private Context mContext;



    public TruckAdapter(Context context,List<Truck> list) {
        this.list = list;
        mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(mContext).inflate(R.layout.add_contacts,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }
    //onBindViewHolder()方法用于对RecyclerView子项数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
    //这里我们通过position参数的得到当前项的实例，然后将数据设置到ViewHolder的TextView即可
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将数据和控件绑定
        holder.title.setText(list.get(position).type);
        switch (list.get(position).type)
        {
            case "Truck":
                holder.photo.setImageResource(R.drawable.truck_1);
                break;
            case "Van":
                holder.photo.setImageResource(R.drawable.truck_2);
                break;
            case "Refrigeratedtruck":
                holder.photo.setImageResource(R.drawable.truck_3);
                break;
            case "Minitruck":
                holder.photo.setImageResource(R.drawable.truck_4);
                break;
            case "Other":
                holder.photo.setImageResource(R.drawable.truck_5);
                break;
        }
        holder.context.setText(list.get(position).context);

    }
    //getItemCount()告诉RecyclerView一共有多少个子项，直接返回数据源的长度。
    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView photo;
        TextView context;
        ImageButton share;
        public MyViewHolder(View itemView) {//这个view参数就是recyclerview子项的最外层布局
            super(itemView);
            //可以通过findViewById方法获取布局中的TextView
            title = (TextView) itemView.findViewById(R.id.title);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            context = (TextView) itemView.findViewById(R.id.context);
            share = (ImageButton) itemView.findViewById(R.id.share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title1= (String) title.getText();
                    String context1= (String) context.getText();
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    String extraText="This car type is"+title1+", besides its Model is"+context1;
                    share.putExtra(Intent.EXTRA_TEXT, extraText);
                    mContext.startActivity(Intent.createChooser(share,"SHARE"));
                }
            });
        }
    }
}

