window.onload = function (){
    userLoggedIn();
    updateIncomes();
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

//Bevételek listázása
function updateIncomes(){
    let url = "/api/incomes/" + actualUser;

    fetch(url)
        .then(function (request){
            return request.json();
        })
        .then(function (jsonData){
            fillIncomes(jsonData);
        });
}

function fillIncomes(data){
    let incomesData = document.querySelector(".incomes-data");

    for(let i = 0; i < data.length; i++){
        let income = data[i];
        let div = document.createElement('div');
        div.setAttribute('class', 'income-div');
        div.innerHTML = "Date: " + income.date +"</br>"+ "Amount: " + income.value;
        incomesData.appendChild(div);
    }

}
