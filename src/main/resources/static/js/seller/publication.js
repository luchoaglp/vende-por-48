$(function() {

    const $messages = $('#messages');
    const $err = $('#err');
    let messages = [];
    let stompClient = null;

    const publication = window.location.pathname.split("/");
    const publicationId = publication[publication.length - 1];

    function displayMsg(message) {
        const dateTime = new Date(message.messageDateTime);
        const dateTimeStr = `${dateTime.getFullYear()}/${dateTime.getMonth() + 1}/${dateTime.getDate()} ${dateTime.getHours()}:${dateTime.getMinutes()}:${dateTime.getSeconds()}`;
        const liked = message.liked ? '<span class="text-success font-weight-bold">✓</span>' : '';
        const likeBtn = !message.liked ? `<a href="/publication/message/${publicationId}/like/${message.id}/" class="btn btn-outline-success btn-sm">Me Gusta</a>` : '';

        $messages.append(
            `<li class="list-group-item">
                [<b>${dateTimeStr}</b>]
                <a href="#">${message.client.username}</a>:
                ${message.message}
                ${liked}${likeBtn}
                <a href="/publication/message/${publicationId}/sell/${message.id}/" class="btn btn-outline-info btn-sm float-right">Vender</button>
            </li>`);
    }

    $.get("/publication/messages/seller/" + publicationId, function(data) {
        messages = data;
        messages.forEach(message => displayMsg(message));

    }).fail(function() {
        console.log("Error");
    });

    let connect = () => {
        let socket = new SockJS('/ws');
        const stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/queue/reply', function (msg) {
                const message = JSON.parse(msg.body);
                displayMsg(message);
            });
        });
    }

    connect();

    $(window).bind('beforeunload', () => {
        if(stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    });

});