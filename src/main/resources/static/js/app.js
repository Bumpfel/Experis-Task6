import { addActorTemplate, listActorsTemplate, actorCard } from './templates.js'

const host = 'http://localhost:8080'
const api = '/api/v1'
const msgTimeout = 5000

const styleSheet = document.createElement('link')
styleSheet.setAttribute('rel', 'stylesheet')
styleSheet.setAttribute('href', '/css/content.css')

const fontawesome = document.createElement('link')
fontawesome.setAttribute('rel', 'stylesheet')
fontawesome.setAttribute('href', 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css')

const contentContainer = document.createElement('div')
contentContainer.id = 'contentContainer'

const msgContainer = document.createElement('div')
msgContainer.classList.add('messages')

class ActorApp extends window.HTMLElement {
  constructor () {
    super()

    this.attachShadow({ mode: 'open' })
    this.shadowRoot.appendChild(styleSheet)
    this.shadowRoot.appendChild(fontawesome)
    this.shadowRoot.appendChild(contentContainer)
    this.shadowRoot.appendChild(msgContainer)
    
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
    while(contentContainer.lastChild) {
      contentContainer.removeChild(contentContainer.lastChild)
    }
  }
  
  async _showAllActors () {
    this._clearPage()

    contentContainer.addEventListener('click', e => {
      if(e.target.nodeName === 'I') {
        const actor = e.target.parentElement.parentElement.parentElement
        if(e.target.id === 'deleteActor') {
          this._deleteActor(actor.getAttribute('data-id'))
        } else if(e.target.id === 'editActor') {
          this._showEditActor(actor.getAttribute('data-id'))
        }
      }
    })

    const response = await fetch(host + api + '/actors')
    if(response.ok) {
      const actors = await response.json()
      if(actors) {
        for(const actor of actors) {
          const card = actorCard(actor)
          contentContainer.appendChild(card)
        }
      } else {
        contentContainer.textContent = 'No actors found'
      }
    } else {
      this._addMsg('Could not fetch actors. Dno why')
    }
  }

  async _showAddActor (id) {
    this._clearPage()

    contentContainer.appendChild(addActorTemplate.content.cloneNode(true))
    const form = contentContainer.querySelector('#addActorForm')
    form.querySelector('input').focus() // focus first input

    let actor = {}  

    form.addEventListener('submit', async e => {
      e.preventDefault()

      for(const input of form.querySelectorAll('input')) {
        if(input.value.trim().length > 0) {
          actor[input.name] = input.value
        }
      }

      const response = await this._addActor(actor)
      
      if(response.ok) {
        form.reset()
        form.querySelector('input').focus()
        this._addMsg('Actor added')
      } else {
        this._addMsg('Could not add actor. Make sure the mandatory fields are filled in')
      }
    })
  }

  async _showEditActor(id) {
    this._clearPage()

    contentContainer.appendChild(addActorTemplate.content.cloneNode(true))
    const form = contentContainer.querySelector('#addActorForm')
    
    for(const input of form.querySelectorAll('input')) {
      input.setAttribute('disabled', '')
    }
    
    let response = await fetch(host + api + '/actors/' + id)
    if(response.ok) {

      var actor = await response.json()
      for(const input of form.querySelectorAll('input')) {
        input.removeAttribute('disabled')
        input.value = actor[input.name]
      }
      form.querySelector('input').focus() // focus first input
    } else {
      this._addMsg('Can not edit actor. Actor not found')
    }

    form.addEventListener('submit', async e => {
      e.preventDefault()

      for(const input of form.querySelectorAll('input')) {
        if(input.value.trim().length > 0) {
          actor[input.name] = input.value
        } else {
          actor[input.name] = null
        }
      }
      response = await this._editActor(actor, id)
      
      if(response.ok) {
        this._addMsg('Actor edited')
        this._showAllActors()
      } else {
        if(response.status / 100 === 4) {
          this._addMsg('Could not edit actor. Fill in all the fields')
        } else if(response.status / 100 === 5) {
          this._addMsg('Server error. Could not edit actor. Try again later')
        } else {
          this._addMsg('Unknown error. Could not edit actor')
        }
      }
    })
  }

  async _addActor (actor) {
    const response = fetch(host + api + '/actors', {
      method: 'POST',
      body: JSON.stringify(actor),
      headers: {
        'Content-Type': 'application/json'
      }
    })
    return response
  }
  
  async _editActor (actor, id) {
    const response = fetch(host + api + '/actors/' + id, {
      method: 'PUT',
      body: JSON.stringify(actor),
      headers: {
        'Content-Type': 'application/json'
      }
    })
    return response
  }

  async _deleteActor (id) {
    const response = await fetch(host + api + '/actors/' + id, {
      method: 'DELETE'
    })
    if(response.ok) {
      contentContainer.querySelector('#actor' + id).remove()
    }
  }

  _addMsg(msg) {
    const line = document.createElement('div')
    line.textContent = msg
    msgContainer.appendChild(line)
    setTimeout(() => {
      msgContainer.removeChild(line)
    }, msgTimeout)
  }

}

window.customElements.define('actor-app', ActorApp)
