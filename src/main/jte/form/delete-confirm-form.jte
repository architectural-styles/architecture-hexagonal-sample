@param com.hexagonal.sample.adapters.in.common.dto.UserResponse response

<h2>Confirm Deletion</h2>

Delete user — “${response.id()}. ${response.name()}”?

<hr>

<h2>Return to User Details</h2>

<!-- Direct link -->
<a href="/mvc/users/search/id?id=${response.id()}">Back to User</a>

<!-- Button in form -->
<form action="/mvc/users/search/id" method="get" style="display:inline;">
    <input type="hidden" name="id" value="${response.id()}">
    <button type="submit">Back to User</button>
</form>

<hr>

<h2>Delete</h2>
<form action="/mvc/users/${response.id()}/delete" method="post">
    <button type="submit">Delete</button>
</form>

<hr>