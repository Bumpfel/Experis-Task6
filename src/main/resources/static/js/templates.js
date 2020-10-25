export const listActorsTemplate = document.createElement('template')
listActorsTemplate.innerHTML = `
<div class="flexContainer">
</div>
`

export const addActorTemplate = document.createElement('template')
addActorTemplate.innerHTML = `
<form method="POST" id="addActorForm">
  <div for="firstName">First Name: *</div>
  <input type="text" name="firstName" autocomplete="off">
  <br>

  <div for="lastName">Last Name: *</div>
  <input type="text" name="lastName" autocomplete="off">
  <br>

  <div for="dateOfBirth">Date of Birth: *</div>
  <input type="date" name="dateOfBirth">
  <br>

  <div for="imdbUrl">Imdb URL: *</div>
  <input type="text" name="imdbUrl" autocomplete="off">
  <br>

  <button type="submit">Create</button>
</form>
`

export const actorCard = (actor) => {
  const card = document.createElement('a')
  card.href = "#"
  card.target = '_blank'
  card.classList.add('card')
  card.id = 'actor' + actor.id
  card.setAttribute('data-id', actor.id)
  
  const functions = document.createElement('div')
  functions.classList.add('functions')
  functions.innerHTML = `
  <a href="#">
    <i id="deleteActor"class="fa fa-remove"></i>
  </a>
  <a href="#">
    <i id="editActor" class="fa fa-edit"></i>
  </a>
  `
  
  const row1 = document.createElement('div')
  row1.textContent = actor.firstName + ' ' + actor.lastName
  const row2 = document.createElement('div')
  row2.textContent = actor.dateOfBirth
  
  card.appendChild(functions)
  card.appendChild(row1)
  card.appendChild(row2)
  return card
}