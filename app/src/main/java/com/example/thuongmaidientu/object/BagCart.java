package com.example.thuongmaidientu.object;

public class BagCart {
    String key, ten,gia,hinhAnh;
    int soLuong;

    public String getIdBag() {
        return idBag;
    }

    public void setIdBag(String idBag) {
        this.idBag = idBag;
    }

    String idBag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BagCart(String key,String idBag, String ten, String gia, String hinhAnh, int soLuong) {
        this.key = key;
        this.idBag=idBag;
        this.ten = ten;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
    }

    public BagCart()
    {

    }
}
