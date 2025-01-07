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

    const taskId = taskElement.dataset.id;
    const listId = taskElement.dataset.listId;
    // const newPosition = Array.from(taskElement.parentElement.children).indexOf(
    //   taskElement
    // );

    // Gestion du drop
    task.addEventListener("drop", (e) => {
      updateTaskPositions(taskId, listId, newPosition);
    });
  });

  function updateTaskPositions(taskId, listId, newPosition) {
    // const taskId = taskElement.dataset.id;
    // const listId = taskElement.dataset.listId; 

    // Envoi des données au serveur
    fetch(`/move/${taskId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        listId: listId,
        taskId: taskId,
        newPosition: newPosition,
      }),
    })
      .then((response) => {
        if (response.ok) {
          console.log(`Position de la tâche ${taskId} mise à jour !`);
        } else {
          console.error("Erreur lors de la mise à jour de la position");
        }
      })
      .catch((error) => console.error("Erreur : ", error));
    // const tasks = document.querySelectorAll(".draggable");
    // const updatedPositions = Array.from(tasks).map((task, index) => ({
    //   taskId: task.dataset.id,
    //   listId: index + 1,
    //   newPosition: index + 1,
    // }));

    // // Envoi des nouvelles positions au serveur
    // fetch(`/move/${taskId}`, {
    //   method: "POST",
    //   headers: {
    //     "Content-Type": "application/json",
    //   },
    //   body: JSON.stringify(updatedPositions),
    // })
    //   .then((response) => {
    //     if (response.ok) {
    //       console.log("Positions mises à jour !");
    //     } else {
    //       console.error("Erreur lors de la mise à jour des positions");
    //     }
    //   })
    //   .catch((error) => console.error("Erreur : ", error));
  }
});
