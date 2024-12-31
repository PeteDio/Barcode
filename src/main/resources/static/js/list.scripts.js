const deleteItem = (itemId) => {
    // Confirmation modal with user interaction
    let confirmation;
    confirmation = confirm("Are you sure you want to delete this item?");

    if (confirmation) {
        // Send the delete request using fetch
        fetch(`http://localhost:8080/api/items/${itemId}`, {
            method: 'DELETE'
        }).then(response => {
            if (!response.ok) {
                response.json().then(error => {
                    const errorDetails = encodeURIComponent(JSON.stringify(error));
                    window.location.href = `/error?message=${error.message}&details=${errorDetails}`;
                }).catch(() => {
                    window.location.href = "/error?message=An error occurred during update.";
                });
                return Promise.reject();
            }
            else {
                window.location.href = `/`
            }
        })
    }
}