


document.addEventListener('DOMContentLoaded', function () {
    // Ensure that the fileList element exists before applying Sortable
    const fileList = document.getElementById('fileList');
    if (fileList) {
        Sortable.create(fileList, {
            animation: 150,
            onEnd: function () {
                console.log("Order changed");
            }
        });
    } else {
        alert("Element #fileList not found!");
    }
});

// let existingFiles = [
//     { id: 1, name: "img.png", path: "/uploads/products/img.png" },
//     { id: 2, name: "image2.png", path: "/uploads/products/image2.png" }
// ];
let selectedFiles = [];
function renderFiles() {
    const fileList = document.getElementById('fileList');
    fileList.innerHTML = ""; 

    // // Render existing files
    // existingFiles.forEach((file, index) => {
    //     const li = document.createElement("li");
    //     li.className = "file-item";
    //     li.innerHTML = `
    //         <img src="${file.path}" alt="${file.name}" width="100">
    //         <button type="button" onclick="removeExistingFile(${index})">Remove</button>
    //     `;
    //     li.dataset.id = file.id;
    //     fileList.appendChild(li);
    // });

    // Render selected files
selectedFiles.forEach((file, index) => {
    const li = document.createElement("li");
    li.className = "file-item";
    const fileReader = new FileReader();
    fileReader.onload = function (e) {
        li.innerHTML = `
            <img src="${e.target.result}" alt="${file.name}" width="100">
            <button type="button" onclick="removeNewFile(${index})">Remove</button>
        `;
        fileList.appendChild(li);
    };
    if (file.type.startsWith("image/")) {
        fileReader.readAsDataURL(file);
    }
});
}



// Function to preview newly selected files
function previewNewFiles() {
    const files = document.getElementById('files').files;
    Array.from(files).forEach((file) => {
        selectedFiles.push(file);
    });
    renderFiles();
}

// Function to remove new files
function removeNewFile(index) {
    selectedFiles.splice(index, 1);
    renderFiles();
}

// // Initially render existing files
// renderFiles();


//  Leave this for backend !!!!!!!!!!!!!!!!!!!!!!!!!!
function removeImage(imageId){

    fetch('/api/product/removeImage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.getElementById('_csrf').value
        },
        body: JSON.stringify({imageId : imageId})
    }).then(response => {
        if(response.ok){
            window.location.reload();
        }else{
            alert('Failed');
            alert(response.status);
        }
    }).catch(error => {
        console.error('ERROR: ',error);
    });
}