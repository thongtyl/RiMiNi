package com.example.thuongmaidientu.object;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Bag implements Serializable {
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //id,idncc,ten,gia,hinhanh,mota,kichthuoc,idloai
    String ten, gia,moTa,kichThuoc,iDNhaCungCap,iDLoai;
    String image;
    String idBag;

    public String getIdBag() {
        return idBag;
    }

    public void setIdBag(String idBag) {
        this.idBag = idBag;
    }

    public Bag(String idBag, String ten, String gia, String moTa, String kichThuoc, String iDNhaCungCap, String iDLoai, String image) {
        this.idBag = idBag;
        this.ten = ten;
        this.image=image;
        this.gia = gia;
        this.moTa = moTa;
        this.kichThuoc = kichThuoc;
        this.iDNhaCungCap = iDNhaCungCap;
        this.iDLoai = iDLoai;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getiDNhaCungCap() {
        return iDNhaCungCap;
    }

    public void setiDNhaCungCap(String iDNhaCungCap) {
        this.iDNhaCungCap = iDNhaCungCap;
    }

    public String getiDLoai() {
        return iDLoai;
    }

    public void setiDLoai(String iDLoai) {
        this.iDLoai = iDLoai;
    }

    public Bag()
    {

    }
}
