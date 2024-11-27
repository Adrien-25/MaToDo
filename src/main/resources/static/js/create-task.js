document.addEventListener("DOMContentLoaded", function () {
  document
    .getElementById("create-task-form")
    .addEventListener("submit", function (e) {
      e.preventDefault(); // Empêche le rechargement de la page

      const description = document.getElementById("new-task-input").value;
      const csrfToken = document.querySelector('input[name="_csrf"]').value;
      const taskListId = document.querySelector('input[name="taskListId"]').value;
      const defaultCategory = 1;
      // const taskListId = 2;

      if (!description) {
        alert("Veuillez entrer une description pour la tâche.");
        return;
      }

      const formData = new URLSearchParams();
      formData.append("description", description);
      formData.append("categoryId", defaultCategory);
      formData.append("task_list_id", taskListId);

      fetch("/todo", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "X-XSRF-TOKEN": csrfToken,
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
