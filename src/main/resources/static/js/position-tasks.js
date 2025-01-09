document.addEventListener("DOMContentLoaded", () => {
  const tasks = document.querySelectorAll(".draggable");
  let draggedElement = null;

  tasks.forEach((task) => {
    const grip = task.querySelector(".bi-grip-vertical");
    const status = task.dataset.status;

    grip.addEventListener("dragstart", (event) => {
      draggedElement = task;
      event.dataTransfer.setData("text/plain", task.dataset.id);
      task.classList.add("dragging");
    });

    grip.addEventListener("dragend", () => {
      updateAllTaskPositions();

      draggedElement.classList.remove("dragging");
      draggedElement = null;
    });

    task.addEventListener("dragover", (event) => {
      event.preventDefault();
      const bounding = task.getBoundingClientRect();
      const offset = event.clientY - bounding.top + window.scrollY;

      if (canDrop(draggedElement, task)) {
        if (offset > bounding.height / 2) {
          task.parentNode.insertBefore(draggedElement, task.nextSibling);
        } else {
          task.parentNode.insertBefore(draggedElement, task);
        }
      }
    });

    const taskId = task.dataset.id;
    const listId = task.dataset.listId;
    const newPosition = task.dataset.position;
  });

  function canDrop(draggedTask, targetTask) {
    const draggedStatus = draggedTask.dataset.status;
    const targetStatus = targetTask.dataset.status;

    // Autoriser le drop seulement si les statuts sont identiques
    return draggedStatus === targetStatus;
  }
  
  function updateAllTaskPositions() {
    const tasks = document.querySelectorAll(".draggable");
    const totalTasks = tasks.length;

    tasks.forEach((task, index) => {
      const newPosition = totalTasks - index;
      task.dataset.position = newPosition;

      if (task.classList.contains("dragging")) {
        const taskId = task.dataset.id;
        const listId = task.dataset.listId;

        updateTaskPositions(taskId, listId, newPosition);
      }
    });
  }

  function updateTaskPositions(taskId, listId, newPosition) {
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    const currentListId = document.getElementById("current-list-id").value;

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
