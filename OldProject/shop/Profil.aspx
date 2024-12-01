<%@ Page Language="C#" Title="Profil" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Profil.aspx.cs" Inherits="shop.Profil" %>


<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
<style>


    body {
    background: rgb(99, 39, 120)
}

.form-control:focus {
    box-shadow: none;
    border-color: #BA68C8
}

.profile-button {
    background: rgb(99, 39, 120);
    box-shadow: none;
    border: none
}

.profile-button:hover {
    background: #682773
}

.profile-button:focus {
    background: #682773;
    box-shadow: none
}

.profile-button:active {
    background: #682773;
    box-shadow: none
}

.back:hover {
    color: #682773;
    cursor: pointer
}

.labels {
    font-size: 11px
}

.add-experience:hover {
    background: #BA68C8;
    color: #fff;
    cursor: pointer;
    border: solid 1px #BA68C8
}
</style>
     <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
      <script type="text/javascript">
          function silme() {
              swal({
                  title: "Başarılı",
                  text: "Başarılı bir şekilde kullanici silindi!",
                  icon: "success",
                  button: "Kapat!",
              });
          }
         function sifre() {
              swal({
                  title: "Başarılı",
                  text: "Başarılı bir şekilde şifre değiştirldi!",
                  icon: "success",
                  button: "Kapat!",
              });
         }
          function guncelleme() {
              swal({
                  title: "Başarılı",
                  text: "Başarılı bir şekilde değiştirldi!",
                  icon: "success",
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
           

  <div class="container rounded bg-white mt-5 mb-5">
    <div class="row">
        <div class="col-md-3 border-right">
            <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" src="https://static.vecteezy.com/system/resources/previews/005/129/844/original/profile-user-icon-isolated-on-white-background-eps10-free-vector.jpg"></div>
        </div>
        <div class="col-md-5 border-right">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">Profil Ayarları</h4>
                </div>
                <div class="row mt-2">
                    <div class="col-md-6"><label class="labels">Ad</label><asp:TextBox  id="TextBox1"  runat="server" type="text" class="form-control" placeholder="Ad"></asp:TextBox></div>

                    <div class="col-md-6"><label class="labels">Soyad</label><asp:TextBox id="TextBox2"  runat="server" type="text" class="form-control"  placeholder="Soyad" ></asp:TextBox></div>
                </div>
                <div class="row mt-3">
                    <div class="col-md-12"><label class="labels">E-Posta</label><asp:TextBox id="TextBox3" runat="server" type="text" class="form-control" placeholder="E-Posta"></asp:TextBox></div>
                    <div class="col-md-12"><label class="labels">Telefon Numarası</label><asp:TextBox id="TextBox4" runat="server" type="text" class="form-control" placeholder="Telefon Numarası" value=""></asp:TextBox></div>
                    <div class="col-md-12"><label class="labels">Adres 1</label><asp:TextBox id="TextBox5" runat="server" type="text" class="form-control" placeholder="Adres 1" value=""></asp:TextBox></div>
                    <div class="col-md-12"><label class="labels">Adres 2</label><asp:TextBox id="TextBox6" runat="server" type="text" class="form-control" placeholder="Adres 2" value=""></asp:TextBox></div>
                    <div class="mt-5 text-center"><asp:Button id="Button1" OnClick="Button1_Click" runat="server" class="btn btn-primary profile-button" style="margin-left:350px;" Text="Güncelle" type="button"></asp:Button></div>
                </div>
                
               
            </div>
        </div>
        <div class="col-md-4">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center experience"><span>Şifre Değiştirme</span></div><br>
                <div class="col-md-12"><label class="labels">Eski Şifre</label><asp:TextBox id="TextBox7" runat="server" type="text" class="form-control" value=""></asp:TextBox></div> <br>
                <div class="col-md-12"><label class="labels">Yeni Şifre</label><asp:TextBox id="TextBox8" runat="server" type="text" class="form-control"  value=""></asp:TextBox></div>
                <div class="col-md-12"><label class="labels">Yeni Şifre Tekarar</label><asp:TextBox id="TextBox9" runat="server" type="text" class="form-control" value=""></asp:TextBox></div>
                <div style="float:right;" class="mt-5 text-center"><asp:Button id="Button2" OnClick="Button2_Click" runat="server" style="margin-right:15px;" class="btn btn-primary profile-button" type="button" Text="Kaydet"></asp:Button></div></br></br></br></br></br>

                <div style="margin-left:25px;" class="mt-5 text-center"><asp:Button id="Button3" OnClick="Button3_Click" runat="server" style="margin-right:15px;" class="btn btn-primary profile-button" type="button" Text="Hesabı Sil"></asp:Button></div>

            </div>
        </div>
    </div>
</div>


    
    </form>
</asp:Content>