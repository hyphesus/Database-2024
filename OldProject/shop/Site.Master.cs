using shop.Sinfilar;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using shop.Database;
using System.Collections;
using System.Drawing;
using System.Threading.Tasks;

namespace shop
{
    public partial class SiteMaster : MasterPage
    {

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void cikis(object sender, EventArgs e)
        {

            Session.Contents.RemoveAll();
            Response.Redirect("Login.aspx");
        }
    }




    
}