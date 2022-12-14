<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <link rel="stylesheet" href="styles/styles.css">
    <script>
    $(document).ready(function(){
      $("p").click(function(){
        $(this).hide();
      });
    });
    </script>
    <style>

    #statusBar {
      width: 1%;
      height: 30px;
      background-color: #04AA6D;
    }

    #orderStatusPanel {
        width: 20px;
        padding-right: 10px;
        padding-left: 10px;
        padding-top: 3px;
        padding-bottom: 3px;
        border-block-width: 1px;
        border-color: black;
        border: 1px solid green;
        font-family: fantasy, Arial;
      background-color: green;
      }

      .orderStatus{
        padding-right: 10px;
        padding-left: 10px;
        padding-top: 3px;
        padding-bottom: 3px;
        border-block-width: 1px;
        border-color: black;
        color: black;
        border: 1px solid black;
        font-family: fantasy, Arial;
        background-color: #F8F8FF;
      }
      .activeOrderStatus{
        width: 20px;
        padding-right: 10px;
        padding-left: 10px;
        padding-top: 3px;
        padding-bottom: 3px;
        border-block-width: 1px;
        border-color: black;
        font-family: fantasy, Arial;
        border: 1px solid green;
      background-color: green;
      }
    </style>
</head>

<body>

    <h1>Order Status Tracker</h1>

    <div id="customerPanel">
        <p>Hello ${customerId},</p>
        <p>Welcome back and thank you for being a valued customer.</p>
    </div>
    <div id="orderPanel" style="display: none">
        <hr/>
        <p>Your order # <b id="orderId"></b>
            <span id="orderDescription"></span>
        </p>

        <p> Status is now <span id="orderStatusPanel" ></span></p>

        <p id="orderProgress">
        </p>
    </div>

    <br>

    <script>

        function decorateOrderStatus(orderStatusCode)
        {
            $('#orderPanel').css("display", "block");

            switch(orderStatusCode) {
              case 0:
                $('#orderStatusPanel').html("CREATED");
                $('#orderProgress').html("<span class='activeOrderStatus'>CREATED</span> <span class='orderStatus'>CONFIRMED</span> <span  class='orderStatus'>BUILDING</span> <span  class='orderStatus'>FINISHED</span> <span  class='orderStatus'>DISPATCHED</span>");
                break;
              case 1:
                $('#orderStatusPanel').html("CONFIRMED");
                $('#orderProgress').html("<span class='orderStatus'>CREATED</span> <span class='activeOrderStatus'>CONFIRMED</span> <span  class='orderStatus'>BUILDING</span> <span  class='orderStatus'>FINISHED</span> <span  class='orderStatus'>DISPATCHED</span>");
                break;
              case 2:
                $('#orderStatusPanel').html("BUILDING");
                $('#orderProgress').html("<span class='orderStatus'>CREATED</span> <span class='orderStatus'>CONFIRMED</span> <span  class='activeOrderStatus'>BUILDING</span> <span  class='orderStatus'>FINISHED</span> <span  class='orderStatus'>DISPATCHED</span>");
                break;
              case 3:
                $('#orderStatusPanel').html("FINISHED");
                $('#orderProgress').html("<span class='orderStatus'>CREATED</span> <span class='orderStatus'>CONFIRMED</span> <span  class='orderStatus'>BUILDING</span> <span  class='activeOrderStatus'>FINISHED</span> <span  class='orderStatus'>DISPATCHED</span>");
                break;
              case 4:
                $('#orderStatusPanel').html("DISPATCHED");
                $('#orderProgress').html("<span class='orderStatus'>CREATED</span> <span class='orderStatus'>CONFIRMED</span> <span  class='orderStatus'>BUILDING</span> <span  class='orderStatus'>FINISHED</span> <span  class='activeOrderStatus'>DISPATCHED</span>");
                break;
              default:
                $('#orderStatusPanel').html("UNKNOWN");
                $('#orderProgress').html("<span class='orderStatus'>CREATED</span> <span class='orderStatus'>CONFIRMED</span> <span  class='orderStatus'>BUILDING</span> <span  class='orderStatus'>FINISHED</span> <span  class='orderStatus'>DISPATCHED</span>");
            }

        }
        //-----------------------------------------
        $(document).ready(
        function() {
            var sse = new EventSource('orders/order/1');

            sse.onmessage = function(message) {

            console.log("data: "+message.data);
            var orderDomain = JSON.parse(message.data);

            $('#orderId').text(orderDomain.id);
            $('#orderDescription').text(orderDomain.description);

            decorateOrderStatus(orderDomain.orderStatusCode);

           }
       }
    );
    </script>
</body>
</html>
