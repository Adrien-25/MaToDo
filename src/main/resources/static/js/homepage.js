// Sélection des éléments
const toggleButton = document.getElementById("toggle-sidebar");
const sidebar = document.getElementById("sidebar");

// Ajouter un écouteur d'événement au bouton
toggleButton.addEventListener("click", () => {
  sidebar.classList.toggle("close"); // Ajouter ou retirer la classe 'open'
});

//   const form = document.getElementById(`todoForm-${id}`);
//   form.submit();  // Soumet le formulaire lorsqu'on coche ou décoche la case

// inputIsComplete
const taskCheckboxes = document.querySelectorAll(".task-checkbox");
console.log(taskCheckboxes);
taskCheckboxes.forEach((checkbox) => {
  checkbox.addEventListener("change", function () {
    // Récupère l'ID du formulaire associé à cette checkbox
    const formId = `todoForm-${this.dataset.id}`;
    console.log(formId);
    // Soumet le formulaire associé
    const form = document.getElementById(formId);
    console.log(form);

    form.submit();
  });
});
