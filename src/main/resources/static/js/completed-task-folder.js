document.addEventListener("DOMContentLoaded", () => {
  const taskList = document.querySelector(".d-flex.flex-column.gap-3");
  const completedTasksContainer = document.createElement("details");
  completedTasksContainer.id = "completed-tasks";
  completedTasksContainer.className = "d-flex flex-column gap-3";
  completedTasksContainer.innerHTML =
    "<summary class='mb-2 mt-3 h5'>Complété</summary>";
  taskList.parentNode.insertBefore(
    completedTasksContainer,
    taskList.nextSibling
  );

  function moveCompletedTasks() {
    const tasks = document.querySelectorAll(".task-card");
    tasks.forEach((task) => {
      const checkbox = task.querySelector(".task-checkbox");
      if (checkbox.checked) {
        completedTasksContainer.appendChild(task);
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
