exports.handler = function (event, context, callback) {

    var generatednumber = Math.floor(Math.random() * 10)

    callback(null, generatednumber);
};
