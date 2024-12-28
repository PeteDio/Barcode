export function validateBarcode(barcode) {
    const barcodeRegex = /^[0-9]+$/; // Only digits allowed
    return barcodeRegex.test(barcode);
}

export async function updateItem(itemId, data) {
    try {
        const response = await fetch(`http://localhost:8080/api/items/${itemId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            handleError(response, "Error during item update.");
            return;
        } else {
            window.location.href = `/admin/items/${itemId}/edit`;
        }
    } catch (fetchError) {
        console.error("Fetch error:", fetchError);
        handleError(null, "A network error occurred.");
    }
}
export async function createItem(item) {
    try {
        const response = await fetch('/api/items', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });

        if (!response.ok) {
            handleError(response, "Error during item creation.");
            return;
        }
        else {
            window.location.href = `/admin/items/`;
        }
    } catch (fetchError) {
        console.error("Fetch error:", fetchError);
        handleError(null, "A network error occurred.");
    }
}

export function handleError(response, defaultErrorMessage = "An error occurred.") {
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