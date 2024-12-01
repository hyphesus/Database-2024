using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace shop.Sinfilar
{


    public class Urunler
    {

        private int id;
        private string ad;
        private string cinsiyet;
        private int katId;
        private string size;
        private string renk;
        private int ayakkabiNumarasi;
        private string resim;
        private int fiyat;



        public Urunler()
        {

        }

        public Urunler(int id, string ad, string cinsiyet,int KatId, string size, string renk, int ayakkabiNumarasi, string resim,int fiyat)
        {
            this.id = id;
            this.ad = ad;
            this.cinsiyet = cinsiyet;
            this.katId = katId;
            this.size = size;
            this.renk = renk;
            this.ayakkabiNumarasi = ayakkabiNumarasi;
            this.resim = resim;
            this.fiyat = fiyat;
        }

        public int Id { get => id; set => id = value; }
        public string Ad { get => ad; set => ad = value; }
        public string Cinsiyet { get => cinsiyet; set => cinsiyet = value; }
        public string Size { get => size; set => size = value; }
        public string Renk { get => renk; set => renk = value; }
        public int AyakkabiNumarasi { get => ayakkabiNumarasi; set => ayakkabiNumarasi = value; }
        public string Resim { get => resim; set => resim = value; }
        public int Fiyat { get => fiyat; set => fiyat = value; }
        public int KatId { get => katId; set => katId = value; }

    }
}