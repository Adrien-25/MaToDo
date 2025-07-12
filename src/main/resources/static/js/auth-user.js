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
/* ===============
Validation Mot de passe 
=============== */
document.addEventListener('DOMContentLoaded', () => {
  const pwd         = document.getElementById('password');
  const minLengthEl = document.getElementById('minLength');
  const upperEl     = document.getElementById('uppercase');
  const lowerEl     = document.getElementById('lowercase');
  const symbolEl    = document.getElementById('symbol');
  const errorMsgEl  = document.getElementById('errorMessage');

  const strongRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  function updateRule(element, valid, message) {
    const icon = valid
      ? '<i class="bi bi-check-circle text-success me-2"></i>'
      : '<i class="bi bi-x-circle text-danger me-2"></i>';
    element.innerHTML = icon + message;
  }

  pwd.addEventListener('input', () => {
    const val = pwd.value;

    updateRule(minLengthEl, val.length >= 8, 'Minimum 8 caractères');
    updateRule(upperEl, /[A-Z]/.test(val), 'Au moins une majuscule');
    updateRule(lowerEl, /[a-z]/.test(val), 'Au moins une minuscule');
    updateRule(symbolEl, /[@$!%*?&]/.test(val), 'Au moins un symbole (@$!%*?&)');

    if (strongRegex.test(val)) {
      errorMsgEl.textContent = 'Mot de passe solide';
      errorMsgEl.classList.remove('text-danger');
      errorMsgEl.classList.add('text-success');
    } else {
      errorMsgEl.textContent = 'Mot de passe faible';
      errorMsgEl.classList.remove('text-success');
      errorMsgEl.classList.add('text-danger');
    }
  });
});
