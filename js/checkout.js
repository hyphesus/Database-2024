document.getElementById('checkoutForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const shippingAddress = document.getElementById('shippingAddress').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const discountCode = document.getElementById('discountCode').value;

    // Get token and user ID
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const userUsageCount = await getUserDiscountUsageCount(userId, discountCode);

    if (!token) {
        alert('Please login to place an order.');
        return;
    }

    // Get cart ID (assuming you have a way to retrieve the current cart)
    const cartId = getCurrentCartId(); // Replace with actual function to retrieve cart ID

    const data = {
        cartId: cartId,
        shippingAddress: shippingAddress,
        paymentMethod: paymentMethod,
        discountCode: discountCode,
        userId: userId,
        userUsageCount: userUsageCount
    };

    try {
        const response = await fetch('http://localhost:8081/api/orders/place', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const order = await response.json();
            document.getElementById('checkoutMessage').innerText = 'Order placed successfully!';
            // Optionally, redirect to order confirmation page
            window.location.href = `order-confirmation.html?id=${order.id}`;
        } else {
            const error = await response.text();
            document.getElementById('checkoutMessage').innerText = 'Error: ' + error;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('checkoutMessage').innerText = 'Failed to place order.';
    }
});