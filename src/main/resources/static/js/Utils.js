const addNewBarcode = ()=> {
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
}

const removeBarcode = (index) => {
    // Remove from the displayed list
    const listItem = document.querySelector("#barcode-" + index);
    if (listItem) {
        listItem.remove();
    }
}