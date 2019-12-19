package com.example.thuongmaidientu.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuongmaidientu.LoginHelper;
import com.example.thuongmaidientu.R;
import com.example.thuongmaidientu.object.BagCart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    ArrayList<BagCart> list;
    Context context;
    DatabaseReference databaseReference;
    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser="";

    public CartAdapter(ArrayList<BagCart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_cart,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        holder.ten.setText(list.get(position).getTen());
        holder.gia.setText(list.get(position).getGia() + " USD");
        holder.soluong.setText(String.valueOf(list.get(position).getSoLuong()));
        Picasso.with(context).load(list.get(position).getHinhAnh()).into(holder.hinhanh);
        holder.tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int sl =  list.get(position).getSoLuong();
               sl=sl+1;
               list.get(position).setSoLuong(sl);
               BagCart bagCart = new BagCart(list.get(position).getKey(),list.get(position).getIdBag(),list.get(position).getTen(),list.get(position).getGia(),list.get(position).getHinhAnh(),sl);
               databaseReference = FirebaseDatabase.getInstance().getReference();
               databaseReference.child("Users").child(IdUser).child("Cart").child(list.get(position).getKey()).setValue(bagCart);
               String b = String.valueOf(sl);
               holder.soluong.setText(b);
            }
        });
        holder.giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl =  list.get(position).getSoLuong();
                if (sl<=1)
                {
                    //giu lai san pham
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Users").child(IdUser).child("Cart").child(list.get(position).getKey()).removeValue();
                    Toast.makeText(context,"Đã xóa sản phẩm",Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyDataSetChanged();
                }
                else
                {
                    sl=sl-1;
                    list.get(position).setSoLuong(sl);
                    BagCart bagCart = new BagCart(list.get(position).getKey(),list.get(position).getIdBag(),list.get(position).getTen(),list.get(position).getGia(),list.get(position).getHinhAnh(),sl);
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Users").child(IdUser).child("Cart").child(list.get(position).getKey()).setValue(bagCart);
                    String b = String.valueOf(sl);
                    holder.soluong.setText(b);
                }
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(IdUser).child("Cart").child(list.get(position).getKey()).removeValue();
                Toast.makeText(context,"Đã xóa sản phẩm",Toast.LENGTH_SHORT).show();
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView ten,gia,soluong;
        ImageButton tang, giam,xoa;
        ImageView hinhanh;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.txt_car_ten);
            gia=itemView.findViewById(R.id.txt_car_gia);
            soluong=itemView.findViewById(R.id.textView_total);
            hinhanh=itemView.findViewById(R.id.image_Cart);
            tang=itemView.findViewById(R.id.imageButton_add);
            giam=itemView.findViewById(R.id.imageButton_remove);
            xoa = itemView.findViewById(R.id.imageButton_delete);
        }
    }

    public void GetIDUser()
    {
        loginHelper = new LoginHelper(context, "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }
    }
}
