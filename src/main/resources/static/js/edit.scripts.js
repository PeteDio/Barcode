import {updateItem} from "./Utils";

(()=>{
    const saveButton = document.querySelector("#submitButton")
    const removeButton =document.querySelector("#remove-button")
    const addNewButton =document.querySelector("#add-new-button")
    saveButton.addEventListener("click", () => {
        const name = document.querySelector("#name").value
        const itemId = document.querySelector("#id").value
        const ulElement = document.querySelector("#barcodeList")
        const listItems = Array.from(ulElement.children);
        let barcodes = []
        listItems.forEach(li => {
            const textElement = li.querySelector('p');
            if (textElement) {
                const barcodeText = textElement.innerText;
                barcodes.push(barcodeText)
            }
        });
        const data = {
            name, barcodes
        };
        updateItem(itemId,data)
    })

    removeButton.addEventListener("click",()=>{
            // Remove from the displayed list
            const listItem = document.querySelector("#barcode-" + index);
            if (listItem) {
                listItem.remove();
            }
    })

    addNewButton.addEventListener("click",()=>{
            const newBarcode = document.querySelector("#newBarcode").value;
            // Basic validation
            if (!newBarcode) {
                alert("Please enter a barcode.");
                return;
            }
            if (document.querySelector("#barcodeList").children.length >= 10) {
                alert("You have reached the maximum number of barcodes");
                return;
            }

            // Add to displayed list
            const list = document.querySelector("#barcodeList");
            const listItem = document.createElement("li");
            let nextIndex = document.querySelector("#barcodeList").children.length;
            listItem.id = "barcode-" + nextIndex;
            listItem.innerHTML = "<span>" + (nextIndex + 1) + "</span><p>" + newBarcode + "</p><button type='button' onclick='removeBarcode(" + nextIndex + ")'>Remove</button>";
            list.appendChild(listItem);
            document.querySelector("#newBarcode").value = "";
    })

})()
