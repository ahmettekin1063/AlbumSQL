package com.ahmettekin;

import java.sql.*;
import java.util.ArrayList;

public class DataSource {

    public static final String DB_NAME = "sarkiProje.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public static final String TABLO_ALBUM = "album";
    public static final String SUTUN_ALBUM_ID = "albumId";
    public static final String SUTUN_ALBUM_ADI = "albumAd";
    public static final String SUTUN_ALBUM_SARKICIID = "sarkiciID";

    public static final String TABLO_SARKICI = "sarkici";
    public static final String SUTUN_SARKICI_ID = "sarkiciID";
    public static final String SUTUN_SARKICI_ADI = "sarkiciAd";

    public static final String TABLO_SARKI = "sarki";
    public static final String SUTUN_SARKI_ID = "sarkiId";
    public static final String SUTUN_SARKI_ADI = "sarkiAdi";
    public static final String SUTUN_SARKI_ALBUMID = "albumId";

    public static final int ARTAN_SIRA = 1;
    public static final int AZALAN_SIRA = 2;

    private Connection baglanti;

    private DataSource() {
    }

    private static DataSource instance = new DataSource();

    public static DataSource getInstance() {
        return instance;
    }


    public boolean veritabaniniAc() {
        try {
            baglanti = DriverManager.getConnection(CONNECTION_STRING);
            return true;

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public void veriTabaniniKapat() {
        try {
            if (baglanti != null) {
                baglanti.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }


    public ArrayList<Sarkici> tumSarkicilariGetir(int siralama) {

        ArrayList<Sarkici> sarkiciListesi = new ArrayList<>();

        StringBuilder sb = new StringBuilder("SELECT * FROM " + TABLO_SARKICI);

        if (siralama == ARTAN_SIRA) {
            sb.append(" ORDER BY sarkiciAd ASC");
        } else {
            sb.append(" ORDER BY sarkiciAd DESC");
        }

        try (Statement s = baglanti.createStatement();
             ResultSet sonuc = s.executeQuery(sb.toString())) {

            while (sonuc.next()) {

                Sarkici sarkici = new Sarkici();
                sarkici.setSarkiciID(sonuc.getInt(1));
                sarkici.setIsim(sonuc.getString(2));

                sarkiciListesi.add(sarkici);

            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return sarkiciListesi;
    }

    public ArrayList<Album> tumAlbumleriGetir(int id) {
        ArrayList<Album> albumList = new ArrayList<>();


        try (PreparedStatement ps = baglanti.prepareStatement("SELECT * FROM album WHERE sarkiciID = ?")) {
            ps.setInt(1, id);
            ResultSet sonuc = ps.executeQuery();

            while (sonuc.next()) {

                Album album = new Album();

                album.setAlbumID(sonuc.getInt(1));
                album.setIsim(sonuc.getString(2));
                album.setSarkiciID(sonuc.getInt(3));

                albumList.add(album);

            }
            return albumList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Boolean veriyiGuncelle(int albumId, String guncellenenVeri) {

        try(PreparedStatement ps = baglanti.prepareStatement("UPDATE album SET albumAd = ? WHERE albumId = ?")){

            ps.setString(1,guncellenenVeri);
            ps.setInt(2,albumId);



            return ps.execute();

        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
            return false;
        }

    }

}
