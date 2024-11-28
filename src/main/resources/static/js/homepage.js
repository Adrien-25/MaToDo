// Sélection des éléments
const toggleButton = document.getElementById("toggle-sidebar");
const sidebar = document.getElementById("sidebar");

// Ajouter un écouteur d'événement au bouton
toggleButton.addEventListener("click", () => {
  sidebar.classList.toggle("close"); // Ajouter ou retirer la classe 'open'
});