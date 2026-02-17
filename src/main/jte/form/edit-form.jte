@param String error
@param String message
@param com.hexagonal.sample.adapters.in.common.dto.Response response

<h2>Обновить пользователя</h2>

@if (error != null)
    <p style="color:red">${error}</p>
@endif

<form action="/mvc/users/${response.id()}" method="post">

    <label for="name">Имя:</label><br>
    <input id="name" type="text" name="name" value="${response.name()}" required>
    <br><br>

    <label for="birthDate">Дата рождения:</label><br>
    <input id="birthDate"
           type="date"
           name="birthDate"
           value="${response.birthDate().toString()}"
           required>
    <br><br>

    <button type="submit">Обновить</button>
</form>

<hr>

<a href="/mvc/users/search/id?id=${response.id()}">Назад</a>