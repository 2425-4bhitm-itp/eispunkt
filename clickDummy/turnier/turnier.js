inputName = document.getElementById("name")
display = document.getElementById("displayBox")
body = document.getElementById("bodyBox")

function openModal() {
    document.getElementById("overlay").classList.add("active");
}

function closeModal() {
    document.getElementById("overlay").classList.remove("active");
}

document.addEventListener("click", function(e) {
    const overlay = document.getElementById("overlay");
    if (e.target === overlay) {
        closeModal();
    }
});


function saveTurnier(){
    document.getElementById("overlay").classList.remove("active");
    display.innerHTML += `<div class='turnierDet' id="2"><a href="turnierDetails.html" target="_self"><h1>${inputName.value}</h1></a>
                            <svg width="60" height="60" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" onclick="deleteTurnier('2')">
                            <path d="M6 19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V9C18 7.9 17.1 7 16 7H8C6.9 7 6 7.9 6 
                            9V19ZM18 4H15.5L14.79 3.29C14.61 3.11 14.35 3 14.09 3H9.91C9.65 3 9.39 3.11 9.21 3.29L8.5 4H6C5.45 4 5 
                            4.45 5 5C5 5.55 5.45 6 6 6H18C18.55 6 19 5.55 19 5C19 4.45 18.55 4 18 4Z" fill="#7FC8EE"/>
                            </svg>
                            </div>`

}




function deleteTurnier(turnierDet) {
    document.getElementById(turnierDet).remove();
}