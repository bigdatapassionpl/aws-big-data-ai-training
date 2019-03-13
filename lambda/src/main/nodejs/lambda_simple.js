
exports.handler = (event, context, callback) => {
    // TODO implement
    let generatednumber = Math.floor(Math.random() * 10)
    callback(null, generatednumber);
};
