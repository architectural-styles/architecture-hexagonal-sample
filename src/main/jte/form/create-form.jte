@param String error
@param com.hexagonal.sample.adapters.in.common.dto.Request request

<h2>Создать нового пользователя</h2>

<form action="/mvc/users" method="post">

    <label for="name">Имя:</label><br>
    <input id="name" type="text" name="name" required>
    <br><br>

    <label for="birthDate">Дата рождения:</label><br>
    <input id="birthDate"
           type="date"
           name="birthDate"
           value="${request.birthDate().toString()}"
           required>

    <button type="submit">Создать</button>
</form>

@if (error != null) <p>${error}</p> @endif

<a href="/mvc/users">Вернуться на главную страницу</a>