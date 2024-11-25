function showPassword() {
    // Change input type to 'text' to show the password
    document.getElementById('password').type = 'text';
    document.getElementById("visibility-icon").textContent = "visibility";
}

function hidePassword() {
    // Change input type back to 'password' to hide it
    document.getElementById('password').type = 'password';
    document.getElementById("visibility-icon").textContent = "visibility_off";
}