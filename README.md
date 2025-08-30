# Lottery Simulation Project

## Overview
This Java-based lottery simulation system, developed as a school assignment, models a complete lottery ecosystem, including ticket purchasing, draw processing, prize calculation, and financial management. Designed with object-oriented principles, the project emphasizes modularity, extensibility, and maintainability. It includes unit tests to ensure functionality and serves as an academic exercise to explore Java programming, software design, and testing methodologies.

## Project Structure
The project is organized into the following key directories and packages:

- **src/totolotek**: Core Java classes implementing the lottery system's functionality.
- **src/testyJednostkowe**: JUnit tests for validating system components.
- **src/wyjątki**: Custom exception classes for error handling.

### Key Files
- **totolotek/**:
  - `Main.java`: Entry point for the simulation, initializing the system and running a predefined number of draws.
  - `Centrala.java`: Manages central lottery operations, including draws, prize pools, and financial transactions.
  - `Kupon.java`: Represents a lottery ticket with attributes such as unique identifiers, bets, and draw participation.
  - `Blankiet.java`: Models a ticket template for user-defined or random bets.
  - `Gracz.java` and subclasses (`GraczMinimalista`, `GraczLosowy`, `GraczStałoBlankietowy`, `GraczStałoLiczbowy`): Define different player strategies for purchasing tickets.
  - `Losowanie.java`: Handles individual lottery draws, including random number generation and prize calculation.
  - `Kolektura.java`: Simulates a point-of-sale, managing ticket sales and prize payouts.
  - `BudżetPaństwa.java`: Tracks taxes and subsidies related to lottery operations.
  - `Informacja.java`: Stores constants and provides system-wide information.
  - `PoleBlankietu.java`: Represents a single field on a lottery ticket.
  - `Zakład.java`: Models a single bet with a set of numbers.

- **testyJednostkowe/**:
  - `TestKuponyBlankiety.java`: Tests ticket creation, validation, and pricing.
  - `TestSystem.java`: Verifies system functionalities like draws, prize pools, and ticket sales.
  - `TestGracze.java`: Tests player behaviors and financial transactions.

- **wyjątki/**:
  - `Wyjątek.java`: Base custom exception class.
  - `WyjątekNiepoprawnegoArgumentu.java`: Handles invalid input arguments.
  - `WyjątekNielegalnejAktywności.java`: Manages unauthorized operations, such as invalid ticket redemption.

## Key Features and Design
1. **Object-Oriented Structure**:
   - Uses inheritance and polymorphism through an abstract `Gracz` class, extended by four player types (`GraczMinimalista`, `GraczLosowy`, `GraczStałoBlankietowy`, `GraczStałoLiczbowy`), each with distinct ticket-purchasing logic.
   - Encapsulation ensures data integrity via private fields and controlled method access.

2. **Error Handling**:
   - Custom exceptions (`Wyjątek`, `WyjątekNiepoprawnegoArgumentu`, `WyjątekNielegalnejAktywności`) manage invalid inputs and unauthorized actions, such as attempts to redeem unverified tickets.

3. **Unit Testing**:
   - JUnit tests validate ticket creation, draw processing, prize calculations, and player transactions. Reflection is used to test private methods and fields, ensuring comprehensive coverage.

4. **Financial Tracking**:
   - Manages ticket prices, taxes, and prize payouts. The `BudżetPaństwa` class handles tax collection and subsidies, while `Centrala` oversees prize pools and transactions.

5. **Ticket System**:
   - The `Blankiet` and `PoleBlankietu` classes enable flexible ticket creation for user-defined or random bets, with validation ensuring bets contain six unique numbers from 1 to 49.

6. **Draw and Prize Logic**:
   - The `Losowanie` class generates random numbers and calculates prize pools based on predefined percentages and guaranteed amounts, supporting cumulative jackpots (`kumulacja`) for top-tier prizes.

## Technical Details
- **Modularity**: Classes like `Centrala`, `Kolektura`, and `Gracz` separate concerns, allowing independent modification of system components.
- **Randomization**: The `Centrala.losujLiczby` method uses a Fisher-Yates shuffle to generate random, sorted numbers for draws and bets.
- **Testing Approach**: Unit tests access private members via reflection to validate internal state while preserving encapsulation.
- **Exception Hierarchy**: A structured exception system distinguishes between invalid arguments and illegal activities, enhancing error management.
- **Extensibility**: The system is designed with future functionality in mind, such as the addition of new player strategies, facilitated by the abstract `Gracz` class and modular architecture.

## Usage
To run the simulation:
1. Compile and execute `Main.java` to simulate a lottery with 10 collection points, 200 players of different types, and 20 draws.

## Dependencies
- Java 8 or higher (for core functionality and JUnit tests).
- JUnit 5 (for unit testing).

## Conclusion
This lottery simulation project models real-world lottery operations through a modular, object-oriented Java system with comprehensive unit testing. It serves as an academic exercise to demonstrate software design and implementation skills in a structured, maintainable codebase.
