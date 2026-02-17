@param com.hexagonal.sample.adapters.in.common.dto.Response response

<h2>Подтвердите удаление</h2>

Удалить Юзера - «${response.id()}. ${response.name()}»?

<hr>
<h2>Вернуться к данным о пользователе</h2>

<!-- Прямая ссылка -->
<a href="/mvc/users/search/id?id=${response.id()}">Вернуться к пользователю</a>

<!-- Кнопка в форме -->
<form action="/mvc/users/search/id" method="get" style="display:inline;">
    <input type="hidden" name="id" value="${response.id()}">
    <button type="submit">Вернуться к пользователю</button>
</form>
<hr>
<h2>Удалить</h2>
<form action="/mvc/users/${response.id()}/delete" method="post">
    <button type="submit">Удалить</button>
</form>
<hr>