const books = document.getElementById('books')
const libName = document.getElementById('lib-name')

fillBooks()

function fillBooks ()
{
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/readList/get',
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            for (data of response)
            {
                const bookCard = document.createElement('div')
                const bookImage = document.createElement('div')
                const bookImageSrc = document.createElement('img')
                const bookTag = document.createElement('div')
                const bookName = document.createElement('p')
                const bookBtn = document.createElement('a')

                bookCard.className = 'arrivals_card card_read_later'
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

                books.appendChild(bookCard)
            }
        }
    })
}