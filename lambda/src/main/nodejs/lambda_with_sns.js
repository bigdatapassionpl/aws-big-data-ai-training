var aws_sdk = require('aws-sdk');
aws_sdk.config.region = 'us-east-1';

exports.handler = function (event, context) {
    var sns = new aws_sdk.SNS();

    sns.publish({
        Message: 'Test Message to SNS from Lambda',
        TopicArn: 'arn:aws:sns:us-east-1:252594130684:testSNS'
    }, function (err, data) {
        if (err) {
            console.log(err.stack);
            return;
        }
        console.log('Message sent successfully');
        console.log(data);
    });
};