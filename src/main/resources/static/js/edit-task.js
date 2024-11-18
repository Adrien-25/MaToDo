document.addEventListener("DOMContentLoaded", function () {
  const taskCheckboxes = document.querySelectorAll(".task-checkbox");

  taskCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", function () {
      const taskId = this.dataset.id;

      // Envoi de la requête AJAX
      fetch(`/todo/${taskId}/toggle`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Erreur lors de la mise à jour de la tâche");
          }
          location.reload();
        })
        .catch((error) => {
          console.error("Erreur :", error);
          alert("Impossible de mettre à jour la tâche.");
        });
    });
  });
});
