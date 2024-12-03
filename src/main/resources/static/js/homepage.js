document.addEventListener("DOMContentLoaded", () => {
  /* 
  ===================================================================
  TOGGLE SIDEBAR
  */
  const toggleButton = document.getElementById("toggle-sidebar");
  const sidebar = document.getElementById("sidebar");
  const closeButton = document.getElementById("close-sidebar");
  toggleButton.addEventListener("click", () => {
    sidebar.classList.toggle("close");
  });
  closeButton.addEventListener("click", () => {
    sidebar.classList.toggle("close");
  });

  /* 
  ===================================================================
  SUBMIT CHECKBOX FORM
  */
  const taskCheckboxes = document.querySelectorAll(".task-checkbox");
  taskCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", function () {
      const formId = `todoForm-${this.dataset.id}`;
      const form = document.getElementById(formId);
      form.submit();
    });
  });

  /* 
  ===================================================================
  PARAMETRAGE DU FORMULAIRE DE MODIFICATION D'UNE LISTE
  */
  const renameButtons = document.querySelectorAll(
    "[data-bs-target='#editList']"
  );
  const form = document.getElementById("edit-list-form");
  const inputName = document.getElementById("edit-list-name");

  renameButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const listId = button.getAttribute("data-id");
      const listName = button
        .querySelector(".list-name-data")
        .getAttribute("data-name");
      const clickedTaskId = document.querySelector('#edit-list-form  input[name="task_list_id"');
      clickedTaskId.value=listId;

      form.action = `/task-lists/${listId}`;
      inputName.value = listName;
    });
  });
});
