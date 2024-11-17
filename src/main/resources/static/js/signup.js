// document
//   .getElementById("create-task-form")
//   .addEventListener("submit", function (e) {
//     e.preventDefault(); // Empêche le rechargement de la page

//     const description = document.getElementById("new-task-description").value;

//     fetch("/todo", {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//       },
//       body: JSON.stringify({ description }),
//     })
//       .then((response) => {
//         if (response.ok) {
//           return response.json();
//         }
//         throw new Error("Erreur lors de la création de la tâche");
//       })
//       .then((task) => {
//         // Ajoute la tâche au DOM
//         addTaskToDOM(task);
//         document.getElementById("new-task-description").value = ""; // Réinitialise le champ
//       })
//       .catch((error) => console.error(error));
//   });
