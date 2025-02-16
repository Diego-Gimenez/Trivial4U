document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("casillas-container");
    const totalCasillas = 49;
    const numRepeticiones = 7;
    const radioExterno = 218;
    const radioTriangulos = 90;
    const centroX = 267;
    const centroY = 267;

  // Definimos el patrón de colores para las casillas manualmente
    const casillasColores = [
        "morado", "verde", "blanco", "naranja", "rosa", "blanco", "azul", // Primera sección
        "rojo", "morado", "blanco", "amarillo", "verde", "blanco", "naranja", // Segunda sección
        "azul", "rojo", "blanco", "rosa", "morado", "blanco", "amarillo", // Tercera sección
        "naranja", "azul", "blanco", "verde", "rojo", "blanco", "rosa", // Cuarta sección
        "amarillo", "naranja", "blanco", "morado", "azul", "blanco", "verde", // Quinta sección
        "rosa", "amarillo", "blanco", "rojo", "naranja", "blanco", "morado", // Sexta sección
        "verde", "rosa", "blanco", "azul", "amarillo", "blanco", "rojo", // Séptima sección
    ];
  
   const ordenCasillas = [  
    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
    19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34,
    35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48];

    // Asegúrate de que el número de casillas sea el correcto
    if (casillasColores.length !== totalCasillas) {
        console.error("El número de colores no coincide con el total de casillas");
        return;
    }
  
    const casillas = [];

    // Crear las casillas basadas en el patrón definido
    for (let i = 0; i < totalCasillas; i++) {
        const casilla = document.createElement("div");
        casilla.classList.add("casilla");
        const color = casillasColores[i];

        // Si la casilla es blanca, no agregamos color, solo el fondo blanco
        if (color === "blanco") {
            casilla.classList.add("blanca");
            casilla.style.backgroundColor = "white";
            casilla.textContent = "🎲";
        } else if (i % 7 === 0) { // Casilla especial
            casilla.classList.add("especial", color);
            casilla.textContent = "★";
        } else {
            casilla.classList.add("normal", color);
        }

        // Calcular la posición de la casilla
        const angle = (i * 360 / totalCasillas) * (Math.PI / 180);
        const x = centroX + Math.cos(angle) * radioExterno - 30; 
        const y = centroY + Math.sin(angle) * radioExterno - 30;

        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

        // Agregar las casillas al contenedor
        container.appendChild(casilla);
        casillas.push(casilla);
    }
  
  // Quesitos internos
  for (let i = 0; i < 7; i++) {
    const colores = ["morado", "rojo", "azul", "naranja", "amarillo", "rosa", "verde"];
    const color = colores[i]; 
    const angle = (i * 360 / 7);
    const radianes = angle * (Math.PI / 180);

    const x = centroX + Math.cos(radianes) * radioTriangulos - 93;
    const y = centroY + Math.sin(radianes) * radioTriangulos - 92;

    const casilla = document.createElement("div");
    casilla.classList.add("quesito", color);
    casilla.style.left = `${x}px`;
    casilla.style.top = `${y}px`;
    casilla.style.transform = `rotate(${angle - 90}deg)`;

    container.appendChild(casilla);
}
  
    const fichas = {
        1: document.getElementById("ficha1"),
        2: document.getElementById("ficha2")
    };
  
    let turnos = { 1: 0, 2: 0 };
    let turnoActual = 1;
  
    function posicionarFicha(jugador, index) {
        if (index < totalCasillas) {
            const casilla = casillas[index];
            fichas[jugador].style.left = `${casilla.offsetLeft + 5}px`;
            fichas[jugador].style.top = `${casilla.offsetTop + 5}px`;
            fichas[jugador].dataset.posicion = index;
        }
    }
  
    posicionarFicha(1, 0);
    posicionarFicha(2, 0);
  
    document.getElementById("dado1").addEventListener("click", () => lanzarDado(1));
    document.getElementById("dado2").addEventListener("click", () => lanzarDado(2));

    function lanzarDado(jugador) {
        if (jugador !== turnoActual) return;
        const resultado = Math.floor(Math.random() * 6) + 1;
        document.getElementById(`resultadoDado${jugador}`).textContent = resultado;
        document.getElementById("controles").style.display = "block";
        dado1.disabled = true;
        document.getElementById("flechaIzquierda").addEventListener("click", () => moverFicha(jugador, -1, resultado));
        document.getElementById("flechaDerecha").addEventListener("click", () => moverFicha(jugador, 1, resultado));
    }
  
    let fichaIntervalo = null;

    function moverFicha(jugador, direccion, pasos) {
    let movimientos = 0;
    document.getElementById("controles").style.display = "none"; // Ocultar controles

    let ficha = fichas[jugador]; // Obtener la ficha correspondiente
    let posicionActual = parseInt(ficha.dataset.posicion || "0");

    // Limpiar cualquier intervalo previo antes de iniciar uno nuevo
    clearInterval(fichaIntervalo);

    // Crear un nuevo intervalo para mover la ficha paso a paso
    fichaIntervalo = setInterval(() => {
        if (movimientos < pasos) {
            // Mover la ficha una casilla por iteración
            posicionActual = (posicionActual + direccion + ordenCasillas.length) % ordenCasillas.length;
            ficha.dataset.posicion = posicionActual;

            let casillaNueva = casillas[posicionActual];
            ficha.style.left = `${casillaNueva.offsetLeft + 5}px`;
            ficha.style.top = `${casillaNueva.offsetTop + 5}px`;

            movimientos++;
        } else {
            clearInterval(fichaIntervalo); // Detener el intervalo al completar los pasos
            dado1.disabled = false;
            dado2.disabled = false; 

            // Cambiar turno
            turnoActual = turnoActual === 1 ? 2 : 1;
        }
    }, 500); // ⏳ Controla la velocidad del movimiento
}
});