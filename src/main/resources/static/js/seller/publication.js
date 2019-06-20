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

    function sellMessage(publicationId, messageId) {
        $.post('/publication/message/' + publicationId + '/sell/' + messageId, function(message) {
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

        let sold = false;

        for(message of messages) {

            if(message.sold) {

                const dateTime = moment(new Date(message.messageDateTime)).format("YYYY-MM-DD HH:mm:ss");

                $messages.html(
                    $('<li id="li' + message.id + '" class="list-group-item">[<b>' + dateTime + '</b>] <a href="#">' + message.client.username + '</a>: ' + message.description + '<span class="text-success"> Vendido </span></li>')
                );

                sold = true;

                break;
            }
        }

        if(!sold) {

            for(message of messages) {

                const dateTime = moment(new Date(message.messageDateTime)).format("YYYY-MM-DD HH:mm:ss");
                const liked = message.liked ? '<span class="text-success font-weight-bold">✓</span>' : '';
                const btnLike = !message.liked ? $('<button id="like-' + publicationId + '-' + message.id + '" class="btn btn-outline-success btn-sm">Me Gusta</button>') : undefined;
                const btnSell = $('<button id="sell-' + publicationId + '-' + message.id + '" class="btn btn-outline-info btn-sm float-right">Vender</button>');

                $messages.append(
                    $('<li id="li' + message.id + '" class="list-group-item">[<b>' + dateTime + '</b>] <a href="#">' + message.client.username + '</a>: ' + message.description + ' ' + liked + '</li>')
                );

                if(btnLike) {
                    $('#li' + message.id).append(btnLike);
                    $('#like-' + publicationId + '-' + message.id + '').bind("click", function() {
                        likeMessage($(this)[0].id.split('-')[1], $(this)[0].id.split('-')[2]);
                    });
                }

                $('#li' + message.id).append(btnSell);

                $('#sell-' + publicationId + '-' + message.id + '').bind("click", function() {
                    sellMessage($(this)[0].id.split('-')[1], $(this)[0].id.split('-')[2]);
                });
            }
        }
    }

    $.get("/publication/messages/seller/" + publicationId, function(data) {
        messages = data;
        console.log("GET", messages);
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
            stompClient.subscribe('/queue/seller', function (msg) {
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