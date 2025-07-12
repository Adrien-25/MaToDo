function togglePasswordVisibility(inputId, toggleButton) {
  const input = document.getElementById(inputId);
  const icon = toggleButton.querySelector("i");

  if (input.type === "password") {
    input.type = "text";
    icon.classList.remove("bi-eye");
    icon.classList.add("bi-eye-slash");
  } else {
    input.type = "password";
    icon.classList.remove("bi-eye-slash");
    icon.classList.add("bi-eye");
  }
}
document.addEventListener("DOMContentLoaded", () => {
  /* ===============
  Validation de la protection du Mot de passe 
  =============== */
  const pwd = document.getElementById("password");
  const minLengthEl = document.getElementById("minLength");
  const upperEl = document.getElementById("uppercase");
  const lowerEl = document.getElementById("lowercase");
  const symbolEl = document.getElementById("symbol");
  const errorMsgEl = document.getElementById("errorMessage");
  const numberEl = document.getElementById("number");

  const strongRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  function updateRule(element, valid, message) {
    const icon = valid
      ? '<i class="bi bi-check-circle text-success me-2"></i>'
      : '<i class="bi bi-x-circle text-danger me-2"></i>';
    element.innerHTML = icon + message;
  }

  pwd.addEventListener("input", () => {
    const val = pwd.value;

    updateRule(minLengthEl, val.length >= 8, "Minimum 8 caractères");
    updateRule(upperEl, /[A-Z]/.test(val), "Au moins une majuscule");
    updateRule(lowerEl, /[a-z]/.test(val), "Au moins une minuscule");
    updateRule(numberEl, /\d/.test(val), "Au moins un chiffre");
    updateRule(
      symbolEl,
      /[@$!%*?&]/.test(val),
      "Au moins un symbole (@$!%*?&)"
    );

    if (strongRegex.test(val)) {
      errorMsgEl.textContent = "Mot de passe solide";
      errorMsgEl.classList.remove("text-danger");
      errorMsgEl.classList.add("text-success");
    } else {
      errorMsgEl.textContent = "Mot de passe faible";
      errorMsgEl.classList.remove("text-success");
      errorMsgEl.classList.add("text-danger");
    }
  });

  /* ===============
  Validation du Mot de passe de confirmation 
  =============== */
  const confirmPwd = document.getElementById("confirmPassword");
  const confirmError = document.getElementById("confirmError");

  confirmPwd.addEventListener("input", () => {
    if (confirmPwd.value === pwd.value) {
      confirmPwd.classList.remove("is-invalid");
      confirmPwd.classList.add("is-valid");
      confirmError.style.display = "none";
    } else {
      confirmPwd.classList.remove("is-valid");
      confirmPwd.classList.add("is-invalid");
      confirmError.style.display = "block";
    }
  });

  // Synchronise quand on modifie le mot de passe principal
  pwd.addEventListener("input", () => {
    if (confirmPwd.value.length > 0) {
      confirmPwd.dispatchEvent(new Event("input")); // relance la validation
    }
  });

  /* ===============
  Bloquage de l'envoie du formulaire lorsque les mdp ne correpondent pas 
  =============== */
  const form = document.getElementById("credential-signup");

  form.addEventListener("submit", (e) => {
    if (pwd.value !== confirmPwd.value) {
      e.preventDefault(); // ⛔️ Empêche la soumission du formulaire
      confirmPwd.classList.add("is-invalid");
    } else {
      confirmPwd.classList.remove("is-invalid");
    }
  });
});
