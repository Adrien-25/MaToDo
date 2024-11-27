// document.addEventListener("DOMContentLoaded", () => {
//   const addListBtn = document.getElementById("add-list-btn");
//   const listNameInput = document.getElementById("list-name");
//   const errorElement = document.getElementById("list-name-error");
//   const csrfToken = document.querySelector('input[name="_csrf"]').value;

//   addListBtn.addEventListener("click", () => {
//     const listName = listNameInput.value.trim();

//     // Vérifiez si le champ est vide
//     if (!listName) {
//       errorElement.style.display = "block";
//       return;
//     }

//     errorElement.style.display = "none";

//     // Envoyer les données au backend
//     fetch("/task-lists", {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//         "X-XSRF-TOKEN": csrfToken,
//       },
//       body: JSON.stringify({ name: listName }),
//     })
//       .then((response) => {
//         if (!response.ok) {
//           throw new Error("Erreur lors de la création de la liste");
//         }
//         // return response.json();
//       })
//       .then((data) => {
//         console.log("Liste créée :", data);
//         // Fermez le modal
//         const modal = bootstrap.Modal.getInstance(
//           document.querySelector(".modal-create-list")
//         );
//         modal.hide();

//         // Réinitialisez le formulaire
//         listNameInput.value = "";
//       })
//       .catch((error) => {
//         console.error("Erreur :", error);
//         alert("Une erreur est survenue lors de la création de la liste.");
//       });
//   });
// });
