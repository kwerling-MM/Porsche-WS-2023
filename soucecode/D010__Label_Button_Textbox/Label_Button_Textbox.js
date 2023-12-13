
const nameInput = document.getElementById('name');
const button = document.getElementById('button');
const greetingLabel = document.getElementById('greeting');

// button.addEventListener('click', setGreetingLabel );

function setGreetingLabel() {
         const name = nameInput.value;
         greetingLabel.textContent = `Hello, ${name}!`;
}