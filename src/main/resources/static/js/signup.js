document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.querySelector('section');
    const signupButton = document.querySelector('#submit-signup');
    if (signupButton) {
      console.log(signupButton);
      signupButton.addEventListener('click', function () {
        const emailInput = document.querySelector('input[type="email"]');
        const passwordInput = document.querySelector('input[type="password"]');
        const confirmPasswordInput = document.querySelector('input[type="password"][name="confirm-password"]');
    
        // Check for a valid email and password (you can add your validation logic here)
        const isValid = emailInput.checkValidity() && passwordInput.checkValidity() && confirmPasswordInput.checkValidity();
    
        if (!isValid) {
          signupForm.classList.add('shake');
    
          setTimeout(() => {
            signupForm.classList.remove('shake');
          }, 1000);
        }
      });
    } 
  });
  