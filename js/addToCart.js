document.addEventListener('DOMContentLoaded', function () {
    const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');

    addToCartButtons.forEach(button => {
        button.addEventListener('click', async function () {
            const productId = this.getAttribute('data-id');
            const quantity = 1; // Default quantity

            // Get token
            const token = localStorage.getItem('token');
            if (!token) {
                alert('Please login to add items to your cart.');
                return;
            }

            const data = {
                productId: parseInt(productId),
                quantity: quantity
            };

            try {
                const response = await fetch('http://localhost:8080/api/cart/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    const result = await response.json();
                    alert('Product added to cart.');
                } else {
                    const error = await response.text();
                    alert('Error: ' + error);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Failed to add product to cart.');
            }
        });
    });
});