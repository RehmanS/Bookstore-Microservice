const books = document.getElementById('books')
const libName = document.getElementById('lib-name')

checkUrl()

function fillBooks (id)
{
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/library/' + id,
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            libName.innerText = response.libraryName
            for (data of response.userBookList)
            {
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

                books.appendChild(bookCard)
            }
        }
    })
}



function checkUrl ()
{
    let url = new URL(window.location.href);
    let id = url.searchParams.get("id");
    if (id != null) {
        bookId = id;
        fillBooks(id)
    }
}