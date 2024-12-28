document.addEventListener('DOMContentLoaded', async function () {
    const cartContainer = document.getElementById('cartContainer');
    const token = localStorage.getItem('token');

    if (!token) {
        cartContainer.innerHTML = '<p>Please login to view your cart.</p>';
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/cart/view', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (response.ok) {
            const cart = await response.json();
            // Render cart items
            // Example rendering logic
            let cartHTML = '<h3>Your Cart</h3>';
            cart.items.forEach(item => {
                cartHTML += `
                    <div class="cart-item">
                        <p>${item.product.name} - $${item.priceAtAddTime} x ${item.quantity}</p>
                    </div>
                `;
            });
            cartHTML += `<p>Total: $${cart.totalAmount}</p>`;
            cartContainer.innerHTML = cartHTML;
        } else {
            const error = await response.text();
            cartContainer.innerHTML = `<p>Error: ${error}</p>`;
        }
    } catch (error) {
        console.error('Error:', error);
        cartContainer.innerHTML = '<p>Failed to load cart.</p>';
    }
});