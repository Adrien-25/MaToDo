
document
  .querySelector(".edit-pass-form form")
  .addEventListener("submit", async function (event) {
    event.preventDefault(); // Empêche la soumission classique du formulaire

    // Récupérer les données du formulaire
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

      const result = await response.text();

      console.log(result);
      // Vérification de la réponse
      if (result.ok) {
        // alert("Le mot de passe a été modifié avec succès !");
        var notifDiv = document.querySelector(".info-request-notif");
        notifDiv.classList.add("text-success");
        notifDiv.textContent = result;
      } else {
        // alert(result);
        // document.querySelector(".info-request-notif").textContent=result;
        var notifDiv = document.querySelector(".info-request-notif");
        notifDiv.classList.add("text-danger");
        notifDiv.textContent = result;
      }
    } catch (error) {
      console.error(error);
      alert("Une erreur est survenue. Veuillez réessayer plus tard.");
    }
  });
