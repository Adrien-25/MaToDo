document.querySelectorAll("#settingsTabs button").forEach((btn) => {
  btn.addEventListener("click", () => {
    // Enlever "active" de tous les boutons
    document
      .querySelectorAll("#settingsTabs .nav-link")
      .forEach((link) => link.classList.remove("active"));
    btn.classList.add("active");

    // Masquer toutes les sections
    document
      .querySelectorAll(".settings-section")
      .forEach((section) => section.classList.add("d-none"));

    // Afficher la section ciblée
    const sectionId = "section-" + btn.dataset.section;
    document.getElementById(sectionId).classList.remove("d-none");
  });
});
  // if (document.getElementById("current-list-id") !== null) {

document.addEventListener("DOMContentLoaded", () => {
  const baseSection = document.getElementById("account-base");
  const emailForm = document.getElementById("account-edit-email");
  const passForm = document.getElementById("account-edit-pass");

  // Boutons d'édition
  document.getElementById("editMail").addEventListener("click", () => {
    baseSection.classList.add("d-none");
    emailForm.classList.remove("d-none");
  });

  document.getElementById("editPassword").addEventListener("click", () => {
    baseSection.classList.add("d-none");
    passForm.classList.remove("d-none");
  });

  // Boutons d'annulation
  document.getElementById("backFromEmailBtn").addEventListener("click", () => {
    emailForm.classList.add("d-none");
    baseSection.classList.remove("d-none");
  });

  document.getElementById("backFromPassBtn").addEventListener("click", () => {
    passForm.classList.add("d-none");
    baseSection.classList.remove("d-none");
  });
});
