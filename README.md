# HotelGo - Java Spark Boilerplate

A minimal Java Spark project to get you started with building REST APIs or web applications.

This README covers **setting up the environment** for development.

---

## Prerequisites

Before you start, make sure you have the following installed:

* [Java JDK 17+](https://adoptium.net/)
* [Maven 3.8+](https://maven.apache.org/install.html)
* Git

Optional for IDEs:

* [VSCode](https://code.visualstudio.com/) + Java Extension Pack
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## Clone the project

```bash
git clone https://github.com/<your-username>/hotel-go.git
cd hotel-go
```

---

## Build and Run

### Using Maven CLI

Compile the project:

```bash
mvn clean compile
```

Run the project:

```bash
mvn exec:java -Dexec.mainClass="com.hotelgo.App"
```

Or package into a JAR and run:

```bash
mvn package
java -cp target/hotel-go-1.0-SNAPSHOT.jar com.hotelgo.App
```

---

### Using VSCode

1. Open VSCode in the project folder:

   ```bash
   code .
   ```
2. Install the **Java Extension Pack** if prompted.
3. VSCode will automatically detect the Maven project.
4. Run the project by pressing the green **Run** button on `App.java` or using the terminal with Maven commands.

---

### Using IntelliJ IDEA

1. Open IntelliJ IDEA → **Open** → Select the project folder.
2. IntelliJ will detect it as a Maven project. Click **Import Changes** if prompted.
3. Build the project: `Build > Build Project`.
4. Run `App.java` directly by right-clicking the file → **Run 'App.main()'**.

---

## Notes

* Ensure Java 17+ and Maven 3.8+ are installed and available in your PATH.
* IDEs like VSCode or IntelliJ will automatically recognize the Maven project and dependencies.
* No additional setup is required beyond cloning, building, and running the project.
