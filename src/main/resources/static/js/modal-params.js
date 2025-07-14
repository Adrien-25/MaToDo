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
