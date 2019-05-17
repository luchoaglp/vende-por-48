$(function() {

    let stompClient = null;
    const $messages = $('#messages');
    const $err = $('#err');

    let connect = () => {
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
                    <a href="#">${message.client.username}</a>:
                    ${message.message}
                </li>`);
            });
        });
    }

    connect();

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