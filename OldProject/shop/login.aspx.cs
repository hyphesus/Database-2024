//using shop.Database;

using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using shop.Sinfilar;
using shop.Database;

namespace shop
{
    public partial class login : System.Web.UI.Page
    {
        querry kuyruk = new querry();
        protected void Page_Load(object sender, EventArgs e)
        {
        }
        protected void UyeGiris_Click(object sender, EventArgs e)
        {
            kullanici user = new kullanici(0,"","", text1.Text, "",0,"","", text2.Text);
            if(kuyruk.sessionVeri(user)!=null)
            {
                user = kuyruk.sessionVeri(user);
           
            
            if (kuyruk.kullanici_Giris(user))
            {
                Session.Timeout = 10;

                Session.Add("id", user.Id);
                Session.Add("ad", user.Ad);
                Session.Add("soyad", user.Soyad);
                Session.Add("kullanici", user.Kullanici_ad);
                Session.Add("eposta", user.E_posta);
                Session.Add("telefon", user.Tel_no);
                Session.Add("adres1", user.Adres1);
                Session.Add("adres2", user.Adres2);
                Session.Add("sifre", user.Sifre);

                ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "basariliGiris();", true);
                //System.Threading.Thread.Sleep(10000);
                //Response.Redirect("profil.aspx", true);
                Response.AddHeader("Refresh", "3; url=profil.aspx");



            }
            }
            else
                ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "yanlisGiris();", true);
        }

        protected void Kaydol_Click(object sender, EventArgs e)
        {
            if (TextBox7.Text == TextBox8.Text && TextBox1.Text!= "" && TextBox2.Text != "" && TextBox3.Text != "" && TextBox4.Text != "" && TextBox5.Text != "" && TextBox6.Text != "" && TextBox7.Text != "" && TextBox8.Text != "")
            {
                kullanici user = new kullanici(0, TextBox1.Text, TextBox2.Text, TextBox3.Text, TextBox4.Text, Convert.ToInt32(TextBox5.Text), TextBox6.Text, "", TextBox8.Text);

                if (kuyruk.kullanici_Kayit(user))
                {

                    Session.Timeout = 10;
                    Session.Add("KullaniciAdi", TextBox3.Text);
                    Session.Add("Eposta", TextBox4.Text);
                    Session.Add("Sifre", TextBox6.Text);
                    ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "basariliPopup();", true);
                    text1.Text = "";
                    text2.Text = "";
                    TextBox1.Text = "";
                    TextBox2.Text = "";
                    TextBox3.Text = "";
                    TextBox4.Text = "";
                    TextBox5.Text = "";
                    TextBox6.Text = "";
                    TextBox7.Text = "";
                    TextBox8.Text = "";

                }
                else
                    ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "hataPopup();", true);
            }
            else
            {
                
               ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "sifreUyusmuyor();", true);
                TextBox7.Text = "";
                TextBox8.Text = "";
            }
            
        }

    }
}