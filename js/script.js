document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("casillas-container");
    const colores = ["rojo", "naranja", "amarillo", "verde", "azul", "morado", "rosa"];
    const totalCasillas = 49; // 5 de cada color
    const numRepeticiones = 7;
    const radioExterno = 218; // Distancia desde el centro
    const radioTriangulos = 90;
    const centroX = 267; // Centro del tablero (mitad de 500px)
    const centroY = 267;

  // Definimos el patrón de colores para las casillas manualmente
    const casillasColores = [
        "morado", "verde", "blanco", "naranja", "rosa", "blanco", "azul", // Primera sección
        "rojo", "morado", "blanco", "amarillo", "verde", "blanco", "naranja", // Segunda sección
        "azul", "rojo", "blanco", "rosa", "morado", "blanco", "amarillo", // Tercera sección
        "naranja", "azul", "blanco", "verde", "rojo", "blanco", "rosa", // Cuarta sección
        "amarillo", "naranja", "blanco", "morado", "azul", "blanco", "verde", // Quinta sección
        "rosa", "amarillo", "blanco", "rojo", "naranja", "blanco", "morado", // Sexta sección
        "verde", "rosa", "blanco", "azul", "amarillo", "blanco", "rojo", // Última sección, colores completos
    ];

    // Asegúrate de que el número de casillas sea el correcto
    if (casillasColores.length !== totalCasillas) {
        console.error("El número de colores no coincide con el total de casillas");
        return;
    }

    // Crear las casillas basadas en el patrón definido
    for (let i = 0; i < casillasColores.length; i++) {
        const casilla = document.createElement("div");
        casilla.classList.add("casilla");

        const color = casillasColores[i];

        // Si la casilla es blanca, no agregamos color, solo el fondo blanco
        if (color === "blanco") {
            casilla.classList.add("blanca");
            casilla.style.backgroundColor = "white";
            casilla.textContent = "🎲";
        } else if (i % 7 === 0) { // Casilla especial
            casilla.classList.add("especial");
            casilla.classList.add(color);
            casilla.textContent = "★"; // Estrella para diferenciarla
        } else {
            casilla.classList.add("normal");
            casilla.classList.add(color);
        }

        // Calcular la posición de la casilla
        const angle = (i * 360 / totalCasillas) * (Math.PI / 180); // Convertir grados a radianes
        const x = centroX + Math.cos(angle) * radioExterno - 30; 
        const y = centroY + Math.sin(angle) * radioExterno - 30;

        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

        // Agregar las casillas al contenedor
        container.appendChild(casilla);
    }
  
  // Quesitos internos
    for (let i = 0; i < 7; i++) {
    const colores = ["morado", "rojo", "azul", "naranja", "amarillo", "rosa", "verde"];
    const color = colores[i]; 
    const angle = (i * 360 / 7); // Cada 360/7 grados
    const radianes = angle * (Math.PI / 180); // Convertir a radianes

    const x = centroX + Math.cos(radianes) * radioTriangulos - 93;
    const y = centroY + Math.sin(radianes) * radioTriangulos - 92;

    const casilla = document.createElement("div");
    casilla.classList.add("quesito", color);
    casilla.style.left = `${x}px`;
    casilla.style.top = `${y}px`;

    // Rotar para que apunte al centro
    casilla.style.transform = `rotate(${angle - 90}deg)`;

    container.appendChild(casilla);
}
});

document.addEventListener("DOMContentLoaded", () => {
    const dado = document.getElementById("dado");
    const resultadoDado = document.getElementById("resultado-dado");

    dado.addEventListener("click", () => {
        const numeroAleatorio = Math.floor(Math.random() * 6) + 1; // Número entre 1 y 6
        resultadoDado.textContent = `${numeroAleatorio}`;
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const dado = document.getElementById("dado");
    const resultadoDado = document.getElementById("resultado-dado");
    const ficha = document.getElementById("ficha");
    const casillas = document.querySelectorAll(".casilla"); // Todas las casillas del tablero
    let posicionFicha = 0; // Índice de la casilla donde está la ficha
  const ordenCasillas = [  
    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
    19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34,
    35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48
];

    // Colocar la ficha en la primera casilla
    function posicionarFicha(index) {
    if (index < ordenCasillas.length) {
        const casillaIndex = ordenCasillas[index]; // Obtener la casilla en el orden personalizado
        const casilla = casillas[casillaIndex]; // Buscar la casilla en la lista general
        ficha1.style.left = `${casilla.offsetLeft + 5}px`; // Ajustar posición
        ficha1.style.top = `${casilla.offsetTop + 5}px`;
    }
}

    // Iniciar la ficha en la primera casilla
    posicionarFicha(posicionFicha);

    // Evento al hacer clic en el dado
   dado.addEventListener("click", () => {
    const numeroAleatorio = Math.floor(Math.random() * 6) + 1;
    resultadoDado.textContent = `${numeroAleatorio}`;

    // Mostrar los botones para elegir dirección
    document.getElementById("controles").style.display = "block";

    // Desactivar el dado temporalmente para evitar otro lanzamiento
    dado.disabled = true;

    // Esperar a que el jugador elija la dirección antes de mover
    document.getElementById("flechaIzquierda").addEventListener("click", () => moverFicha(-1, numeroAleatorio));
    document.getElementById("flechaDerecha").addEventListener("click", () => moverFicha(1, numeroAleatorio));
});
  
  let fichaIntervalo = null; // Variable global para almacenar el intervalo

  function moverFicha(direccion, pasos) {
    let movimientos = 0;
    document.getElementById("controles").style.display = "none"; // Ocultar controles

    // Limpiar cualquier intervalo previo antes de iniciar uno nuevo
    clearInterval(fichaIntervalo);

    // Crear un nuevo intervalo para mover la ficha paso a paso
    fichaIntervalo = setInterval(() => {
        if (movimientos < pasos) {
            // Mover la ficha una sola casilla por iteración
            posicionFicha = (posicionFicha + direccion + ordenCasillas.length) % ordenCasillas.length;
            posicionarFicha(posicionFicha);
            movimientos++;
        } else {
            clearInterval(fichaIntervalo); // Detener el intervalo cuando se alcance el número de pasos
            dado.disabled = false; // Reactivar el dado para la siguiente tirada
        }
    }, 500); // ⏳ Controlamos la velocidad de movimiento
}
});