using shop.Sinfilar;
using shop.Database;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Web.Services;

namespace shop
{
    public partial class erkek : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }



        [WebMethod]
        public static List<Urunler> liste(int KatId)
        {

            querry kuyruk = new querry();

            List<Urunler> urunlist = new List<Urunler>();

            urunlist = kuyruk.urunListesi(KatId);

            return urunlist;
        }

    }
}