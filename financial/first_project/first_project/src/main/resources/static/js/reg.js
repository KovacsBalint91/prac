window.onload = function (){
    let registerForm = document.querySelector(".register-form");
    registerForm.onsubmit = handleSubmit;
};

function handleSubmit() {
    let userNameInput = document.getElementById("nick-name-input");
    let nameInput = document.getElementById("name-input");
    let passwordInput = document.getElementById("password-input");
    let ConfirmPasswordInput = document.getElementById("password-input-confirm");
    let userName = userNameInput.value;
    let name = nameInput.value;
    let password = passwordInput.value;
    let confirmPassword = ConfirmPasswordInput.value;

    let user = {"username": userName, "name": name, "password": password, "confirmPassword": confirmPassword};

    fetch("api/users", {
        method: "POST",
        headers: {
           "Content-Type": "application/json; charset=utf-8"
                 },
        body: JSON.stringify(user)
        })
        .then(function (request) {
            return request.json()
        })
        .then(function(jsonData) {
        let message = document.querySelector("#message-p");
        message.innerHTML = jsonData.message;
        nameInput.value = "";
        userNameInput.value ="";
        passwordInput.value="";
        ConfirmPasswordInput.value="";
    })
    return false;
}

function userLoggedIn() {
    fetch('api/user')
      .then(function (request) {
        return request.json();
      })
      .then(function (jsonData) {
      let user = jsonData;
      });
  }