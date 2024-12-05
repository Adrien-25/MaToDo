document.addEventListener("DOMContentLoaded", function () {
  document
    .querySelector(".edit-pass-form form")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const modal = document.querySelector("#editProfileModal");
      const form = modal.querySelector(".edit-pass-form form");
      const notifDiv = modal.querySelector(".info-request-notif");

      modal.addEventListener("hidden.bs.modal", function () {
        form.reset(); // Réinitialise tous les champs du formulaire
        notifDiv.textContent = ""; // Vide le texte de notification
        notifDiv.classList.remove("text-success", "text-danger");
      });

      const formData = new FormData(event.target);
      const formObject = Object.fromEntries(formData);

      try {
        // Envoi de la requête AJAX
        const response = await fetch(event.target.action, {
          method: "POST",
          headers: {
            Accept: "application/json",
            "X-CSRF-TOKEN": formObject._csrf, // Ajout du token CSRF si nécessaire
          },
          body: new URLSearchParams(formData),
        });

        if (!response.ok) {
          throw new Error("Une erreur s'est produite lors de la modification.");
        }

        
        notifDiv.classList.remove("text-success", "text-danger");
        if (response.ok) {
          // const result = await response.text();
          form.reset();
          notifDiv.classList.add("text-success");
          notifDiv.textContent = "Mot de passe modifié avec succès !";
        } else {
          const result = await response.text();

          notifDiv.classList.add("text-danger");
          notifDiv.textContent = result;
        }
      } catch (error) {
        console.error(error);
        notifDiv.textContent = "Une erreur est survenue. Veuillez réessayer plus tard.";
      }
    });
});
