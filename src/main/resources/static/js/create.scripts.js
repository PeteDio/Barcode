(() => {
    async function createItem(item) {
        try {
            const response = await fetch('http://localhost:8080/api/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item)
            });

            if (!response.ok) {
                handleError(response, "Error during item creation.");

            }
            else {
                window.location.href = `/admin/items`;
            }
        } catch (fetchError) {
            console.error("Fetch error:", fetchError);
            handleError(null, "A network error occurred.");
        }
    }

    function handleError(response, defaultErrorMessage = "An error occurred.") {
        let errorMessage = defaultErrorMessage;
        let errorDetails = "";

        try {
            if (response && response.json) { // Check if response and response.json exist
                response.json().then(error => {
                    if (error && error.message) {
                        errorMessage = error.message;
                        errorDetails = encodeURIComponent(JSON.stringify(error));
                    }
                    window.location.href = `/error?message=${errorMessage}&details=${errorDetails}`;
                }).catch(jsonError => { // Catch JSON parsing errors
                    console.error("Error parsing JSON error response:", jsonError);
                    window.location.href = `/error?message=${errorMessage}`; // Redirect with default message
                });
            }
            else{
                window.location.href = `/error?message=${errorMessage}`;
            }
        } catch (generalError) {
            console.error("General error in handleError:", generalError);
            window.location.href = `/error?message=An unexpected error occurred.`;
        }
    }
    document.querySelector('#submit-button').addEventListener('click', async (event) => {
        event.preventDefault(); // Prevent default form submission

        const nameInput = document.querySelector('#name');
        const name = nameInput.value.trim();
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

        if (!name) {
            alert("Please enter a name.");
            return;
        }

        for (const barcode of barcodes) {
            if (!/^\d+$/.test(barcode)) {
                alert(`Barcode "${barcode}" is invalid. Please use only digits.`);
                return;
            }
        }

        const newItem = {
            name: name,
            barcodes: barcodes
        };
        try {
            await createItem(newItem);
        } catch (error) {
            console.error("error", error);
        }
    });
})()