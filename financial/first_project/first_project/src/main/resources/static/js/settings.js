window.onload = function (){
    userLoggedIn();
    let categoryForm = document.querySelector(".new-category-form");
    categoryForm.onsubmit = handleCategorySubmit;
    let deleteForm = document.querySelector(".delete-user-form");
    deleteForm.onsubmit = deleteUser;
    let userForm = document.querySelector("#user-form");
    userForm.onsubmit = handleEditButton;
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
               logout()
            } else {
              actualUser = user;
              fillData(actualUser);
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

//Felhasználó törlése
function deleteUser(){

    let url = "/api/users/delete/" + actualUser.username;

    if (confirm('Are you sure?')) {
        fetch(url, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json; charset=utf-8'
          },
        })
          .then(function(request) {
            return request.json()
           })
          .then(function(jsonData){
            setTimeout(function() {
                window.location.href = '/index.html'
            }, 1000);
          })
    };
}

// Profil módosítás

function fillData(user){
    let nickname = document.querySelector("#user-nickname");
    nickname.value = user.username;
    let name = document.querySelector("#user-name");
    name.value = user.name;
    }

function handleEditButton() {
  let nickName = document.querySelector("#user-nickname")
  let name = document.querySelector("#user-name");
  let password = document.querySelector("#user-password");
  let anotherPassword = document.querySelector("#user-password-second");

  let userNickName = nickName.value;
  let userName = name.value;
  let userPassword = password.value;
  let passw = anotherPassword.value;

  let data = {"name": userName, "password": userPassword, "confirmPassword": passw};
  let url = "/api/users/" + userNickName;

  fetch(url, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
    },
  body: JSON.stringify(data)
  })
    .then(function() {
       setTimeout('userLoggedIn()', 1000);
       password.value = "";
       anotherPassword.value = "";
    })
    return false;
}


