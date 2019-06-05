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

    function likeMessage(publicationId, messageId) {
        $.post('/publication/message/' + publicationId + '/like/' + messageId, function(message) {
            console.log("AJAX", message);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error(jqXHR, textStatus, errorThrown);
        });
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
            btnLike = !message.liked ? $('<button class="btn btn-outline-success btn-sm">Me Gusta</button>') : undefined;

            $messages.append(
                $('<li id="li' + message.id + '" class="list-group-item">[<b>' + dateTimeStr + '</b>] <a href="#">' + message.client.username + '</a>: ' + message.description + ' ' + liked  + '<a href="/publication/message/${publicationId}/sell/${message.id}/" class="btn btn-outline-info btn-sm float-right">Vender</a></li>')
            );

            if(btnLike) {
                $('#li' + message.id).append(btnLike);
                $(btnLike).bind("click", function() {
                    likeMessage(publicationId, message.id);
                });
            }
        });
    }

    $.get("/publication/messages/seller/" + publicationId, function(data) {
        messages = data;
        displayMessages();
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        console.error(jqXHR, textStatus, errorThrown);
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
                console.log(messages);
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