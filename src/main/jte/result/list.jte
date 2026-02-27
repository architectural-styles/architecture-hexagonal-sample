@import java.util.List
@import com.hexagonal.sample.adapters.in.common.dto.UserResponse

@param List<UserResponse> userViews
@param String searchTerm

<h2>Search results for name: "${searchTerm}"</h2>

@if (userViews.isEmpty())
    <h2>No results found.</h2>
@endif

@for (UserResponse i : userViews)
    <li>
        ID: ${i.id()} — ${i.name()} — ${i.birthDate().toString()}
        <a href="/mvc/users/search/id?id=${i.id()}">Select</a>&nbsp;
        <a href="/mvc/users/${i.id()}/edit">Edit</a>&nbsp;
        <a href="/mvc/users/${i.id()}/delete">Delete</a>
    </li>
@endfor

<h2>Return to Main Page</h2>
<hr>
<a href="/mvc/users">Return to Main Page</a>
<form action="/mvc/users" method="get" style="display: inline;">
    <button type="submit">Return to Main Page</button>
</form>
<hr>