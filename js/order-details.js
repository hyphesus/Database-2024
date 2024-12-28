document.addEventListener('DOMContentLoaded', async function () {
    const orderDetails = document.getElementById('orderDetails');
    const params = new URLSearchParams(window.location.search);
    const orderId = params.get('id');
    const token = localStorage.getItem('token');

    if (!token) {
        orderDetails.innerHTML = '<p>Please login to view order details.</p>';
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/api/orders/${orderId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (response.ok) {
            const order = await response.json();
            let detailsHTML = `
                <h4>Order ID: ${order.id}</h4>
                <p>Status: ${order.status}</p>
                <p>Ordered At: ${new Date(order.orderedAt).toLocaleString()}</p>
                <p>Shipping Address: ${order.shippingAddress}</p>
                <p>Payment Method: ${order.paymentMethod}</p>
                <h5>Items:</h5>
                <ul>
            `;
            order.orderItems.forEach(item => {
                detailsHTML += `<li>${item.product.name} - $${item.priceAtOrderTime} x ${item.quantity}</li>`;
            });
            detailsHTML += `</ul><p>Total Amount: $${order.totalAmount}</p>`;
            orderDetails.innerHTML = detailsHTML;
        } else {
            const error = await response.text();
            orderDetails.innerHTML = `<p>Error: ${error}</p>`;
        }
    } catch (error) {
        console.error('Error:', error);
        orderDetails.innerHTML = '<p>Failed to load order details.</p>';
    }
});