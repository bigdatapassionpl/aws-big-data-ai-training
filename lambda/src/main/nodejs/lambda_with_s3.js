const AWS = require('aws-sdk');

exports.handler = async (event, context) => {
    // Set up the S3 client
    const s3 = new AWS.S3();

    // Specify the bucket and file key
    const bucketName = 'radek-politechnika-2023-11-25';
    const fileKey = 'test.txt';

    try {
        // Get the file from S3
        const params = {
            Bucket: bucketName,
            Key: fileKey
        };

        const s3Object = await s3.getObject(params).promise();

        // Process the file content (you can customize this part based on your needs)
        const fileContent = s3Object.Body.toString('utf-8');

        // Log or process the file content as needed
        console.log('File Content:', fileContent);

        // You can also return the file content in the response
        const response = {
            statusCode: 200,
            body: JSON.stringify({
                fileContent: fileContent
            }),
        };

        return response;
    } catch (error) {
        console.error('Error:', error);

        // Return an error response
        const response = {
            statusCode: 500,
            body: JSON.stringify({
                error: 'Failed to retrieve the file from S3'
            }),
        };

        return response;
    }
};