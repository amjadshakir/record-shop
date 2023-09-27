# Record Shop Application

The Record Shop Application is a Spring Boot-based application that simplifies the management of music records. With
this application, users can effortlessly add, retrieve, update, and delete music records through RESTful endpoints that
interact with a music records database.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

The Record Shop Application boasts a variety of features that cater to music record management:

- **Add Music Records**: Easily add new music records, including album name, artist, release year, stock, and genre.

- **Retrieve Music Records**: Retrieve music records by release year, artist, or album name for quick access to your
  music catalog.

- **Update Stock Amount**: Effortlessly update the stock amount of a music record as your inventory changes.

- **Delete Music Records**: Delete unwanted music records by specifying their unique ID.

- **Exception Handling**: Robust exception handling ensures a smooth user experience by handling scenarios like record
  not found or invalid input.

## Prerequisites

Before diving into the Record Shop Application, ensure that you've met these prerequisites:

- **Java Development Kit (JDK)**: Install JDK 11 or a higher version to compile and run the application.

- **Database**: Set up a PostgreSQL or another compatible database and ensure it's correctly configured and running.

## Getting Started

To kickstart your journey with the Record Shop Web Application, follow these simple steps:

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/amjadshakir/record-shop.git
   cd record-shop
   ```

2. **Database Configuration**:

    - Locate the `application.properties` file in the `src/main/resources` directory.
    - Provide the necessary database connection details to configure your database.

3. **Build and Run**:

    - Build the application using Maven:

      ```bash
      mvn clean install
      ```

    - Run the application by executing `RecordShopApplication` within your IDE or through the command line:

      ```bash
      java -jar target/record-shop-0.1.jar
      ```

   The application will now be up and running at `http://localhost:8080`.

## Usage

Once the application is running, you can seamlessly manage your music records by making requests to the provided API
endpoints.
Feel free to use tools like Postman or `curl` for making API requests and interacting with your music catalog.

## API Endpoints

The Record Shop Application offers the following API endpoints for your convenience:

- **POST /api/v1/record**: Add a new music record to your collection.
- **GET /api/v1/record/releaseYear/{releaseYear}**: Retrieve music records by release year.
- **DELETE /api/v1/record/{recordId}**: Delete a specific music record by its unique ID.
- **PUT /api/v1/record/{recordId}/{stock}**: Update the stock amount of a music record.
- **GET /api/v1/record/album/{albumName}**: Retrieve music records by album name.

## Contributing

We welcome contributions to enhance the Record Shop Application. Here's how you can get involved:

1. **Fork the Project**: Fork the project to your GitHub account.

2. **Create a Feature Branch**: Create a feature branch in your forked repository, e.g., `feature/your-feature`.

3. **Commit Your Changes**: Commit your code changes with a descriptive message, e.g., 'Add some feature'.

4. **Push to Your Branch**: Push your changes to the branch in your forked repository.

5. **Open a Pull Request**: Open a pull request to the main project repository.

## License

This project is licensed under the MIT License. For complete licensing details, refer to the [LICENSE](LICENSE) file.

# record-shop

# How to run the application

# Key features of the application
![img.png](img.png): list all albums in stock

![img.png](img.png): list all albums by a given artist

![img.png](img.png): list all albums by a given release year

![img.png](img.png): list all albums by a given genre

![img.png](img.png): get album information by album name

![img.png](img.png): add new albums into the database

![img.png](img.png): update album details

![img.png](img.png): update stock amounts of a particular album

![img.png](img.png): delete albums from the inventory 

# Assumptions

# Approaches
 
# Future thoughts




