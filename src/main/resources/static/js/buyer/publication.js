$(function() {

    const $messages = $('#messages');
    const $err = $('#err');
    const $msg = $('#msg');
    const $send = $('#send');
    let messages = [];
    let stompClient = null;

    $msg.focus();

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

        let sold = false;

        for(message of messages) {
            if(message.sold) {

                const dateTime = moment(new Date(message.messageDateTime)).format("YYYY-MM-DD HH:mm:ss");

                $messages.html(
                    $('<li id="li' + message.id + '" class="list-group-item">[<b>' + dateTime + '</b>] <a href="#">' + message.client.username + '</a> <span class="text-success"> Vendido </span></li>')
                );
                sold = true;
            }
        }

        if(!sold) {

            for(message of messages) {

                const dateTime = moment(new Date(message.messageDateTime)).format("YYYY-MM-DD HH:mm:ss");
                const liked = message.liked ? '<span class="text-success font-weight-bold">✓</span>' : '';

                $messages.append(
                    $('<li id="li' + message.id + '" class="list-group-item">[<b>' + dateTime + '</b>] <a href="#">' + message.client.username + '</a>' + liked)
                );
            }
        }
    }

    $.get("/publication/messages/buyer/" + publicationId, function(data) {
        messages = data;
        console.log("GET", messages);
        displayMessages();

    }).fail(function() {
        console.log("Error");
    });

    let connect = () => {
        let socket = new SockJS('/ws');
        const stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/queue/buyer', function (msg) {
                const message = JSON.parse(msg.body);
                replaceMessage(message);
                displayMessages();
                console.log(messages);
            });
        });
    }

    connect();

    $send.click(() => {
        $.ajax({
            method: "POST",
            url: `/publication/message/${publicationId}`,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({ description: $.trim($msg.val()) })
        })
        .done(function(message) {
            $msg.val('');
            $msg.focus();
            console.log("AJAX", message);
        })
        .fail(function(jqXHR, textStatus) {
            $msg.val('');
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