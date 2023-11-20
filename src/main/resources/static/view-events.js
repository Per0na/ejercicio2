document.addEventListener("DOMContentLoaded", function () {
    const socketUrl = 'http://localhost:8080/ws';

    var socket = new SockJS(socketUrl);
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Conected: ' + frame);
        stompClient.subscribe('/topic/events', function (event) {
            var event = JSON.parse(event.body);
            createEventBox(event);
        });
    });

    function createEventBox(event) {
        // Create HTML elements
        var divEventBox = document.createElement('div');
        divEventBox.className = 'event-box';

        var heading2 = document.createElement('h2');
        heading2.className = 'heading-2';
        heading2.innerText = event.item.name;

        var ul = document.createElement('ul');
        ul.className = 'event-box-list';

        // Iterate over the properties of the 'item' object
        for (var property in event.item) {
            if (event.item.hasOwnProperty(property)) {
                var li = document.createElement('li');
                li.innerText = `${property}: ${event.item[property]}`;
                ul.appendChild(li);
            }
        }

        var divEventBoxState = document.createElement('div');
        divEventBoxState.className = 'event-box-state';
        divEventBoxState.innerText = event.state;
        // Set background color based on the value of 'event.state'
        switch (event.state) {
            case 'EDITED':
                divEventBoxState.style.backgroundColor = 'yellow';
                break;
            case 'DELETED':
                divEventBoxState.style.backgroundColor = 'red';
                break;
            default:
                divEventBoxState.style.backgroundColor = 'green';
                break;
        }

        // Attach elements to divEventBox
        divEventBox.appendChild(heading2);
        divEventBox.appendChild(ul);
        divEventBox.appendChild(divEventBoxState);

        var eventsContainer = document.getElementById('event-content');
        eventsContainer.insertBefore(divEventBox, eventsContainer.firstChild);
    }
});
