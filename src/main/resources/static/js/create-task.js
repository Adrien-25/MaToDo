document.addEventListener("DOMContentLoaded", function () {
  document
    .getElementById("create-task-form")
    .addEventListener("submit", function (e) {
      e.preventDefault(); // Empêche le rechargement de la page

      const description = document.getElementById("new-task-input").value;

      if (!description) {
        alert("Veuillez entrer une description pour la tâche.");
        return;
      }

      const formData = new URLSearchParams();
      formData.append("description", description);

      fetch("/todo", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formData.toString(),
      })
        .then((response) => {
          console.log(response);
          if (!response.ok) {
            throw new Error("Erreur lors de la création de la tâche.");
          }
          // return response.json();
          location.reload();
          console.log(response);
        })
        .catch((error) => console.error(error));
    });
});
