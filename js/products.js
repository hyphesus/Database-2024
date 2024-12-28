document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await fetch('http://localhost:8081/api/products', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // Include JWT token if required
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });

        if (response.ok) {
            const products = await response.json();
            const productList = document.getElementById('productList');

            products.forEach(product => {
                const productCard = document.createElement('div');
                productCard.className = 'col-md-4';
                productCard.innerHTML = `
                    <div class="card mb-4 shadow-sm">
                        <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                        <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.description}</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="text-muted">$${product.price}</span>
                                <a href="product-details.html?id=${product.id}" class="btn btn-sm btn-outline-secondary">View</a>
                            </div>
                        </div>
                    </div>
                `;
                productList.appendChild(productCard);
            });
        } else {
            console.error('Failed to fetch products');
        }
    } catch (error) {
        console.error('Error:', error);
    }
});