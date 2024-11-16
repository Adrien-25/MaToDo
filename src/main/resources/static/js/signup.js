// document.addEventListener("DOMContentLoaded", function () {
//   const signupForm = document.querySelector("section");
//   const signupButton = document.querySelector("#submit-signup");
//   if (signupButton) {
//     signupButton.addEventListener("click", function (e) {
//       e.preventDefault();

//       // CHECK INPUT VALIDITY
//       const emailInput = document.querySelector('input[type="email"]');
//       const passwordInput = document.querySelector('input[type="password"]');
//       const confirmPasswordInput = document.querySelector(
//         'input[type="password"][name="confirm-password"]'
//       );
//       const isValid =
//         emailInput.checkValidity() &&
//         passwordInput.checkValidity() &&
//         confirmPasswordInput.checkValidity();

//       if (!isValid) {
//         signupForm.classList.add("shake");

//         setTimeout(() => {
//           signupForm.classList.remove("shake");
//         }, 1000);
//       }

//       // Post form
//       const username = document.getElementById("username").value;
//       const password = document.getElementById("password").value;
//       const confirmPassword = document.getElementById("passwordcon").value;
//       const email = document.getElementById("email").value;
//       const data = {
//         username,
//         email,
//         password,
//       };
//       if (password == confirmPassword) {
//         const jsonData = JSON.stringify(data);
//         fetch("/req/signup", {
//           method: "POST",
//           headers: {
//             "Content-Type": "application/json",
//           },
//           body: jsonData,
//         }).then((response) => {
//           //alert('succesfull');
//           //if(response.status == 200){
//           //alert('succesfull');
//           //xs}
//         });
//       }
//     });
//   }
// });
