const name = document.getElementById('name')
const author = document.getElementById('author')
const year = document.getElementById('year')
const fiction = document.getElementById('fiction')
const isbn = document.getElementById('isbn')
const description = document.getElementById('description')
const addButton = document.getElementById('addButton')
const message = document.getElementById('message')
const operation = document.getElementById('operation')
const upload = document.getElementById('upload')
const image = document.getElementById('image')
const fileName = document.getElementById('fileName')

let bookId, imageUrl;

checkUrl()

image.addEventListener('change', function (event) {
    fileName.innerText = image.files[0].name

    const FR = new FileReader()
    FR.addEventListener('load', function (evt) {
        imageUrl = evt.target.result
    })

    FR.readAsDataURL(image.files[0])
})

upload.addEventListener('click', function () {
    image.click()
})

function addBook() {
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:8888/book',
        headers: {'Authorization': window.localStorage.token},
        data: JSON.stringify({
            "image": imageUrl,
            "name": name.value,
            "author": author.value,
            "bookYear": year.value,
            "fiction": fiction.value,
            "isbn": isbn.value,
            "detail": description.value
        }),
        contentType: "application/json; charset=utf-8",
        success: function () {
            window.location.href = 'index.html'
        },
        error: function () {
            message.innerText = "Book is already exist with this isbn!";
        }
    })
}

function updateBook() {
    if (imageUrl == null)
        $.ajax({
            type: "PUT",
            url: 'http://127.0.0.1:8888/book',
            headers: {'Authorization': window.localStorage.token},
            data: JSON.stringify({
                "name": name.value,
                "author": author.value,
                "bookYear": year.value,
                "fiction": fiction.value,
                "isbn": isbn.value,
                "detail": description.value
            }),
            contentType: "application/json; charset=utf-8",
            success: function () {
                window.location.href = 'BookDetail.html?id=' + bookId
            },
            error: function () {
                message.innerText = "Book is already exist with this isbn!";
            }
        })
    else
        $.ajax({
            type: "PUT",
            url: 'http://127.0.0.1:8888/book',
            headers: {'Authorization': window.localStorage.token},
            data: JSON.stringify({
                "image": imageUrl,
                "name": name.value,
                "author": author.value,
                "bookYear": year.value,
                "fiction": fiction.value,
                "isbn": isbn.value,
                "detail": description.value
            }),
            contentType: "application/json; charset=utf-8",
            success: function () {
                window.location.href = 'BookDetail.html?id=' + bookId
            },
            error: function () {
                message.innerText = "Book is already exist with this isbn!";
            }
        })
}

function fillFields(id) {
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/book/' + id,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            name.value = response.name;
            author.value = response.author
            year.value = response.bookYear
            isbn.value = response.isbn
            fiction.value = response.fiction
            description.value = response.detail
            addButton.value = 'Update book'
            operation.innerText = 'Update'
        },
        error: function () {
            message.innerText = "Book not found.";
        }
    })
}

function checkUrl() {
    let url = new URL(window.location.href);
    let id = url.searchParams.get("id");
    if (id != null) {
        bookId = id;
        fillFields(id)
        addButton.addEventListener('click', function () {
            updateBook()
        })
    } else
        addButton.addEventListener('click', function () {
            addBook()
        })
}