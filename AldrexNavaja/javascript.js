// ============================================
// MOBILE NAVIGATION
// ============================================

const menuToggle =
    document.getElementById("menuToggle");

const navigation =
    document.getElementById("navigation");


menuToggle.addEventListener("click", function () {

    navigation.classList.toggle("active");

    if (navigation.classList.contains("active")) {

        menuToggle.textContent = "✕";

        menuToggle.setAttribute(
            "aria-label",
            "Close navigation"
        );

    } else {

        menuToggle.textContent = "☰";

        menuToggle.setAttribute(
            "aria-label",
            "Open navigation"
        );

    }

});


// ============================================
// CLOSE MENU AFTER CLICKING LINK
// ============================================

const navLinks =
    document.querySelectorAll(
        ".navigation a"
    );

navLinks.forEach(function (link) {

    link.addEventListener(
        "click",
        function () {

            navigation.classList.remove(
                "active"
            );

            menuToggle.textContent = "☰";

            menuToggle.setAttribute(
                "aria-label",
                "Open navigation"
            );

        }
    );

});


// ============================================
// CURRENT YEAR
// ============================================

const year =
    document.getElementById("year");

year.textContent =
    new Date().getFullYear();


// ============================================
// HEADER SHADOW ON SCROLL
// ============================================

const header =
    document.querySelector(".header");

window.addEventListener(
    "scroll",
    function () {

        if (window.scrollY > 30) {

            header.style.boxShadow =
                "0 5px 25px rgba(0, 0, 0, 0.08)";

        } else {

            header.style.boxShadow =
                "none";

        }

    }
);