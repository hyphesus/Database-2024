using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Data;
using System.Linq;
using System.Web;
using shop.Sinfilar;
using System.Web.Optimization;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Runtime.Remoting.Messaging;
using System.Collections;
using System.Web.Services.Protocols;
using System.Web.UI.HtmlControls;

namespace shop.Database
{
    public class querry
    {
        SqlConnection baglanti = new SqlConnection("Data Source=.; database=shopDatabase;Integrated Security=True");


        public querry()
        {

        }


            public Boolean kullanici_Giris(kullanici user)
            {

                try
                {
                    SqlCommand komut = new SqlCommand();
                    SqlDataReader okuma;
                    if (baglanti.State == ConnectionState.Closed)
                    {
                    baglanti.Open();
                    }
                    komut.Connection = baglanti;
                    komut.Parameters.AddWithValue("@kullaniciAdi", user.Kullanici_ad);
                    komut.Parameters.AddWithValue("@sifre", user.Sifre);
                    komut.CommandText = "select * from kullanici WHERE kullanici_ad = @kullaniciAdi and sifre = @sifre";

                     okuma = komut.ExecuteReader();

                    if (okuma.Read())
                    {

                        baglanti.Close();
                        return true;
                    }
                     okuma.Close();
                    return false;
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                    return false;
                }

        }




        public kullanici sessionVeri(kullanici user)
        {

            if (baglanti.State == ConnectionState.Closed)
            {
                baglanti.Open();
            }

            SqlCommand comd = new SqlCommand("select * from kullanici  where kullanici_ad=@kullaniciAdi AND sifre=@sifre", baglanti);

            comd.Parameters.AddWithValue("@kullaniciAdi", user.Kullanici_ad);
            comd.Parameters.AddWithValue("@sifre", user.Sifre);

            SqlDataReader reader = comd.ExecuteReader();

            while (reader.Read())
            {

                kullanici nesne = new kullanici();
                nesne.Id = Convert.ToInt32((String.Format("{0}", reader["id"])));
                nesne.Ad = (String.Format("{0}", reader["ad"]));
                nesne.Soyad = String.Format("{0}", reader["soyad"]);
                nesne.Kullanici_ad = string.Format("{0}", reader["kullanici_ad"]);
                nesne.E_posta = (String.Format("{0}", reader["eposta"]));
                nesne.Tel_no = Convert.ToInt32((String.Format("{0}", reader["Telefon_No"])));
                nesne.Adres1 = (String.Format("{0}", reader["adres1"]));
                nesne.Adres2 = (String.Format("{0}", reader["adres2"]));
                nesne.Sifre = (String.Format("{0}", reader["sifre"]));

                baglanti.Close();
                return nesne;
            }

            baglanti.Close();
            return null;
        }



        public void profilGuncelleme(kullanici user)
        {
            if (baglanti.State == ConnectionState.Closed)
            {
                baglanti.Open();
            }

            SqlCommand comd = new SqlCommand("UPDATE kullanici SET ad=@ad,soyad=@soyad,kullanici_ad=@kullaniciAdi,sifre=@sifre,eposta=@eposta,Telefon_No=@telefon,adres1=@adres1,adres2=@adres2 where id=@id", baglanti);
            comd.Parameters.AddWithValue("@id", user.Id);
            comd.Parameters.AddWithValue("@ad", user.Ad);
            comd.Parameters.AddWithValue("@soyad", user.Soyad);
            comd.Parameters.AddWithValue("@kullaniciAdi", user.Kullanici_ad);
            comd.Parameters.AddWithValue("@sifre", user.Sifre);
            comd.Parameters.AddWithValue("@eposta", user.E_posta);
            comd.Parameters.AddWithValue("@telefon", user.Tel_no);
            comd.Parameters.AddWithValue("@adres1", user.Adres1);
            comd.Parameters.AddWithValue("@adres2", user.Adres2);

            comd.ExecuteNonQuery();
        

        }



