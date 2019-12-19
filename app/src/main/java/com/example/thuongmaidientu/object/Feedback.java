package com.example.thuongmaidientu.object;

public class Feedback {
    String key, noiDung, idUser, idBag;
    int diemSo;

    public Feedback(String key,String idUser,String idBag, String noiDung, int diemSo) {
        this.key = key;
        this.idUser = idUser;
        this.idBag = idBag;
        this.noiDung = noiDung;
        this.diemSo = diemSo;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdBag() {
        return idBag;
    }

    public void setIdBag(String idBag) {
        this.idBag = idBag;
    }

    public Feedback()
    {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(int diemSo) {
        this.diemSo = diemSo;
    }
}
