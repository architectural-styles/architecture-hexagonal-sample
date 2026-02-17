@param String error
@param String message
@param com.hexagonal.sample.adapters.in.common.dto.Response response
@param com.hexagonal.sample.adapters.in.common.dto.Request request

<h2>Main page</h2>

@if (error != null)
    <p style="color: red;">${error}</p>
@endif

@if (message != null)
    <p style="color: green;">${message}</p>
@endif

<h2>Поиск пользователя по имени</h2>
<h4>При нажатии кнопки поиска с пустой формой получите всех пользователей</h4>

<form action="/mvc/users/search/name" method="get">
    <label for="name">Name or prefix:</label>
    <input id="name" type="text" name="name"/>
    <button type="submit">Найти</button>
</form>

<hr>

<h2>Поиск пользователя по ID</h2>

<form action="/mvc/users/search/id" method="get">
    <label for="id">ID:</label>
    <input id="id" type="text" name="id" required />
    <button type="submit">Найти</button>
</form>

<hr>

<h2>Создать нового пользователя</h2>

<a href="/mvc/users/new">Создать нового пользователя</a>
<form action="/mvc/users/new" method="get" style="display: inline;">
    <button type="submit">Создать нового пользователя</button>
</form>
<hr>