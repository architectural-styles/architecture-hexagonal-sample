@param String message
@param com.hexagonal.sample.adapters.in.common.dto.Response response

<h2>Юзер</h2>

@if (message != null) <p>${message}</p> @endif

<p>
    <strong>ID:</strong> ${response.id()}
    <strong>Name:</strong> ${response.name()}
    <strong>Birthdate:</strong> ${response.birthDate().toString()}
</p>

<hr>
<a href="/mvc/users/${response.id()}/edit">Обновить</a>
<form action="/mvc/users/${response.id()}/edit" method="get" style="display:inline;">
    <button type="submit">Обновить</button>
</form>

<h2>Удалить!</h2>
<hr>
<a href="/mvc/users/${response.id()}/delete">Удалить!</a>
<form action="/mvc/users/${response.id()}/delete" method="get" style="display: inline;">
    <button type="submit">Удалить!</button>
</form>
<hr>

<h2>Вернуться на главную страницу</h2>
<hr>
<a href="/mvc/users">Вернуться на главную страницу</a>
<form action="/mvc/users" method="get" style="display: inline;">
    <button type="submit">Вернуться на главную страницу</button>
</form>
<hr>