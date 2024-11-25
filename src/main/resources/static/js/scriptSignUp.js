function togglePasswordVisibility() {
    const passwordField = document.getElementById("password");
    const showButton = passwordField.nextElementSibling;

    if (passwordField.type === "password") {
        passwordField.type = "text";
        document.getElementById("visibility-icon").textContent = "visibility_off";
    } else {
        passwordField.type = "password";
        document.getElementById("visibility-icon").textContent = "visibility";
    }
}

function toggleConfirmPasswordVisibility() {
    const confirmPasswordField = document.getElementById("confirmPassword");
    const showButton = confirmPasswordField.nextElementSibling;

    if (confirmPasswordField.type === "password") {
        confirmPasswordField.type = "text";
        document.getElementById("visibility-icon-confirm").textContent = "visibility_off";
    } else {
        confirmPasswordField.type = "password";
        document.getElementById("visibility-icon-confirm").textContent = "visibility";
    }
}
