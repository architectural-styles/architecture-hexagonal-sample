@param String error
@param String message

<h2>Main Page</h2>

@if (error != null)
    <p style="color: red;">${error}</p>
@endif

@if (message != null)
    <p style="color: green;">${message}</p>
@endif

<h2>Search User by Name</h2>
<h4>Clicking the search button with an empty form will return all users</h4>

<form action="/mvc/users/search/name" method="get">
    <label for="name">Name or prefix:</label>
    <input id="name" type="text" name="name"/>
    <button type="submit">Search</button>
</form>

<hr>

<h2>Search User by ID</h2>

<form action="/mvc/users/search/id" method="get">
    <label for="id">ID:</label>
    <input id="id" type="text" name="id" required />
    <button type="submit">Search</button>
</form>

<hr>

<h2>Create New User</h2>

<a href="/mvc/users/new">Create New User</a>
<form action="/mvc/users/new" method="get" style="display: inline;">
    <button type="submit">Create New User</button>
</form>

<hr>