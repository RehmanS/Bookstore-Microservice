const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const loginButton = document.getElementById('loginButton')
const loginUsername = document.getElementById('loginUsername')
const loginPassword = document.getElementById('loginPassword')
const loginMessage = document.getElementById('loginMessage')
const registerUsername = document.getElementById('registerUsername')
const registerPassword = document.getElementById('registerPassword')
const registerMessage = document.getElementById('registerMessage')
const registerButton = document.getElementById('registerButton')

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

loginButton.addEventListener('click', function () {
    login()
})

registerButton.addEventListener('click', function () {
    register()
})

function login ()
{
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:11880/auth/login',
        data: JSON.stringify({
            "username": loginUsername.value,
            "password": loginPassword.value
        }),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            window.localStorage.token = response.accessToken;
            window.localStorage.username = loginUsername.value
            window.location.href = 'index.html'
        },
        error: function () {
            loginMessage.innerText = "Password is incorrect!";
        }
    })
}

function register ()
{
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:11880/auth/register',
        data: JSON.stringify({
            "username": registerUsername.value,
            "password": registerPassword.value
        }),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            window.localStorage.token = response.accessToken;
            window.localStorage.username = loginUsername.value
            window.location.href = 'index.html'
        },
        error: function (response) {
            registerMessage.innerText = 'Username is already in use...';
        }
    })
}