function togglePasswordVisibility() {
    const passwordField = document.getElementById("password");
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
