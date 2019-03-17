window.onload = function (){
    userLoggedIn();
    let incomeForm = document.querySelector(".income-form");
    incomeForm.onsubmit = handleIncome;
    let expenseForm = document.querySelector(".expense-form");
    expenseForm.onsubmit = handleExpense;
    listCategories();
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
              fillUserData(user);
            }
    });
}

//Felhasználó adatai
function fillUserData(actualUser){
    let userData = document.querySelector(".user-data");
    userData.innerHTML = "Hello " + actualUser.name + "!<br/>Your balance is: " +
        (actualUser.wallet).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")
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
            setTimeout(function() {
                        message.innerHTML = "";
                    }, 3000);
            userLoggedIn();
        })
        return false;
    }

// Kiadás hozzáadása
function handleExpense(){
    let expenseValue = document.querySelector("#expense-value").value;
    let expenseCategory = document.querySelector('.category-select').value;
    let expense;
    if(expenseCategory > 0){
        expense = {"value": expenseValue , "category_id": expenseCategory }
    } else {
        expense = {"value": expenseValue};
    }

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
            setTimeout(function() {
                        message.innerHTML = "";
                    }, 3000);
            userLoggedIn();
        })
        return false;
    }

//Kategóriák megjelenítése

    function listCategories(){
        fetch('/api/categories')
            .then(function(request) {
            return request.json();
            })
            .then(function(jsonData){
                let categories = document.querySelector(".category-select");
                let allCategories = document.createElement('option');
                allCategories.text = "All";
                categories.appendChild(allCategories);
                for(let i = 0; i < jsonData.length; i++){
                    let catName = jsonData[i].name;
                    let catOption = document.createElement('option');
                    catOption.setAttribute("class", "select-items");
                    catOption.value = jsonData[i].id;
                    catOption.text = catName;

                    categories.appendChild(catOption);
                }
            });
    }
