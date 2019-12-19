package com.example.thuongmaidientu.object;

import java.io.Serializable;

public class OrderDetail implements Serializable {

    public OrderDetail(String iDOrder,String iDBag, String iDKhachHang, String iDNhanVien, String iDThanhToan, String iDDanhGia, String ten, String gia,String hinhAnh, String soLuong, String maGiam, String thanhTien, String diaChiNhan,String ngayDat,String ngayNhan, String trangThai) {
        this.iDOrder=iDOrder;
        this.iDBag = iDBag;
        this.iDKhachHang = iDKhachHang;
        this.iDNhanVien = iDNhanVien;
        this.iDThanhToan = iDThanhToan;
        this.iDDanhGia = iDDanhGia;
        this.ten = ten;
        this.gia = gia;
        this.hinhAnh=hinhAnh;
        this.soLuong = soLuong;
        this.maGiam = maGiam;
        this.thanhTien = thanhTien;
        this.diaChiNhan = diaChiNhan;
        this.ngayDat = ngayDat;
        this.ngayNhan=ngayNhan;
        this.trangThai = trangThai;
    }

    public OrderDetail()
    {
    }

    public String getiDBag() {
        return iDBag;
    }

    public void setiDBag(String iDBag) {
        this.iDBag = iDBag;
    }

    public String getiDKhachHang() {
        return iDKhachHang;
    }

    public void setiDKhachHang(String iDKhachHang) {
        this.iDKhachHang = iDKhachHang;
    }

    public String getiDNhanVien() {
        return iDNhanVien;
    }

    public void setiDNhanVien(String iDNhanVien) {
        this.iDNhanVien = iDNhanVien;
    }

    public String getiDThanhToan() {
        return iDThanhToan;
    }

    public void setiDThanhToan(String iDThanhToan) {
        this.iDThanhToan = iDThanhToan;
    }

    public String getiDDanhGia() {
        return iDDanhGia;
    }

    public void setiDDanhGia(String iDDanhGia) {
        this.iDDanhGia = iDDanhGia;
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

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaGiam() {
        return maGiam;
    }

    public void setMaGiam(String maGiam) {
        this.maGiam = maGiam;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getiDOrder() {
        return iDOrder;
    }

    public void setiDOrder(String iDOrder) {
        this.iDOrder = iDOrder;
    }

    String iDOrder, iDBag, iDKhachHang, iDNhanVien, iDThanhToan,  iDDanhGia, ten, gia, soLuong, maGiam, thanhTien, diaChiNhan,ngayDat,ngayNhan, trangThai,hinhAnh;
}
