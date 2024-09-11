function removeImage(imageId){

    fetch('/api/product/removeImage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.getElementById('_csrf').value
        },
        body: JSON.stringify({imageId : imageId})
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