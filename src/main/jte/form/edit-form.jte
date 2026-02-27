@param String error
@param com.hexagonal.sample.adapters.in.common.dto.UserResponse response

<h2>Update User</h2>

@if (error != null)
    <p style="color:red">${error}</p>
@endif

<form action="/mvc/users/${response.id()}" method="post">

    <label for="name">Name:</label><br>
    <input id="name" type="text" name="name" value="${response.name()}" required>
    <br><br>

    <label for="birthDate">Birth Date:</label><br>
    <input id="birthDate"
           type="date"
           name="birthDate"
           value="${response.birthDate().toString()}"
           required>
    <br><br>

    <button type="submit">Update</button>
</form>

<hr>

<a href="/mvc/users/search/id?id=${response.id()}">Back</a>