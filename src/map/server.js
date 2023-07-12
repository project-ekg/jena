var express = require("express");
var fs = require('fs')
var app = express();
var bodyParser = require('body-parser')
 
var app = express()
 
app.listen(5050, () => {
 console.log("Server running on port 5050");
});

app.post("/add_query", bodyParser.text(), (req, res) => {
    var query = req.body
    console.log(query)
    fs.writeFileSync('./data/query.txt', query + '\n')
    res.send(`query received: ${query}`)
});

