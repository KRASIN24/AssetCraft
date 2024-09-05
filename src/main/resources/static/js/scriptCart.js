

function calculateTotal(){

    let total = 0;

    const checkboxes = document.querySelectorAll('.product-checkbox')
    checkboxes.forEach(function(checkbox){
        if(checkbox.checked){
            total += parseFloat(checkbox.getAttribute('data-price'));
        }
    });

    document.getElementById('totalPrice').innerText = total.toFixed(2);
}

const productCheckboxes = document.querySelectorAll('.product-checkbox');
productCheckboxes.forEach(function(checkbox){
    checkbox.addEventListener('change', calculateTotal)
});


function getSelectedProductsIds(){
    const selectedProductIds = [];
    document.querySelectorAll('.product-checkbox:checked').forEach(checkbox => {
        selectedProductIds.push(checkbox.value);
    });
    return selectedProductIds;
}

document.getElementById('cartForm').addEventListener('submit', function(event){
    event.preventDefault();

    const selectedProductIds = getSelectedProductsIds();
    if(selectedProductIds.length === 0){
        alert('No products selected');
        return;
    }

    fetch('/updateCart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.getElementById('_csrf').value
        },
        body: JSON.stringify({productIds : selectedProductIds})
    }).then(response => {
        if(response.ok){

            window.location.href = '/shop';
        }else{
            alert('Failed');
            alert(response.status);
        }
    }).catch(error => {
        console.error('ERROR: ',error);
    });
});

function removeProduct(productId){

    fetch('/cart/remove', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.getElementById('_csrf').value
        },
        body: JSON.stringify({productId : productId})
    }).then(response => {
        if(response.ok){
            window.location.reload();
        }else{
            alert('Failed');
            alert(response.status);
        }
    }).catch(error => {
        console.error('ERROR: ',error);
    });
}