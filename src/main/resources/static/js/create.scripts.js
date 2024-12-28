import {createItem} from "./Utils";

(() => {

    const createButton = document.querySelector("#create-button")

    createButton.addEventListener("click", (event) => {
        event.preventDefault()
        const name = document.querySelector("#name").value
        const barcodes = document.querySelector("#barcodes").value
        const data = {
            name,
            barcodes
        }

        createItem(data);

    })
})()