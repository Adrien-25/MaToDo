document.addEventListener("DOMContentLoaded", () => {
  const tasks = document.querySelectorAll(".draggable");
  let draggedElement = null;

  tasks.forEach((task) => {
    // Démarrage du drag
    task.addEventListener("dragstart", (event) => {
      draggedElement = task;
      event.dataTransfer.setData("text/plain", task.dataset.id);
      task.classList.add("dragging");
    });

    // Fin du drag
    task.addEventListener("dragend", () => {
      // Recalculer les positions après le drag-and-drop
      updateAllTaskPositions();

      draggedElement.classList.remove("dragging");
      draggedElement = null;
    });

    // Autoriser le drop sur d'autres éléments
    task.addEventListener("dragover", (event) => {
      event.preventDefault();
      const bounding = task.getBoundingClientRect();
      const offset = event.clientY - bounding.top + window.scrollY;

      // Vérifie si on est avant ou après
      if (offset > bounding.height / 2) {
        task.parentNode.insertBefore(draggedElement, task.nextSibling);
      } else {
        task.parentNode.insertBefore(draggedElement, task);
      }
    });

    const taskId = task.dataset.id;
    const listId = task.dataset.listId;
    const newPosition = task.dataset.position;

    // Gestion du drop
    // task.addEventListener("drop", (e) => {
    //   updateTaskPositions(taskId, listId, newPosition);
    // });
  });

  // Fonction pour mettre à jour toutes les positions
  function updateAllTaskPositions() {
    const tasks = document.querySelectorAll(".draggable");
    console.log("list de taches" + tasks);

    tasks.forEach((task, index) => {
      // Mettre à jour la position dans l'attribut dataset
      task.dataset.position = index + 1;

      // Si c'est l'élément déplacé, envoyer les données au backend
      if (task.classList.contains("dragging")) {
        console.log("dragging" + task);
        const taskId = task.dataset.id;
        const listId = task.dataset.listId;
        const newPosition = index + 1;

        updateTaskPositions(taskId, listId, newPosition);
        // updateTaskPosition(taskId, listId, newPosition);
      }
    });
  }

  function updateTaskPositions(taskId, listId, newPosition) {
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    const currentListId = document.getElementById("current-list-id").value;

    console.log("send new positions");

    const formData = new URLSearchParams();
    formData.append("task_list_id", currentListId);
    formData.append("taskId", taskId);
    formData.append("newPosition", newPosition);

    // Envoi des données au serveur
    fetch(`/move/${taskId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        "X-XSRF-TOKEN": csrfToken,
      },
      body: formData.toString(),
    })
      .then((response) => {
        if (response.ok) {
          console.log(`Position de la tâche ${taskId} mise à jour !`);
        } else {
          console.error("Erreur lors de la mise à jour de la position");
        }
      })
      .catch((error) => console.error("Erreur : ", error));
  }
});
