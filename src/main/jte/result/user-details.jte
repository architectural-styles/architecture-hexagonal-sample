@param String message
@param com.hexagonal.sample.adapters.in.common.dto.UserResponse response

<h2>User</h2>

@if (message != null) <p>${message}</p> @endif

<p>
    <strong>ID:</strong> ${response.id()}<br>
    <strong>Name:</strong> ${response.name()}<br>
    <strong>Birthdate:</strong> ${response.birthDate().toString()}
</p>

<hr>

<a href="/mvc/users/${response.id()}/edit">Update</a>
<form action="/mvc/users/${response.id()}/edit" method="get" style="display:inline;">
    <button type="submit">Update</button>
</form>

<h2>Delete</h2>
<hr>

<a href="/mvc/users/${response.id()}/delete">Delete</a>
<form action="/mvc/users/${response.id()}/delete" method="get" style="display: inline;">
    <button type="submit">Delete</button>
</form>

<hr>

<h2>Return to Main Page</h2>
<hr>

<a href="/mvc/users">Return to Main Page</a>
<form action="/mvc/users" method="get" style="display: inline;">
    <button type="submit">Return to Main Page</button>
</form>

<hr>