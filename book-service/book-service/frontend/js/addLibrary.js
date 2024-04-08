const name = document.getElementById('text-input')
const add = document.getElementById('addButton')

add.addEventListener('click', function ()
{
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:8888/library',
        headers: {'Authorization': window.localStorage.token},
        data: JSON.stringify({
            "libraryName": name.value
        }),
        contentType: "application/json; charset=utf-8",
        success: function () {
            window.location.href = 'libraries.html'
        }
    })
})