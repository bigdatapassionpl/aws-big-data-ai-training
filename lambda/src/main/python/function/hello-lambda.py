def my_handler(event, context):

    message = 'Hello {} {}!'.format(event['firstName'],
                                    event['lastName'])
    return {
        'message': message
    }
