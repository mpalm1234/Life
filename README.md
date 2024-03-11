# Conway's Game of Life

## Required
* Java 17
* Maven

## Running app locally
* Run the following command from your terminal: `mvn spring-boot:run`
* Hit the endpoint: `POST http://localhost:8080/api/life/start`
  * Request body should contain the desired starting coordinates in JSON. Example format:
  ```
  {
    "coordinates": [
      "5,5",
      "6,6",
      "7,6",
      "5,7",
      "6,7"
    ]
  }
  ```
* A window should pop up that produces Conway's Game of Life starting with the coordinates provided in the request 

## Running unit tests
* Run the following command from your terminal: `mvn test`

## Resources
* https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
* https://playgameoflife.com/info