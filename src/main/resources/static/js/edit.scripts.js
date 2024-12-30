(()=>{
    const saveButton = document.querySelector("#submit-button")

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
        fetch(`http://localhost:8080/api/items/${itemId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }).then(response => {
            if (!response.ok) {
                //  Redirect to error page with error details (optional)
                response.json().then(error => {
                    const errorDetails = encodeURIComponent(JSON.stringify(error)); // Encode for URL
                    window.location.href = `/error?message=${error.message}&details=${errorDetails}`;
                }).catch(() => {
                    window.location.href = "/error?message=An error occurred during update.";
                });
                return Promise.reject(); // Important: Reject the promise to stop further .then() execution
            } else {
                //  Redirect to view page
                window.location.href = `/admin/items`;
                return Promise.resolve();
            }
        })
    })

})()