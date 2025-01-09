document.addEventListener("DOMContentLoaded", () => {
  const taskList = document.querySelector(".d-flex.flex-column.gap-3");
  const completedTasksContainer = document.createElement("details");
  completedTasksContainer.id = "completed-tasks";
  completedTasksContainer.className = "d-flex flex-column gap-3";
  completedTasksContainer.innerHTML = `
    <summary class='mb-2 mt-4 h6'>Complété</summary>
    <div class="completed-tasks-list d-flex flex-column gap-3"></div>
    `;
  taskList.parentNode.insertBefore(
    completedTasksContainer,
    taskList.nextSibling
  );

  // Référence au conteneur interne pour les tâches complétées
  const completedTasksList = completedTasksContainer.querySelector(
    ".completed-tasks-list"
  );

  function moveCompletedTasks() {
    const tasks = document.querySelectorAll(".task-card");
    tasks.forEach((task) => {
      const checkbox = task.querySelector(".task-checkbox");
      if (checkbox.checked) {
        completedTasksList.appendChild(task);
      } else {
        taskList.appendChild(task);
      }
    });
  }

  document.querySelectorAll(".task-checkbox").forEach((checkbox) => {
    checkbox.addEventListener("change", () => {
      moveCompletedTasks();
    });
  });
  moveCompletedTasks();
});
