var express = require("express");
var fs = require('fs')
var app = express();
var bodyParser = require('body-parser')
 
var app = express()
 
app.listen(3000, () => {
 console.log("Server running on port 3000");
});

app.post("/add_query", bodyParser.text(), (req, res) => {
    var query = req.body
    fs.writeFileSync('./data/queries.txt', query + '\n')
    res.send(`query received: ${query}`)
});

