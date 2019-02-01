window.onload = function (){
    userLoggedIn();
    let categoryForm = document.querySelector(".new-category-form");
    categoryForm.onsubmit = handleCategorySubmit;

};

let actualUser;

//Bejelentkezett felhasználó
function userLoggedIn() {
  fetch('/api/user')
    .then(function (request) {
      return request.json();
    })
    .then(function (jsonData) {
      let user = jsonData;

      if (user.username === null) {
              let resultDiv = document.getElementById('result');
              resultDiv.innerHTML = 'Az oldal megtekintéséhez <a href="/register.html">regisztráció</a> vagy <a href="/login.html">belépés</a> szükséges.';
              let userFormDiv = document.getElementById('user-form');
              userFormDiv.style.display = 'none';
              let userDataDiv = document.querySelector('.user-data');
              userDataDiv.style.display = 'none';
            } else {
              actualUser = user;
            }
    });
}


//Új kategória felvétele
function handleCategorySubmit() {
    let newCategoryInput = document.querySelector("#new-category");
    let categoryName = newCategoryInput.value;


    let data = {"name": categoryName};
    let url = "api/category/add/" + categoryName;

    fetch(url, {
        method: "POST",
        headers: {
           "Content-Type": "application/json; charset=utf-8"
                 },
        body: JSON.stringify(data)
        })
        .then(function (request) {
            return request.json()
        })
        .then(function(jsonData) {
        let message = document.querySelector("#message-p");
        message.innerHTML = jsonData.message;
        newCategoryInput.value = "";
        setTimeout(function() {
            message.innerHTML = "";
        }, 3000);
    })
    return false;
}

