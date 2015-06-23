var express = require('express');
var fs = require('fs');

var app = express.createServer(express.logger());

app.get('/', function(request, response) {
  // The fs.readFileSync method returns a buffer if no encoding is specifed.
  // Use the buffer's toString method to get the correct encoding.
  var buffer = fs.readFileSync('./index.html');
  response.send(buffer.toString('utf-8'));
});

var port = process.env.PORT || 5000;
app.listen(port, function() {
  console.log("Listening on " + port);
});
