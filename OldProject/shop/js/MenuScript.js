
function UrunListKadin(id) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ("kadin.aspx/liste"),
        data: JSON.stringify({ KatId: id }),
        dataType: "json",
        success: function (data) {


            console.log(data);

            document.getElementById("UrunListesiBody").innerHTML = "";

            document.getElementById("UrunListesiBody").insertAdjacentHTML("beforeend", generateItem(data))



        },
        error: function (req, status, error) {
            console.log(error)
        }
    });
}



function UrunListErkek(id) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ("erkek.aspx/liste"),
        data: JSON.stringify({ KatId: id }),
        dataType: "json",
        success: function (data) {


            console.log(data);

            document.getElementById("UrunListesiBody").innerHTML = "";

            document.getElementById("UrunListesiBody").insertAdjacentHTML("beforeend", generateItem(data))



        },
        error: function (req, status, error) {
            console.log(error)
        }
    });
}



function UrunListErkekCocuk(id) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ("erkekCocuk.aspx/liste"),
        data: JSON.stringify({ KatId: id }),
        dataType: "json",
        success: function (data) {


            console.log(data);

            document.getElementById("UrunListesiBody").innerHTML = "";

            document.getElementById("UrunListesiBody").insertAdjacentHTML("beforeend", generateItem(data))



        },
        error: function (req, status, error) {
            console.log(error)
        }
    });
}

function UrunListKizCocuk(id) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ("kizCocuk.aspx/liste"),
        data: JSON.stringify({ KatId: id }),
        dataType: "json",
        success: function (data) {


            console.log(data);

            document.getElementById("UrunListesiBody").innerHTML = "";

            document.getElementById("UrunListesiBody").insertAdjacentHTML("beforeend", generateItem(data))



        },
        error: function (req, status, error) {
            console.log(error)
        }
    });
}



function UrunListGuzellik(id) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: ("guzellikUrunleri.aspx/liste"),
        data: JSON.stringify({ KatId: id }),
        dataType: "json",
        success: function (data) {


            console.log(data);

            document.getElementById("UrunListesiBody").innerHTML = "";

            document.getElementById("UrunListesiBody").insertAdjacentHTML("beforeend", generateItem(data))



        },
        error: function (req, status, error) {
            console.log(error)
        }
    });
}


function generateItem(data) {
    var veri = "";
    veri = "<div class='row product__filter'>"
    for (var i in data.d) {
        var item = data.d[i];
        var counter = 0;

        veri +=
            "<div class='col-lg-3 col-md-6 col-sm-3'> " +
            "<div class='product__item'> " +
            "<div class='product__item__pic set-bg'> " +
            "<img src='Resim/" + item.Resim + "'style='width:300px;height:300px;'>" +
            "<ul class='product__hover'> " +
            "<li><a href='#'><img src='img/icon/heart.png' alt=''></a></li> " +
            "<li><a href='#'><img src='img/icon/compare.png' alt=''> <span>Compare</span></a></li > " +
            "<li><a href='#'><img src='img/icon/search.png' alt=''></a></li> " +
            "</ul> " +
            "</div> " +
            "<div class='product__item__text'>" +
        "<a href='#Card" + item.Id + "' class='add - cart' style='font-weight:bold; width:100px; background-color:black;'>+ Sepete Ekle</a>" +
            " <h6 style='color:white;font-weight:bold; background-color:black; width:100px;'>" + item.Ad + "</h6>" +
            " <h6>Renk:" + item.Renk + "</h6>" +
            " <h6>Size:" + item.Size + "</h6>" +
            "<h5 style='padding-top:5px;'>" + item.Fiyat + "TL</h5>" +

            "</div> " +
            "</div> " +
            "</div> ";
       
    }
    veri +="</div>";
    return veri;
}




  
          




//function getdata()
//{
//    console.log(UrunList(1));
//}

//function liste(id) {
//    // Add the page method call as an onclick handler for the div.

//    $.ajax({
//        type: "POST",
//        url: "erkek.aspx/UrunList",
//        data: { kategoriId: id },
//        contentType: "application/json; charset=utf-8",
//        dataType: "json",
//        success: function (data) {
//            console.log(data);

//            //document.getElementById("footerDiv").innerHTML += generateItem(item.id,item.adi);
//            document.getElementById("Menu-Content").innerHTML = "";

//            document.getElementById("Menu-Content").insertAdjacentHTML("afterbegin", generateItem(data))
//        }
//    });
//}

//function liste() {
//    $.ajax({
//        type: 'GET',
//        url: "erkek.aspx/UrunList",
//        crossDomain: true,
//        contentType: "application/json; charset=utf-8",
//        data: { kategoriId: 1 },
//        dataType: "json",
//        async: false,
//        cache: false,
//        success: function (data) {
//            console.log(data);

//            //document.getElementById("footerDiv").innerHTML += generateItem(item.id,item.adi);
//            document.getElementById("Menu-Content").innerHTML = "";

//            document.getElementById("Menu-Content").insertAdjacentHTML("afterbegin", generateItem(data))
//        }
//    });


//}



