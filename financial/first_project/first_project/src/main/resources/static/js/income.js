window.onload = function (){
    userLoggedIn();
    actualIncomes();
    allIncomes();
    actualMonth();
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
    userData.innerHTML = "Hello " + actualUser.name + "!<br/>Your balance is: " +
        (actualUser.wallet).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")
}

//Adott hónap bevételei
function actualMonth(){
    const monthNames = ["January", "February", "March", "April", "May", "June",
     "July", "August", "September", "October", "November", "December"
    ];

    let month = document.querySelector("#actual-month");
    let date = new Date();
    month.innerHTML = monthNames[date.getMonth()] + " incomes"
}

function actualIncomes(){
    let url = "/api/filteredincomes/" + actualUser;

    fetch(url)
        .then(function (request){
            return request.json();
        })
        .then(function (jsonData){
            fillActualIncomes(jsonData);
        });
}

function fillActualIncomes(data){
    let actualIncomesData = document.querySelector(".actual-incomes-data");

    let actualAmount = 0;

    for(let i = 0; i < data.length; i++){
        let income = data[i];
        actualAmount += income.value;
        let div = document.createElement('div');
        div.setAttribute('class', 'actual-income-div');
        div.innerHTML = "Date: " + income.date +"</br>"+ "Amount: " +
            (income.value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")
        actualIncomesData.appendChild(div);
    }

    document.querySelector("#actual-amount").innerHTML =
     actualAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")

}

//Összes bevétel listázása
function allIncomes(){
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

    let allAmount = 0;

    for(let i = 0; i < data.length; i++){
        let income = data[i];
        allAmount += income.value;
        let div = document.createElement('div');
        div.setAttribute('class', 'income-div');
        div.innerHTML = "Date: " + income.date +"</br>"+ "Amount: " +
            (income.value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")
        incomesData.appendChild(div);
    }

    document.querySelector("#history-amount").innerHTML =
     allAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");

}
