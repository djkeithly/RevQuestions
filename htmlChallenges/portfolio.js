const faveName = "banette";
const faveType = "ghost";
const faveGeneration = "generation-iii";
const faveIsLegendary = false;
const faveIsMythical = false;
const faveEvoLine = true;
const faveEvolvesFrom = true;
let guesses = 0;

let form = document.querySelector(".game");

form.addEventListener("submit", getPokemonData);

async function getPokemonData(e) {
    e.preventDefault();

    console.log(guesses);
    guesses++;

    let name = document.querySelector(".gamename").value;

    // Specific Pokemon/Line information
    let response = await fetch(
        `https://pokeapi.co/api/v2/pokemon-species/${name}`,
    );
    let data = await response.json();

    // Game play stats for the pokemon
    let specificResponse = await fetch(
        `https://pokeapi.co/api/v2/pokemon/${name}`,
    );
    let specificData = await specificResponse.json();

    console.log(data);
    console.log(specificData);

    let guessBlock = document.querySelector(".guessblock");
    if (guessBlock) guessBlock.remove();

    guessBlock = document.createElement("div");
    guessBlock.classList.add("guessblock");

    // **************************************
    // *                Name                *
    // **************************************
    let guessName = document.createElement("div");
    if (name == faveName) guessName.classList.add("correct");
    else guessName.classList.add("wrong");
    guessName.textContent = name;
    guessBlock.appendChild(guessName);

    // **************************************
    // *                Type                *
    // **************************************
    let typeOne = document.createElement("div");
    let typeTwo = document.createElement("div");
    let tOne = specificData.types[0].type.name;
    let tTwo = specificData.types[1]?.type.name; // Not all pokemon will have a type, this keeps it undefined if they don't

    if (tOne === faveType) typeOne.classList.add("correct", "guesselement");
    else typeOne.classList.add("wrong", "guesselement");
    typeOne.textContent = tOne;

    if (!tTwo) {
        typeTwo.classList.add("correct", "guesselement");
        typeTwo.textContent = "none";
    } else {
        if (tTwo === faveType) typeTwo.classList.add("warn");
        else typeTwo.classList.add("wrong", "guesselement");
        typeTwo.textContent = tTwo;
    }

    let typeHolder = document.createElement("div");
    typeHolder.classList.add("holder");
    typeHolder.appendChild(typeOne);
    typeHolder.appendChild(typeTwo);
    guessBlock.appendChild(typeHolder);

    // ********************************************
    // *                Generation                *
    // ********************************************
    let generation = document.createElement("div");
    let guessGeneration = data.generation.name;
    if (guessGeneration === faveGeneration) generation.classList.add("correct");
    else generation.classList.add("wrong");
    generation.textContent = guessGeneration;
    guessBlock.appendChild(generation);

    // **************************************************
    // *                Mythic/Legendary                *
    // **************************************************
    let mythic = document.createElement("div");
    if (data.is_legendary || data.is_mythic) {
        mythic.classList("wrong");
        mythic.textContent = "Pokemon is a mythic or legendary";
    } else {
        mythic.classList.add("correct");
        mythic.textContent = "Pokemon is not a mythic or legendary";
    }
    guessBlock.appendChild(mythic);

    // **********************************************
    // *                Evolves From                *
    // **********************************************
    if (guesses >= 4) {
        let evolves = document.createElement("div");
        let guessEvolves = document.evolves_from_species;
        if (guessEvolves === null) {
            evolves.classList.add("wrong");
            evolves.textContent = "Pokemon has not evolved";
        } else {
            evolves.classList.add("correct");
            evolves.textContent = "Pokemon has evolved";
        }

        guessBlock.appendChild(evolves);
    }

    // ****************************************
    // *                Finish                *
    // ****************************************
    guessBlock.childNodes.forEach((node) => node.classList.add("guesselement"));
    form.appendChild(guessBlock);
}
