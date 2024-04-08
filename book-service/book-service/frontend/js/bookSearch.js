const type = document.getElementById('type')
const key = document.getElementById('key')
const search = document.getElementById('search')
const booksBox = document.getElementById('books')

key.addEventListener('keyup', (e) => {
    if (e.code === "Enter") {
        search.click()
    }
})

function searchByName() {
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/book/name?text=' + key.value,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            fillBooks(response)
        }
    })
}

function searchByAuthor() {
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/book/author?text=' + key.value,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            fillBooks(response)
        }
    })
}

search.addEventListener('click', function () {
    if (type.value === 'name')
        searchByName()
    else
        searchByAuthor()
})

function fillBooks(response)
{
    while (booksBox.hasChildNodes())
        booksBox.removeChild(booksBox.firstChild)

    for (data of response) {
        const bookCard = document.createElement('div')
        const bookImage = document.createElement('div')
        const bookImageSrc = document.createElement('img')
        const bookTag = document.createElement('div')
        const bookName = document.createElement('p')
        const bookBtn = document.createElement('a')

        bookCard.className = 'arrivals_card'
        bookImage.className = 'arrivals_image'
        bookTag.className = 'arrivals_tag'
        bookBtn.className = 'arrivals_btn'

        bookImageSrc.src = data.image
        bookName.innerText = data.name
        bookBtn.href = 'BookDetail.html?id=' + data.id
        bookBtn.innerText = 'Learn More'

        bookImage.appendChild(bookImageSrc)
        bookCard.appendChild(bookImage)
        bookTag.appendChild(bookName)
        bookTag.appendChild(bookBtn)
        bookCard.appendChild(bookTag)

        booksBox.appendChild(bookCard)
    }
}