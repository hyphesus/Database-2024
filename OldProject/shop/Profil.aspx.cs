using Antlr.Runtime;
using Microsoft.Ajax.Utilities;
using shop.Database;
using shop.Sinfilar;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Optimization;
using System.Web.Script.Serialization;
using System.Web.Services;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace shop
{
    public partial class Profil : System.Web.UI.Page
    {
        querry kuyruk = new querry();

        [WebMethod]
        protected void Page_Load(object sender, EventArgs e)
        {

            if(TextBox1.Text!=""&& TextBox2.Text != "" && TextBox3.Text != "" && TextBox4.Text != "")
            {
                Session["ad"] = TextBox1.Text;
                Session["soyad"] = TextBox2.Text;
                Session["eposta"] = TextBox3.Text;
                Session["telefon"] = TextBox4.Text;
                Session["adres1"] = TextBox5.Text;
                Session["adres2"] = TextBox6.Text;
                TextBox1.Text = Convert.ToString(Session["ad"]);
                TextBox2.Text = Convert.ToString(Session["soyad"]);
                TextBox3.Text = Convert.ToString(Session["eposta"]);
                TextBox4.Text = Convert.ToString(Session["telefon"]);
                TextBox5.Text = Convert.ToString(Session["adres1"]);
                TextBox6.Text = Convert.ToString(Session["adres2"]);
            }
            else
            {
                TextBox1.Text = Convert.ToString(Session["ad"]);
                TextBox2.Text = Convert.ToString(Session["soyad"]);
                TextBox3.Text = Convert.ToString(Session["eposta"]);
                TextBox4.Text = Convert.ToString(Session["telefon"]);
                TextBox5.Text = Convert.ToString(Session["adres1"]);
                TextBox6.Text = Convert.ToString(Session["adres2"]);
            }

        }
        protected void Button1_Click(object sender, EventArgs e)
        {


            kullanici nesne = new kullanici(Convert.ToInt32(Session["id"]), TextBox1.Text, TextBox2.Text, Convert.ToString(Session["kullanici"]), TextBox3.Text, Convert.ToInt32(TextBox4.Text), TextBox5.Text, TextBox6.Text, Convert.ToString(Session["sifre"]));

            kuyruk.profilGuncelleme(nesne);
            ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "guncelleme();", true);




        }


        protected void Button2_Click(object sender, EventArgs e)
        {
            if(TextBox8.Text==TextBox9.Text && TextBox7.Text== Convert.ToString(Session["sifre"]))
            {
                Session["sifre"] = TextBox9.Text;
                kullanici nesne = new kullanici(Convert.ToInt32(Session["id"]), Convert.ToString(Session["ad"]), Convert.ToString(Session["soyad"]), Convert.ToString(Session["kullanici"]), Convert.ToString(Session["eposta"]), Convert.ToInt32(Convert.ToString(Session["telefon"])), Convert.ToString(Session["adres1"]), Convert.ToString(Session["adres2"]), TextBox9.Text);
                kuyruk.sifreDegistir(nesne);
                TextBox7.Text = "";
                TextBox8.Text = "";
                TextBox9.Text = "";
                ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "sifre();", true);



            }
            else
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "sifreUyusmuyor();", true);


            }


        }

        protected void Button3_Click(object sender, EventArgs e)
        {
            kuyruk.hesapSil(Convert.ToInt32(Session["id"]));
            Session.Contents.RemoveAll();
            ScriptManager.RegisterStartupScript(this, GetType(), "Popup", "silme();", true);
            Response.AddHeader("Refresh", "3; url=login.aspx");


        }
    }
    }