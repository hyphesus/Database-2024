<%@ Page Title="Login" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="login.aspx.cs" Inherits="shop.login" %>


<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
      <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
      <script type="text/javascript">
          function hataPopup() {
              swal({
                  title: "HATA",
                  text: "Böyle bir kullanıcı zaten var!",
                  icon: "error",
                  button: "Kapat!",
              });
          }
          function basariliPopup() {
              swal({
                  title: "Başarılı",
                  text: "Başarılı bir şekilde kaydoldunuz!",
                  icon: "success",
                  button: "Kapat!",
              });
          }
          function basariliGiris() {
              swal({
                  title: "Başarılı",
                  text: "Hoş Geldiniz...",
                  icon: "success",
                  button: "Kapat!",
              });
          }
          function yanlisGiris() {
              swal({
                  title: "HATA",
                  text: "Şifre Veya Kullanici Adi Uyuşmuyor!",
                  icon: "error",
                  button: "Kapat!",
              });
          }
          function sifreUyusmuyor() {
              swal({
                  title: "HATA",
                  text: "Şifreler Uyuşmuyor Ya Da Boş Veri Var!",
                  icon: "error",
                  button: "Kapat!",
              });
          }
      </script>
    <form id="form1" runat="server">
    <section class="vh-100" style="background-color: aliceblue;">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="card shadow-2-strong" style="border-radius: 5%;">
          <div class="card-body p-5 text-center">

                   
            

          <div class="block text main-agileits" style="margin-left:100px;">
            <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                <li class="nav-item text-center">
                  <a class="nav-link active btl" id="pills-home-tab" data-toggle="pill" href="#pills-home" role="tab" aria-controls="pills-home" aria-selected="true">

                        
                            <h2 style="margin-bottom:5px; text-align:center; font-size:25px;" >
                                Giriş
                            </h2>

                  </a>
                </li>
                <li class="nav-item text-center">
                  <a class="nav-link btr" id="pills-profile-tab" data-toggle="pill" href="#pills-profile" role="tab" aria-controls="pills-profile" aria-selected="false">

                      <div class="heading_container">
                            <h2 style="margin-bottom:5px; text-align:center; font-size:25px;" >
                                 Kaydol
                            </h2>
                        </div>
                  </a>
                </li>
               
              </ul>
        </div>
              <div class="tab-content" id="pills-tabContent"  style="height:450px;">
                <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab" >
                   <div class="form-group">
                       <img src="img/banner.png" height="200px"/ >
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="text1" runat="server" placeholder="Kullanıcı Adı"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="text2" runat="server" placeholder="Sifre"></asp:TextBox>
                     <asp:Button style="margin-top:10px;" ID="Button1" class="btn btn-primary btn-lg btn-block"  runat="server" OnClick="UyeGiris_Click" Text="Giriş Yap" />
                    </div>
                </div>
                <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">
                   <div class="form-group">
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox1" runat="server" placeholder="Ad"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox2" runat="server" placeholder="Soyad"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox3" runat="server" placeholder="Kullanıcı Adı"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox4" runat="server" placeholder="Eposta"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox5" runat="server" placeholder="Telefon Numarası"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox6" runat="server" placeholder="Adres"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:5px;" class="form-control" ID="TextBox7" runat="server" placeholder="Şifre"></asp:TextBox>
                     <asp:TextBox style="margin-bottom:10px;" class="form-control" ID="TextBox8" runat="server" placeholder="Şifre Tekrar Tekrarı"></asp:TextBox>
                     <asp:Button ID="Button2" class="btn btn-lg btn-block btn-primary" style="background-color: #dd4b39;"  runat="server" OnClick="Kaydol_Click" Text="Kaydol" />


                   </div>
                </div>
            </div>



              </div>
        </div>
      </div>
    </div>
  </div>
</section>
    </form>
</asp:Content>