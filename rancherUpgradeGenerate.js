var readline = require('readline');

var args = process.argv.slice(2);
var imageUuid = args[0];

var rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
    terminal: false
});

rl.on('line', function(line) {
    var lineData = JSON.parse(line);
    lineData.launchConfig.imageUuid = 'docker:' + imageUuid;
    console.log(JSON.stringify(lineData.launchConfig));
})
