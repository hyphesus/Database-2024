// Login form işlemi
function handleLogin(event) {
    event.preventDefault(); // Varsayılan form gönderimini engelle

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
        alert('Lütfen tüm alanları doldurun!');
        return;
    }

    // POST isteği
    fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: username, password: password })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(error => Promise.reject(error));
            }
        })
        .then(data => {
            alert('Giriş başarılı: ' + JSON.stringify(data));
        })
        .catch(error => {
            alert('Hata: ' + error.message);
            console.error('Giriş isteği hatası:', error);
        });
}

// Register form işlemi
function handleRegister(event) {
    event.preventDefault(); // Varsayılan form gönderimini engelle

    const fullName = document.getElementById('fullName').value.trim();
    const username = document.getElementById('username').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!fullName || !email || !password || !username) {
        alert('Lütfen tüm alanları doldurun!');
        return;
    }

    // POST isteği
    fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ fullName: fullName, email: email, username: username, password: password })

    })
        .then(response => {
            if (!response.ok) {
                throw response;
            }
            return response.json();
        })
        .then(data => {
            alert('Kayıt başarılı: ' + JSON.stringify(data));
        })
        .catch(error => {
            alert('Hata: ' + error.message);
            console.error('Kayıt isteği hatası:', error);
        });
}

// Formlar için dinleyicileri ekle
document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }
});