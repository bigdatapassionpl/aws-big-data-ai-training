const { SNSClient, PublishCommand } = require('@aws-sdk/client-sns');

const sns = new SNSClient({ region: 'us-east-1' });

exports.handler = async (event, context) => {
    try {
        const data = await sns.send(new PublishCommand({
            Message: 'Test Message to SNS from Lambda',
            TopicArn: 'arn:aws:sns:us-east-1:932771310344:PolitechnikaSNSTopic'
        }));
        console.log('Message sent successfully');
        console.log(data);
    } catch (err) {
        console.log(err.stack);
    }
};
