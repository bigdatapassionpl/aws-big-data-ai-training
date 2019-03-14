var AWS = require('aws-sdk');
var s3 = new AWS.S3();

exports.handler = function (event, context, callback) {

    // S3 params (bucket and object key)
    var src_bkt = 'bucket';
    var src_key = 'file.txt';

    // Get the object
    s3.getObject({
        Bucket: src_bkt,
        Key: src_key
    }, function (err, data) {
        if (err) {
            console.log(err, err.stack);
            callback(err);
        } else {
            console.log('\n\n' + data.Body.toString() + '\n');
            callback(null, data.Body.toString());
        }
    });
};