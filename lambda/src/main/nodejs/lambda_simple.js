export const handler = async (event) => {
    // Generuj losową liczbę z zakresu 1 do 100
    const randomNum = Math.floor(Math.random() * 100) + 1;

    // Przygotuj odpowiedź
    const response = {
        statusCode: 200,
        body: JSON.stringify({
            randomNumber: randomNum
        }),
    };

    return response;
};
