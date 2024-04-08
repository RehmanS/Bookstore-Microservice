const name = document.getElementById('name')
const author = document.getElementById('author')
const year = document.getElementById('year')
const isbn = document.getElementById('isbn')
const description = document.getElementById('description')
const message = document.getElementById('message')
const editButton = document.getElementById('editButton')
const removeButton = document.getElementById('removeButton')
const favButton = document.getElementById('favButton')
const libraryButton = document.getElementById('libraryButton')
const image = document.getElementById('image')

let bookId;

checkUrl()

function checkUrl ()
{
    let url = new URL(window.location.href);
    let id = url.searchParams.get("id");
    if (id != null) {
        bookId = id;
        fillFields(id)
    }
    else
        message.innerText = 'Book is not found'
}


function fillFields (id)
{
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/book/' + id,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            image.src = response.image
            name.innerText = response.name;
            author.innerText = response.author
            year.innerText = response.bookYear
            isbn.innerText = response.isbn
            description.innerText = response.detail
        },
        error: function () {
            message.innerText = "Book not found.";
        }
    })
}

editButton.addEventListener('click', function () {
    window.location.href = 'AddForm.html?id=' + bookId
})

removeButton.addEventListener('click', function () {
    $.ajax({
        type: "DELETE",
        url: 'http://127.0.0.1:8888/book/' + isbn.innerText,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            window.location.href = 'index.html'
        },
        error: function () {
            message.innerText = "Book not found.";
        }
    })
})

favButton.addEventListener('click', function () {
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/readList/add/' + isbn.innerText,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            favButton.style.color = 'green'
        }
    })
})

