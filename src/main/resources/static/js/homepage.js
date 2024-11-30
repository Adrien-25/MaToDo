// Sélection des éléments
const toggleButton = document.getElementById("toggle-sidebar");
const sidebar = document.getElementById("sidebar");
const closeButton = document.getElementById("close-sidebar");

// Ajouter un écouteur d'événement au bouton
toggleButton.addEventListener("click", () => {
  sidebar.classList.toggle("close"); // Ajouter ou retirer la classe 'open'
});
closeButton.addEventListener("click", () => {
  sidebar.classList.toggle("close"); // Ajouter ou retirer la classe 'open'
});

//   const form = document.getElementById(`todoForm-${id}`);
//   form.submit();  // Soumet le formulaire lorsqu'on coche ou décoche la case

// inputIsComplete
const taskCheckboxes = document.querySelectorAll(".task-checkbox");
taskCheckboxes.forEach((checkbox) => {
  checkbox.addEventListener("change", function () {
    const formId = `todoForm-${this.dataset.id}`;
    const form = document.getElementById(formId);
    form.submit();
  });
});

document.addEventListener("DOMContentLoaded", () => {
  // Sélectionner les boutons "Renommer" et le formulaire du modal
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

      form.action = `/task-lists/${listId}`;
      inputName.value = listName;
    });
  });
});
