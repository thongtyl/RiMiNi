package com.example.thuongmaidientu.object;

public class Bill {
    String key,hinhThuc,soTK,soTien,tinhTrang;

    public Bill(String key, String hinhThuc, String soTK,String soTien, String tinhTrang) {
        this.key = key;
        this.hinhThuc = hinhThuc;
        this.soTK = soTK;
        this.soTien=soTien;
        this.tinhTrang = tinhTrang;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public  Bill()
    {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public String getSoTK() {
        return soTK;
    }

    public void setSoTK(String soTK) {
        this.soTK = soTK;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
