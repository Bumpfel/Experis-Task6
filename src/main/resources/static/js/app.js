import { addActorTemplate, listActorsTemplate } from './templates.js'

const host = 'http://localhost:8080'
const api = '/api/v1'

const styleSheet = document.createElement('link')
styleSheet.setAttribute('rel', 'stylesheet')
styleSheet.setAttribute('href', '/css/content.css')

class MyApp extends window.HTMLElement {
  constructor () {
    super()

    this.attachShadow({ mode: 'open' })
    this.shadowRoot.appendChild(styleSheet)
    
    this._registerMenu()
    this._showAllActors()
  }

  _registerMenu () {
    document.querySelector('#home').addEventListener('click', e => {
      this._showAllActors()
    })
    document.querySelector('#addActor').addEventListener('click', e => {
      this._showAddActor()
    })

  }
  
  _clearPage () {
    while(this.shadowRoot.childElementCount > 1) { // do not remove stylesheet
      this.shadowRoot.removeChild(this.shadowRoot.lastChild)
    }
  }
  
  async _showAllActors () {
    this._clearPage()

    const container = listActorsTemplate.content
    const response = await fetch(host + api + '/actors')
    if(response.ok) {
      const actors = await response.json()
      if(actors) {
        for(const actor of actors) {
          // console.log(actor)
          container.appendChild(this._createCard(actor))
        }
      } else {
        container.textContent = 'No actors found'
      }
    } else {
      throw new Error(response.status)
    }
    this.shadowRoot.appendChild(container)
  }

  _createCard (actor) {
    const card = document.createElement('a')
    card.href = actor.imdbUrl
    card.target = '_blank'
    card.classList.add('card')
    const row1 = document.createElement('div')
    row1.textContent = actor.firstName + ' ' + actor.lastName
    const row2 = document.createElement('div')
    row2.textContent = actor.dateOfBirth
    card.appendChild(row1)
    card.appendChild(row2)
    return card
  }
  
  async _showAddActor () {
    this._clearPage()

    this.shadowRoot.appendChild(addActorTemplate.content.cloneNode(true))
    const form = this.shadowRoot.querySelector('#addActorForm')
    form.querySelector('input').focus() // focus first input
    form.addEventListener('submit', e => {
      e.preventDefault()
      const body = {}
      for(const input of form.querySelectorAll('input')) {
        body[input.name] = input.value
      }

      console.log(JSON.stringify(body))
      fetch(host + api + '/actors', {
        method: 'POST',
        body: JSON.stringify(body),
        headers: {
          'Content-Type': 'application/json'
        }
      })
    })
  }
}

window.customElements.define('my-app', MyApp)
