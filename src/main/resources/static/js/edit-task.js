// document.addEventListener("DOMContentLoaded", function () {
//   const taskCheckboxes = document.querySelectorAll(".task-checkbox");
//   const csrfToken = document.querySelector('input[name="_csrf"]').value;

//   taskCheckboxes.forEach((checkbox) => {
//     checkbox.addEventListener("change", function () {
//       const taskId = this.dataset.id;

//       // Envoi de la requête AJAX
//       fetch(`/todo/${taskId}/toggle`, {
//         method: "PATCH",
//         headers: {
//           "Content-Type": "application/json",
//           "X-XSRF-TOKEN": csrfToken,
//         },
//       })
//         .then((response) => {
//           if (!response.ok) {
//             throw new Error("Erreur lors de la mise à jour de la tâche");
//           }
//         })
//         .catch((error) => {
//           console.error("Erreur :", error);
//           alert("Impossible de mettre à jour la tâche.");
//         });
//     });
//   });
// });
