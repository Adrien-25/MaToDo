document.addEventListener("DOMContentLoaded", () => {
  // Sélectionner tous les éléments modifiables
  const editableElements = document.querySelectorAll(".editable");
  const csrfToken = document.querySelector('input[name="_csrf"]').value;
  const currentListId = document.getElementById("current-list-id").value;

  editableElements.forEach((element) => {
    element.addEventListener("click", () => {
      const taskId = element.dataset.id; // ID de la tâche
      const originalValue = element.textContent.trim();

      // Remplacer le span par un input
      const input = document.createElement("input");
      input.type = "text";
      input.value = originalValue;
      input.className = "form-control input-edit-desc";

      // Remplacer le contenu
      element.replaceWith(input);

      // Focus sur l'input
      input.focus();

      // Sauvegarder les modifications
      const saveChanges = () => {
        const newValue = input.value.trim();

        // Revenir à l'affichage si la valeur n'a pas changé
        if (newValue === originalValue) {
          input.replaceWith(element);
          return;
        }

        const formData = new URLSearchParams();
        formData.append("description", newValue);
        formData.append("task_list_id", currentListId);

        // Envoyer les nouvelles données au serveur (AJAX)
        fetch(`/todo/${taskId}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "X-XSRF-TOKEN": csrfToken,
          },
          // body: JSON.stringify({
          //   description: newValue,
          //   task_list_id: currentListId,
          // }),
          body: formData.toString(),
        })
          .then((response) => {
            if (response.ok) {
              // Mise à jour réussie, afficher la nouvelle valeur
              element.textContent = newValue;
              input.replaceWith(element);
            } else {
              alert("Erreur lors de la mise à jour. Veuillez réessayer.");
              input.replaceWith(element);
            }
          })
          .catch((error) => {
            console.error("Erreur réseau :", error);
            alert("Erreur réseau. Veuillez réessayer.");
            input.replaceWith(element);
          });
      };

      // Sauvegarder au blur (perte de focus)
      input.addEventListener("blur", saveChanges);

      // Sauvegarder au keypress (Entrée)
      input.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
          saveChanges();
        }
      });
    });
  });
});