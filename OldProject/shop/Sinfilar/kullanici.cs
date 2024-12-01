using Microsoft.Win32.SafeHandles;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Runtime.Remoting.Metadata.W3cXsd2001;
using System.Web;

namespace shop.Sinfilar
{
    public class kullanici
    {
        private int id;
        private string ad;
        private string soyad;
        private string kullanici_ad;
        private string sifre;
        private string e_posta;
        private int tel_no;
        private string adres1;
        private string adres2;

        public string Kullanici_ad { get => kullanici_ad; set => kullanici_ad = value; }
        public string Sifre { get => sifre; set => sifre = value; }
        public string Ad { get => ad; set => ad = value; }
        public string Soyad { get => soyad; set => soyad = value; }
        public string E_posta { get => e_posta; set => e_posta = value; }
        public int Id { get => id; set => id = value; }
        public int Tel_no { get => tel_no; set => tel_no = value; }
        public string Adres1 { get => adres1; set => adres1 = value; }
        public string Adres2 { get => adres2; set => adres2 = value; }

        public kullanici()
        { 
        }
    public kullanici(int id,string ad,string soyad, string kullanici_ad,string e_posta,int tel_no,string adres1,string adres2, string sifre)
        {


            this.id = id;
            this.ad = ad;
            this.soyad = soyad;
            this.kullanici_ad = kullanici_ad;
            this.sifre = sifre;
            this.e_posta = e_posta;
            this.tel_no = tel_no;
            this.adres1 = adres1;
            this.adres2 = adres2;


        }





     
    

    }
}