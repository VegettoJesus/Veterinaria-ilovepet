document.addEventListener("DOMContentLoaded", function() {
    let btn = document.querySelector("#btn_menu");
    let sidebar = document.querySelector(".sidebar");

    // Verificar si el botón y el sidebar existen antes de establecer el evento onclick
    if (btn && sidebar) {
        btn.onclick = function() {
            sidebar.classList.toggle("active");
        }
    } else {
        console.log("");
    }
});
