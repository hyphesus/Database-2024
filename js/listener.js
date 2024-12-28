// listener.js

document.addEventListener('DOMContentLoaded', function () {
    const loginLink = document.getElementById('login');
    const registerLink = document.getElementById('register');
    const cartButton = document.querySelectorAll('.fa-shopping-cart');
    const viewDetailButtons = document.querySelectorAll('.fa-eye');

    // Check if user is logged in
    const token = localStorage.getItem('token');
    if (token) {
        // Optionally, you can decode the token to get user info
        // For simplicity, we'll just change the navbar links
        loginLink.style.display = 'none';
        registerLink.style.display = 'none';

        // Create a Logout button
        const logoutButton = document.createElement('a');
        logoutButton.href = '#';
        logoutButton.className = 'nav-item nav-link';
        logoutButton.id = 'logout';
        logoutButton.innerText = 'Logout';
        logoutButton.style.cursor = 'pointer';

        // Append Logout button to the navbar
        const navbarNav = registerLink.parentElement;
        navbarNav.appendChild(logoutButton);

        // Handle Logout
        logoutButton.addEventListener('click', function () {
            localStorage.removeItem('token');
            window.location.href = 'index.html';
        });
    }

    // Handle Add to Cart Buttons
    cartButton.forEach(function (button) {
        button.parentElement.addEventListener('click', async function (e) {
            e.preventDefault();
            const productId = getProductId(button);
            if (!productId) {
                alert('Product ID not found.');
                return;
            }

            if (!token) {
                alert('Please login to add items to your cart.');
                window.location.href = 'login.html';
                return;
            }

            try {
                const response = await fetch('http://localhost:8080/api/cart/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify({ productId, quantity: 1 })
                });

                const result = await response.json();

                if (response.ok) {
                    alert('Product added to cart successfully!');
                    // Optionally, update cart count badge
                    updateCartCount();
                } else {
                    alert(result.message || 'Failed to add product to cart.');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred. Please try again later.');
            }
        });
    });

    // Handle View Detail Buttons
    viewDetailButtons.forEach(function (button) {
        button.parentElement.addEventListener('click', function (e) {
            e.preventDefault();
            const productId = getProductId(button);
            if (!productId) {
                alert('Product ID not found.');
                return;
            }

            // Redirect to product detail page with productId as a query parameter
            window.location.href = `detail.html?productId=${productId}`;
        });
    });

    // Handle Subscribe Form Submission
    const subscribeForm = document.getElementById('subscribeForm');
    if (subscribeForm) {
        subscribeForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            const name = document.getElementById('subscriberName').value;
            const email = document.getElementById('subscriberEmail').value;

            try {
                const response = await fetch('http://localhost:8080/api/subscribe', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ name, email })
                });

                const result = await response.json();

                const messageDiv = document.getElementById('subscribeMessage');
                if (response.ok) {
                    messageDiv.style.color = 'green';
                    messageDiv.innerText = 'Subscribed successfully!';
                    subscribeForm.reset();
                } else {
                    messageDiv.style.color = 'red';
                    messageDiv.innerText = result.message || 'Subscription failed. Please try again.';
                }
            } catch (error) {
                console.error('Error:', error);
                const messageDiv = document.getElementById('subscribeMessage');
                messageDiv.style.color = 'red';
                messageDiv.innerText = 'An error occurred. Please try again later.';
            }
        });
    }

    // Utility Functions
    function getProductId(button) {
        // Assuming each product card has a data-product-id attribute
        const card = button.closest('.card');
        return card ? card.getAttribute('data-product-id') : null;
    }

    function updateCartCount() {
        // Fetch cart details and update the cart count badge
        fetch('http://localhost:8080/api/cart', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => {
                const cartBadge = document.querySelector('.fa-shopping-cart + .badge');
                if (cartBadge) {
                    cartBadge.innerText = data.totalItems || 0;
                }
            })
            .catch(error => console.error('Error:', error));
    }
});
