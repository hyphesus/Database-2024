document.addEventListener('DOMContentLoaded', () => {
    console.log('listener.js loaded and DOM fully parsed');

    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');

    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }

    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }
});

async function handleRegister(event) {
    event.preventDefault();
    console.log('handleRegister triggered');

    const form = event.target;
    const data = {
        username: form.username.value.trim(),
        password: form.password.value.trim(),
        email: form.email.value.trim(),
        fullName: form.fullName.value.trim()
    };

    console.log('Registration data:', data);

    try {
        const response = await fetch('http://localhost:8080/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || 'Registration failed.');
        }

        console.log('Registration successful:', result);
        alert('Registration successful!');
        // Optionally, redirect to login page
        window.location.href = 'login.html';
    } catch (error) {
        console.error('Registration error:', error);
        alert(`Registration failed: ${error.message}`);
    }
}

async function handleLogin(event) {
    event.preventDefault();
    console.log('handleLogin triggered');

    const form = event.target;
    const data = {
        username: form.username.value.trim(),
        password: form.password.value.trim()
    };

    console.log('Login data:', data);

    try {
        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || 'Login failed.');
        }

        console.log('Login successful:', result);
        alert('Login successful!');
        // Store JWT token
        localStorage.setItem('token', result.token);
        // Redirect to dashboard or homepage
        window.location.href = 'index.html';
    } catch (error) {
        console.error('Login error:', error);
        alert(`Login failed: ${error.message}`);
    }
}