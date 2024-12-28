document.addEventListener('DOMContentLoaded', async function () {
    const orderList = document.getElementById('orderList');
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId'); // Retrieve the user's ID

    if (!token) {
        orderList.innerHTML = '<p>Please login to view your orders.</p>';
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/api/orders/user/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (response.ok) {
            const orders = await response.json();
            if (orders.length === 0) {
                orderList.innerHTML = '<p>You have no orders.</p>';
                return;
            }
            orders.forEach(order => {
                const orderDiv = document.createElement('div');
                orderDiv.className = 'order';
                orderDiv.innerHTML = `
                    <h4>Order ID: ${order.id}</h4>
                    <p>Status: ${order.status}</p>
                    <p>Ordered At: ${new Date(order.orderedAt).toLocaleString()}</p>
                    <p>Total Amount: $${order.totalAmount}</p>
                    <a href="order-details.html?id=${order.id}" class="btn btn-sm btn-outline-secondary">View Details</a>
                    <hr/>
                `;
                orderList.appendChild(orderDiv);
            });
        } else {
            const error = await response.text();
            orderList.innerHTML = `<p>Error: ${error}</p>`;
        }
    } catch (error) {
        console.error('Error:', error);
        orderList.innerHTML = '<p>Failed to load orders.</p>';
    }
});