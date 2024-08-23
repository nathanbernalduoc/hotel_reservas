#API's Hotel reservaciones.
##Desarrollo con Spring Boot.
###Listar habitaciones
http://localhost:8080/habitacion/list<br>
Response esperado:
'''json
[
    {
        "id": 1,
        "habitacionNumero": "A01",
        "piso": 1,
        "camas": 3,
        "observacion": "Test #1"
    },
    {
        "id": 2,
        "habitacionNumero": "A02",
        "piso": 1,
        "camas": 1,
        "observacion": "Test #2"
    },
    {
        "id": 3,
        "habitacionNumero": "B01",
        "piso": 2,
        "camas": 2,
        "observacion": "Test #3"
    },
    {
        "id": 4,
        "habitacionNumero": "B02",
        "piso": 2,
        "camas": 3,
        "observacion": "Test #4"
    },
    {
        "id": 5,
        "habitacionNumero": "C01",
        "piso": 3,
        "camas": 4,
        "observacion": "Full"
    }
]
'''
