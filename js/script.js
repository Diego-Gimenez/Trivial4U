document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("casillas-container");
    const colores = ["rojo", "naranja", "amarillo", "verde", "azul", "morado", "rosa"];
    const totalCasillas = 49; // 5 de cada color
    const numRepeticiones = 7;
    const radioExterno = 218; // Distancia desde el centro
    const radioInt1 = 185;
    const radioInt2 = 152;
    const radioInt3 = 118;
    const radioInt4 = 85; 
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
        const x = centroX + Math.cos(angle) * radioExterno - 30; // -30 para centrar la casilla
        const y = centroY + Math.sin(angle) * radioExterno - 30;

        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

        // Agregar las casillas al contenedor
        container.appendChild(casilla);
    }
  
    // Crear pasarela interna
    for (let i = 0; i < 7; i++) {
        const colores = ["rojo", "naranja", "amarillo", "verde", "azul", "morado", "rosa"];
        const color = colores[i]; // Una casilla de cada color
        const angle = (i * 360 / 7) * (Math.PI / 180); // Equiespaciadas cada 60°
        const x = centroX + Math.cos(angle) * radioInt1 - 30;
        const y = centroY + Math.sin(angle) * radioInt1 - 30;

        const casilla = document.createElement("div");
        casilla.classList.add("casilla", color, "normal");
        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

        container.appendChild(casilla);
    }
  
    for (let i = 0; i < 7; i++) {
        const colores = ["rosa", "verde", "naranja", "amarillo", "verde", "azul", "morado"];
        const color = colores[i]; // Una casilla de cada color
        const angle = (i * 360 / 7) * (Math.PI / 180); // Equiespaciadas cada 60°
        const x = centroX + Math.cos(angle) * radioInt2 - 30;
        const y = centroY + Math.sin(angle) * radioInt2 - 30;

        const casilla = document.createElement("div");
        casilla.classList.add("casilla", color, "normal");
        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

        container.appendChild(casilla);
    }
  
    for (let i = 0; i < 7; i++) {
        const colores = ["azul", "morado", "rosa", "rojo", "naranja", "amarillo", "rojo"];
        const color = colores[i]; // Una casilla de cada color
        const angle = (i * 360 / 7) * (Math.PI / 180); // Equiespaciadas cada 60°
        const x = centroX + Math.cos(angle) * radioInt3 - 30;
        const y = centroY + Math.sin(angle) * radioInt3 - 30;

        const casilla = document.createElement("div");
        casilla.classList.add("casilla", color, "normal");
        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

        container.appendChild(casilla);
    }
  
    for (let i = 0; i < 7; i++) {
        const colores = ["verde", "azul", "morado", "rosa", "rojo", "naranja", "amarillo"];
        const color = colores[i]; // Una casilla de cada color
        const angle = (i * 360 / 7) * (Math.PI / 180); // Equiespaciadas cada 60°
        const x = centroX + Math.cos(angle) * radioInt4 - 30;
        const y = centroY + Math.sin(angle) * radioInt4 - 30;

        const casilla = document.createElement("div");
        casilla.classList.add("casilla", color, "normal");
        casilla.style.left = `${x}px`;
        casilla.style.top = `${y}px`;

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
        ficha.style.left = `${casilla.offsetLeft + 5}px`; // Ajustar posición
        ficha.style.top = `${casilla.offsetTop + 5}px`;
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
    verificarCasillaEspecial();
}

  const casillasEspeciales = [0, 7, 14, 21, 28, 35, 42];
  const rutasAlternativas = {
    0: [49, 56, 63, 70, 63, 56, 49, 0],
    7: [50, 57, 64, 71, 64, 57, 50, 7],
    14: [51, 58, 65, 72, 65, 58, 51, 14],
    21: [52, 59, 66, 73, 66, 59, 52, 21],
    28: [53, 60, 67, 74, 67, 60, 53, 28],
    35: [54, 61, 68, 75, 68, 61, 54, 35],
    42: [55, 62, 69, 76, 69, 62, 55, 42]
};


function verificarCasillaEspecial() {
    const flechaArriba = document.getElementById("flechaArriba");

    // Si la casilla actual está en las rutas especiales, mostrar la flecha
    if (rutasAlternativas.hasOwnProperty(posicionFicha)) {
        flechaArriba.style.display = "inline-block";
    } else {
        flechaArriba.style.display = "none";
    }
}

document.getElementById("flechaArriba").addEventListener("click", () => {
    if (rutasAlternativas.hasOwnProperty(posicionFicha)) {
        let ruta = rutasAlternativas[posicionFicha]; // Obtiene la ruta correcta
        let indiceActual = ruta.indexOf(posicionFicha);
        
        if (indiceActual !== -1 && indiceActual < ruta.length - 1) {
            let nuevaPosicion = ruta[indiceActual + 1]; // Mover a la siguiente casilla en la ruta especial
            moverFicha(nuevaPosicion);
        }
    }
});


});