        public void sifreDegistir(kullanici user)
        {
            if (baglanti.State == ConnectionState.Closed)
            {
                baglanti.Open();
            }

            SqlCommand comd = new SqlCommand("UPDATE kullanici SET sifre=@sifre where id=@id", baglanti);
            comd.Parameters.AddWithValue("@id", user.Id);
            comd.Parameters.AddWithValue("@sifre", user.Sifre);

            comd.ExecuteNonQuery();


        }


        public void hesapSil(int userId)
        {
            if (baglanti.State == ConnectionState.Closed)
            {
                baglanti.Open();
            }

            SqlCommand comd = new SqlCommand("delete from kullanici where id=@id", baglanti);
            comd.Parameters.AddWithValue("@id", userId);

            comd.ExecuteNonQuery();


        }

        public List<Urunler> urunListesi(int KatId)
        {
            List<Urunler> liste = new List<Urunler>();
            if (baglanti.State == ConnectionState.Closed)
            {
                baglanti.Open();
            }
            SqlCommand comd = new SqlCommand("select * from urunler where kategori_id =@KatId", baglanti);
            comd.Parameters.AddWithValue("@KatId", KatId);

            SqlDataReader reader = comd.ExecuteReader();

            while (reader.Read())
            {

                Urunler urun = new Urunler();
                urun.Id = Convert.ToInt32((String.Format("{0}", reader["urun_id"])));
                urun.Ad = (String.Format("{0}", reader["urun_ad"]));
                urun.Cinsiyet = String.Format("{0}", reader["cinsiyet"]);
                urun.KatId = Convert.ToInt32((String.Format("{0}", reader["kategori_id"])));
                urun.Size = (String.Format("{0}", reader["size"]));
                urun.Renk = (String.Format("{0}", reader["renk"]));
                urun.AyakkabiNumarasi = Convert.ToInt32((String.Format("{0}", reader["ayakkabi_numarasi"])));
                urun.Resim = (String.Format("{0}", reader["resim"]));
                urun.Fiyat = Convert.ToInt32((String.Format("{0}", reader["fiyat"])));

                liste.Add(urun);
            }

           baglanti.Close();
           return liste;
        }




        public Boolean kullanici_Kayit(kullanici user)
        {
            try
            {


                SqlCommand komut = new SqlCommand("BEGIN " +
                    "IF " +
                    "NOT EXISTS (SELECT * FROM kullanici WHERE kullanici_ad=@KullaniciAdi) " +
                    "BEGIN " +
                    "INSERT INTO kullanici (ad,soyad,kullanici_ad, sifre, eposta,telefon_no,adres1,adres2) values(@ad,@soyad,@KullaniciAdi,@Sifre,@Eposta,@telefon,@adres1,@adres2) " +
                    "END " +
                    "ELSE " +
                    "BEGIN " +
                    "select id from kullanici where kullanici_ad=(@KullaniciAdi) " +
                    "END " +
                    "END ",baglanti);
                komut.Parameters.AddWithValue("@ad", user.Ad);
                komut.Parameters.AddWithValue("@soyad", user.Soyad);
                komut.Parameters.AddWithValue("@kullaniciAdi", user.Kullanici_ad);
                komut.Parameters.AddWithValue("@sifre", user.Sifre);
                komut.Parameters.AddWithValue("@eposta", user.E_posta);
                komut.Parameters.AddWithValue("@telefon", user.Tel_no);
                komut.Parameters.AddWithValue("@adres1", user.Adres1);
                komut.Parameters.AddWithValue("@adres2", user.Adres2);


                baglanti.Open();
                SqlDataReader okuma = komut.ExecuteReader();
                while (okuma != null)
                {
                    if(okuma.RecordsAffected == -1)
                    {
                        baglanti.Close();
                        return false;
                    }
                    else
                    {
                        baglanti.Close();
                        return true;
                    }
                }


                baglanti.Close();
                return false;


            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                return false;
            }


        }



      



    }
}