using shop.Database;
using shop.Sinfilar;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace shop
{
    public partial class kizCocuk : System.Web.UI.Page
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