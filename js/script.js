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
