$(function() {

    const $messages = $('#messages');
    const $err = $('#err');
    let messages = [];
    let stompClient = null;

    const publication = window.location.pathname.split("/");
    const publicationId = publication[publication.length - 1];

    function replaceMessage(message) {
        for(let i = 0; i < messages.length; i++) {
            if(messages[i].id === message.id) {
                messages[i] = message;
                return;
            }
        }
        messages.push(message);
    }

    function displayMessages() {
        messages.sort((msg1, msg2) => {
            if(msg1.liked === msg2.liked) {
                return new Date(msg1.messageDateTime) > new Date(msg2.messageDateTime) ? -1 : 1;
            }
            return msg1.liked ? -1 : 1;
        });
        $messages.html('');
        messages.forEach(message => {

            const dateTime = new Date(message.messageDateTime);
            const dateTimeStr = `${dateTime.getFullYear()}/${dateTime.getMonth() + 1}/${dateTime.getDate()} ${dateTime.getHours()}:${dateTime.getMinutes()}:${dateTime.getSeconds()}`;
            const liked = message.liked ? '<span class="text-success font-weight-bold">✓</span>' : '';

            $messages.append(
                $('<li id="li' + message.id + '" class="list-group-item">[<b>' + dateTimeStr + '</b>] <a href="#">' + message.client.username + '</a>' + liked)
            );
        });
    }

    $.get("/publication/messages/buyer/" + publicationId, function(data) {
        messages = data;
        displayMessages();

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
                replaceMessage(message);
                displayMessages();
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
            data: JSON.stringify({ description: $.trim($('#msg').val()) })
        })
        .done(function(message) {
            console.log("AJAX", message);
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