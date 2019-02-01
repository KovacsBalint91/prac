window.onload = function (){
    userLoggedIn();
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
        (actualUser.wallet).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

//Adott hónap kiadásai
function actualMonth(){
    const monthNames = ["January", "February", "March", "April", "May", "June",
     "July", "August", "September", "October", "November", "December"
    ];

    let month = document.querySelector("#actual-month");
    let date = new Date();
    month.innerHTML = monthNames[date.getMonth()] + " spending";

    actualSpends();
}

function actualSpends(){
    let url = "/api/filteredspends/" + actualUser;

    fetch(url)
        .then(function (request){
            return request.json();
        })
        .then(function (jsonData){
            fillActualSpends(jsonData);
        });
}

function fillActualSpends(data){
    let actualSpendsData = document.querySelector(".actual-expenses-data");

    let actualAmount = 0;

    for(let i = 0; i < data.length; i++){
        let spend = data[i];
        actualAmount += spend.value;
        let div = document.createElement('div');
        div.setAttribute('id', 'actual-spend-div' + i);
        div.setAttribute('class', 'actual-spend-div');
        div.innerHTML = "Date: " + spend.date +"</br>"+ "Amount: " +
            (spend.value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
        actualSpendsData.appendChild(div);

        categoryIdToName(spend.category_id, i, 'actual-spend-div');
    }

    document.querySelector("#actual-amount").innerHTML =
     actualAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");

}


//Kiadások listázása
let myDiv  = document.querySelector(".expenses-data");
myDiv.onclick = function(){
    while(myDiv.firstChild) {
        myDiv.removeChild(myDiv.firstChild);
    }
    updateExpenses();
    myDiv.onclick = null;
    };


function updateExpenses(){
    let url = "/api/spendlist";

    fetch(url)
        .then(function (request){
            return request.json();
        })
        .then(function (jsonData){
            fillExpenses(jsonData);
        });
}

function fillExpenses(data){
    let expensesData = document.querySelector(".expenses-data");

    let allSpends = 0;

    for(let i = 0; i < data.length; i++){
        let expense = data[i];
        allSpends += expense.value;
        let div = document.createElement('div');
        div.setAttribute('id', 'expense-div' + i);
        div.setAttribute('class', 'expense-div');
        div.innerHTML = "Date: " + expense.date +"</br>"
            + "Amount: " + (expense.value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
        expensesData.appendChild(div);

        categoryIdToName(expense.category_id, i, 'expense-div');
    }

    document.querySelector("#history-amount").innerHTML =
         allSpends.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");

}

function categoryIdToName(categoryId, i, myClass){
    let url = '/api/category/' + categoryId;

    fetch(url, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(categoryId)
    })
    .then(function(request){
        return request.json();
    })
    .then(function(jsonData){
        if(myClass === 'expense-div'){
        addCategoryToMyDiv(jsonData.name, i);
        } else {
        addCategoryToActualDiv(jsonData.name, i);
        }
    });
    return false;
}

function addCategoryToMyDiv(categoryName, i){
    document.querySelector("#expense-div" + i).innerHTML += '</br>' + 'Category: ' + categoryName;
}

function addCategoryToActualDiv(categoryName, i){
    document.querySelector("#actual-spend-div" + i).innerHTML += '</br>' + 'Category: ' + categoryName;
}