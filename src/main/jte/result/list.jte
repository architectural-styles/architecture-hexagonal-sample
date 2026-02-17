@import java.util.List
@import com.hexagonal.sample.adapters.in.common.dto.Response

@param List<Response> userViews
@param String searchTerm

<h2>Результаты поиска по имени: "${searchTerm}"</h2>

@if (userViews.isEmpty()) <h2>Ничего не найдено.</h2>@endif
@for (Response i : userViews)
    <li>
        ID: ${i.id()} — ${i.name()} - ${i.birthDate().toString()}
        <a href="/mvc/users/search/id?id=${i.id()}">Выбрать</a>&nbsp;
        <a href="/mvc/users/${i.id()}/edit">Редактировать</a>&nbsp;
        <a href="/mvc/users/${i.id()}/delete">Удалить</a>
    </li>
@endfor

<h2>Вернуться на главную страницу</h2>
<hr>
<a href="/mvc/users">Вернуться на главную страницу</a>
<form action="/mvc/users" method="get" style="display: inline;">
    <button type="submit">Вернуться на главную страницу</button>
</form>
<hr>
