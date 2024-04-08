const form = document.getElementById('form')
const add = document.querySelector('h3')

fill()

add.addEventListener('click', function () {
    window.location.href = 'AddLibrary.html'
})

function fill() {
    $.ajax({
        type: "GET",
        url: 'http://127.0.0.1:8888/library',
        headers: {'Authorization': window.localStorage.token},
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            for (data of response)
            {
                let library = document.createElement('input')
                library.type = 'submit'
                library.value = data.libraryName
                library.style.width = '100%'

                library.addEventListener('click', function () {
                    window.location.href = 'library.html?id=' + data.id
                })

                let box = document.createElement('div')
                box.className='input-group'

                box.appendChild(library)
                form.appendChild(box)
            }
        },
        error: function () {
            message.innerText = "Book not found.";
        }
    })
}
