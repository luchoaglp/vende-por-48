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
        $messages.append(
            `<li class="list-group-item">
                [<b>${dateTimeStr}</b>]
                <a href="#">${message.client.username}</a>:
                ${message.message}
                ${liked}
            </li>`);
    }

    $.get("/publication/messages/" + publicationId, function(data) {
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

    $('#send').click(() => {

        $.ajax({
            method: "POST",
            url: `/publication/message/${publicationId}`,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({ message: $.trim($('#msg').val()) })
        })
        .done(function(res) {
            console.log('Ajax', res);
        })
        .fail(function(jqXHR, textStatus) {
            console.error(jqXHR, textStatus);
            $err.text(jqXHR.responseJSON.message);
        });

    });

    $(window).bind('beforeunload', () => {
        if(stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    });

});