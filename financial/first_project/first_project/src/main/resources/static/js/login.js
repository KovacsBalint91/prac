window.onload = function () {

    let url = window.location.href;
    console.log(url);
    let title = document.querySelector('title');
    let message = document.querySelector('.message');
    if (url === 'http://localhost:8080/login.html?logout') {
        title.innerHTML = 'Sikeres kijelentkezés - EastWest';
        message.innerHTML = '<i class="fas fa-exclamation-triangle symbol"></i><br>Sikeres kijelentkezés...';
    }

    if (url === 'http://localhost:8080/login.html?error') {
        title.innerHTML = 'Sikertelen bejelentkezés - EastWest';
        message.setAttribute('class', 'message-error');
        message.innerHTML = '<i class="fas fa-exclamation-triangle symbol"></i><br>Sikertelen bejelentkezés...';
    }
  };