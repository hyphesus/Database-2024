// Base URL of your backend API
const API_BASE_URL = 'http://localhost:8080/api/products'; // Adjust the port if different

// Function to fetch products from the backend
async function fetchProducts() {
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const products = await response.json();
        return products;
    } catch (error) {
        console.error('Error fetching products:', error);
        return [];
    }
}

// Function to create a product card HTML
function createProductCard(product) {
    const safePrice = typeof product.price === 'number'
        ? product.price
        : 0;
    return `
        <div class="col-lg-3 col-md-6 col-sm-12 pb-1">
            <div class="card product-item border-0 mb-4">
                <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                    <img class="img-fluid w-100" src="${product.image_url}" alt="${product.name}">
                </div>
                <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                    <h6 class="text-truncate mb-3">${product.name}</h6>
                    <div class="d-flex justify-content-center">
                        <h6>$${safePrice.toFixed(2)}</h6>
                        <!-- Remove references to original_price altogether -->
                    </div>
                </div>
                <div class="card-footer d-flex justify-content-between bg-light border">
                    <a href="detail.html?id=${product.id}" class="btn btn-sm text-dark p-0">
                        <i class="fas fa-eye text-primary mr-1"></i>View Detail
                    </a>
                    <a href="cart.html?add=${product.id}" class="btn btn-sm text-dark p-0">
                        <i class="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart
                    </a>
                </div>
            </div>
        </div>
    `;
}

// Function to render products in a given container
function renderProducts(products, containerId) {
    const container = document.getElementById(containerId);
    if (!container) {
        console.error(`Container with ID '${containerId}' not found.`);
        return;
    }

    if (products.length === 0) {
        container.innerHTML = '<p class="text-center w-100">No products available.</p>';
        return;
    }

    const productCards = products.map(product => createProductCard(product)).join('');
    container.innerHTML = productCards;
}

// Main function to initialize product rendering
async function init() {
    const products = await fetchProducts();

    // Display all products in "Trandy Products" section
    renderProducts(products, 'trendy-products');

    // Display the latest 8 products in "Just Arrived" section
    const justArrived = products.slice(-8).reverse(); // Assuming the last 8 are the latest
    renderProducts(justArrived, 'just-arrived-products');
}

// Initialize on DOMContentLoaded
document.addEventListener('DOMContentLoaded', init);
