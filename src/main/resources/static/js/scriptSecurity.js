function toggleOldPasswordVisibility() {
    const passwordField = document.getElementById("oldPassword");
    const showButton = passwordField.nextElementSibling;

    if (passwordField.type === "password") {
        passwordField.type = "text";
        showButton.textContent = "Hide";
    } else {
        passwordField.type = "password";
        showButton.textContent = "Show";
    }
}

function toggleNewPasswordVisibility() {
    const passwordField = document.getElementById("newPassword");
    const showButton = passwordField.nextElementSibling;

    if (passwordField.type === "password") {
        passwordField.type = "text";
        showButton.textContent = "Hide";
    } else {
        passwordField.type = "password";
        showButton.textContent = "Show";
    }
}

function toggleConfirmPasswordVisibility() {
    const confirmPasswordField = document.getElementById("confirmPassword");
    const showButton = confirmPasswordField.nextElementSibling;

    if (confirmPasswordField.type === "password") {
        confirmPasswordField.type = "text";
        showButton.textContent = "Hide";
    } else {
        confirmPasswordField.type = "password";
        showButton.textContent = "Show";
    }
}


function deleteUser() {

    fetch('/api/user/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.getElementById('_csrf').value
        }

    }).then(response => {
        if(response.ok){
            alert('Deleted succesfully');
            window.location.href='/'
        }else{
            alert(response.status);
        }
    }).catch(error => {
        console.error('ERROR: ',error);
    });
}