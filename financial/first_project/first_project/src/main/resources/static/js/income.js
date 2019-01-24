window.onload = function (){
    userLoggedIn();
    let incomeForm = document.querySelector(".income-form");
    incomeForm.onsubmit = handleIncome;
    let expenseForm = document.querySelector(".expense-form");
    expenseForm.onsubmit = handleExpense;
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
              fillUserData();
            }
    });
}

//Felhasználó adatai
function fillUserData(){
    let userData = document.querySelector(".user-data");
    userData.innerHTML = "Hello " + actualUser.name + "!<br/>Your balance is: " + actualUser.wallet;
}

// Bevétel hozzáadása
function handleIncome(){
    let incomeValue = document.querySelector("#input-value").value;

    let income = {"value": incomeValue};

    let url = '/api/addincome/' + actualUser.username;
    fetch(url,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
            },
        body: JSON.stringify(income)
        })
        .then(function (request){
            return request.json()
        })
        .then(function(jsonData){
            document.querySelector("#input-value").value = "";
            let message = document.querySelector("#income-message");
            message.innerHTML = jsonData.message;
            userLoggedIn();
        })
        return false;
    }

// Kiadás hozzáadása
function handleExpense(){
    let expenseValue = document.querySelector("#expense-value").value;

    let expense = {"value": expenseValue};

    let url = '/api/spend/' + actualUser.username;
    fetch(url,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
            },
        body: JSON.stringify(expense)
        })
        .then(function (request){
            return request.json()
        })
        .then(function(jsonData){
            document.querySelector("#expense-value").value = "";
            let message = document.querySelector("#expense-message");
            message.innerHTML = jsonData.message;
            userLoggedIn();
        })
        return false;
    }

