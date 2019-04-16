function connect() {
    let socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/queue/reply', function (msg) {
    const message = JSON.parse(msg.body);
        const dateTime = new Date(message.messageDateTime);
        const dateTimeStr = `${dateTime.getFullYear()}/${dateTime.getMonth() + 1}/${dateTime.getDate()} ${dateTime.getHours()}:${dateTime.getMinutes()}:${dateTime.getSeconds()}`;

        console.log('WS', message);
        $messages.prepend(
            `<li class="list-group-item">
                [<b>${dateTimeStr}</b>]
                <a href="#">${message.client.username}</a>
                ${message.message}
                </li>`);
        });
    });
    return stompClient;
}

function disconnect(stompClient) {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

$(function() {

    let stompClient = connect();
    const $messages = $('#messages');

    $('#send').click(() => {

        $.ajax({
            method: "POST",
            url: `/publication/message/${$("#publicationId").val()}`,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({ message: $.trim($('#msg').val()) })
        })
         .done(function(res) {
            console.log('Ajax', res);
        });

    });

    $(window).bind('beforeunload', function() {
        disconnect(stompClient);
    });


});