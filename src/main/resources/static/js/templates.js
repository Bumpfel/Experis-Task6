export const listActorsTemplate = document.createElement('template')
listActorsTemplate.innerHTML = `
<div class="flexContainer">
</div>
`

export const addActorTemplate = document.createElement('template')
addActorTemplate.innerHTML = `
<form method="POST" id="addActorForm">
  <div for="firstName">First Name: *</div>
  <input type="text" name="firstName">
  <br>

  <div for="lastName">Last Name: *</div>
  <input type="text" name="lastName">
  <br>

  <div for="dateOfBirth">Date of Birth: *</div>
  <input type="date" name="dateOfBirth">
  <br>

  <div for="imdbUrl">Imdb URL: *</div>
  <input type="text" name="imdbUrl">
  <br>

  <button type="submit">Create</button>
</form>
`
