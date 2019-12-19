package com.example.thuongmaidientu.ui.cart;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuongmaidientu.object.BagCart;
import com.example.thuongmaidientu.Adapter.CartAdapter;
import com.example.thuongmaidientu.object.Code;
import com.example.thuongmaidientu.LoginHelper;
import com.example.thuongmaidientu.PayActivity;
import com.example.thuongmaidientu.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    TextView tamtinh, thanhtien;
    Button thanhtoan, huy;
    EditText code;
    int intthanhtien;
    int inttamtinh;
    int intcode;

    //Khai báo adapter
    ArrayList<BagCart> list;
    CartAdapter cartAdapter ;

    //Khai bao firebase
    DatabaseReference databaseReference;
    DatabaseReference fbPayment;
    DatabaseReference fbCheckCode;

    //Kahi báo datalogin
    LoginHelper dbHelper;

    //danh sách mã giảm
    ArrayList<Code> listcode;

    // sql
    LoginHelper loginHelper;
    // id người dùng login
    String IdUser="";

    private CartViewModel mViewModel;

    public static CartFragment newInstance() {
        return new CartFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        //Ánh xạ
        tamtinh = v.findViewById(R.id.txt_tamtinh);
        thanhtien = v.findViewById(R.id.txt_thanhtien);
        thanhtoan = v.findViewById(R.id.button_next);
        huy=v.findViewById(R.id.button_cancel);
        code = v.findViewById(R.id.edt_code);
        //lay dnah sach code
        listcode = new ArrayList<>();
        AddCode();
        CheckCode();
        // lấy id người dừng
        GetIDUser();

        list= new ArrayList<>();
        cartAdapter = new CartAdapter(list,getActivity().getApplicationContext());
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_cart);
        recyclerView.setAdapter(cartAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter.GetIDUser();

        inttamtinh=0;
        intcode=0;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(IdUser).child("Cart").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BagCart bagCart = dataSnapshot.getValue(BagCart.class);
                list.add(bagCart);
                cartAdapter.notifyDataSetChanged();
                inttamtinh=inttamtinh+(Integer.parseInt(bagCart.getGia())*bagCart.getSoLuong());
                String out = String.valueOf(inttamtinh);
                tamtinh.setText(out);
                thanhtien.setText(out);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Payment();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                BagCart bagCart = dataSnapshot.getValue(BagCart.class);
                list.remove(bagCart);
                Payment();
                if (list.size()==0) {
                    String out = String.valueOf(0);
                    tamtinh.setText(out);
                    code.setText("");
                    thanhtien.setText(out);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size()!=0) {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra("magiam",String.valueOf(intcode));
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), "Bạn chưa chọn sản phẩm nào", Toast.LENGTH_SHORT).show();
                }
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelateCrat();
                list.clear();
                cartAdapter.notifyDataSetChanged();
                String out = String.valueOf(0);
                tamtinh.setText(out);
                code.setText("");
            }
        });

        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        // TODO: Use the ViewModel
    }

    public void DelateCrat()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(IdUser).child("Cart").removeValue();
    }

    public void Payment()
    {
        inttamtinh=0;
        intthanhtien=0;
        fbPayment = FirebaseDatabase.getInstance().getReference();
        fbPayment.child("Users").child(IdUser).child("Cart").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BagCart bagCart = dataSnapshot.getValue(BagCart.class);
                inttamtinh=inttamtinh+(Integer.parseInt(bagCart.getGia())*bagCart.getSoLuong());
                String out = String.valueOf(inttamtinh);
                tamtinh.setText(out);
                intthanhtien=(inttamtinh-(inttamtinh*intcode)/100);
                String out2 = String.valueOf(intthanhtien);
                thanhtien.setText(out2);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void AddCode()
    {
        fbCheckCode = FirebaseDatabase.getInstance().getReference("Code");
        fbCheckCode.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Code code = dataSnapshot.getValue(Code.class);
                listcode.add(code);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CheckCode()
    {
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i=0;i<listcode.size();i++)
                {
                    if (listcode.get(i).getMa().toLowerCase().equals(s.toString().toLowerCase()))
                    {
                        intcode=Integer.parseInt(listcode.get(i).getGiam());

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Thông Báo");
                        alert.setMessage("Đơn hàng của bạn được giảm"+listcode.get(i).getGiam()+"%");
                        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                inttamtinh=Integer.parseInt(tamtinh.getText().toString());
                                intthanhtien=0;
                                intthanhtien=(inttamtinh-(inttamtinh*intcode)/100);
                                String out2 = String.valueOf(intthanhtien);
                                thanhtien.setText(out2);
                            }
                        });
                        code.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        alert.show();
                        return;
                    }
                    else
                    {
                        intcode=0;
                        inttamtinh=Integer.parseInt(tamtinh.getText().toString());
                        intthanhtien=0;
                        intthanhtien=(inttamtinh-(inttamtinh*intcode)/100);
                        String out2 = String.valueOf(intthanhtien);
                        thanhtien.setText(out2);
                    }
                }
            }
        });
    }

    public void GetIDUser()
    {
        loginHelper = new LoginHelper(getActivity(), "User.sqlite", null, 1);
        Cursor data = loginHelper.GetData("SELECT * FROM User");
        while (data.moveToNext()) {
            IdUser = data.getString(1);
        }
    }
}