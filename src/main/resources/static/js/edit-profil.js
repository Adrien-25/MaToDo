document.addEventListener("DOMContentLoaded", () => {
  /*
  ===================================================================
  MODIFER PROFIL
  */

  // EDIT EMAIL
  document.getElementById("editEmail").addEventListener("click", changeEmail);

  function changeEmail() {
    console.log("Changement email");
    const emailDiv = document.getElementById("editEmail");
    const originalEmailValue = emailDiv.textContent.trim();
    const inputEmail = document.createElement("input");
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    inputEmail.type = "text";
    inputEmail.value = originalEmailValue;
    inputEmail.className = "form-control input-edit-profil text-end";
    emailDiv.replaceWith(inputEmail);
    inputEmail.focus();

    const saveChanges = () => {
      const newEmailValue = inputEmail.value.trim();

      // Revenir à l'affichage si la valeur n'a pas changé
      if (newEmailValue === originalEmailValue) {
        inputEmail.replaceWith(emailDiv);
        return;
      }

      const formData = new URLSearchParams();
      formData.append("value", newEmailValue);
      formData.append('field', 'email'); 


      // Envoyer les nouvelles données au serveur (AJAX)
      fetch(`/req/profil`, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "X-XSRF-TOKEN": csrfToken,
        },
        body: formData.toString(),
      })
        .then((response) => {
          if (response.ok) {
            // Mise à jour réussie, afficher la nouvelle valeur
            emailDiv.textContent = newValue;
            inputEmail.replaceWith(emailDiv);
          } else {
            alert("Erreur lors de la mise à jour. Veuillez réessayer.");
            inputEmail.replaceWith(emailDiv);
          }
        })
        .catch((error) => {
          console.error("Erreur réseau :", error);
          alert("Erreur réseau. Veuillez réessayer.");
          inputEmail.replaceWith(element);
        });
    };
    inputEmail.addEventListener("blur", saveChanges);
    inputEmail.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        saveChanges();
      }
    });
  }

  // EDIT USERNAME

  document
    .getElementById("editUsername")
    .addEventListener("click", changeUsername);

  function changeUsername() {
    // Logique pour changer l'email
    console.log("Changement username");
    const usernameDiv = document.getElementById("editUsername");
    const originalUsernameValue = usernameDiv.textContent.trim();
    const inputUsername = document.createElement("input");
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    inputUsername.type = "text";
    inputUsername.value = originalUsernameValue;
    inputUsername.className = "form-control input-edit-profil text-end";
    usernameDiv.replaceWith(inputUsername);
    inputUsername.focus();

    const saveChanges = () => {
      const newUsernameValue = inputUsername.value.trim();

      // Revenir à l'affichage si la valeur n'a pas changé
      if (newUsernameValue === originalUsernameValue) {
        inputUsername.replaceWith(usernameDiv);
        return;
      }

      const formData = new URLSearchParams();
      formData.append("value", newUsernameValue);
      formData.append('field', 'username'); 

      // Envoyer les nouvelles données au serveur (AJAX)
      fetch(`/req/profil`, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "X-XSRF-TOKEN": csrfToken,
        },
        body: formData.toString(),
      })
        .then((response) => {
          if (response.ok) {
            // Mise à jour réussie, afficher la nouvelle valeur
            usernameDiv.textContent = newValue;
            inputUsername.replaceWith(usernameDiv);
          } else {
            alert("Erreur lors de la mise à jour. Veuillez réessayer.");
            inputUsername.replaceWith(usernameDiv);
          }
        })
        .catch((error) => {
          console.error("Erreur réseau :", error);
          alert("Erreur réseau. Veuillez réessayer.");
          inputUsername.replaceWith(element);
        });
    };
    inputUsername.addEventListener("blur", saveChanges);
    inputUsername.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        saveChanges();
      }
    });
  }
});
