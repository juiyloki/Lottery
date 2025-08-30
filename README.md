# Lottery Simulation Project

## Overview
This project is a comprehensive lottery simulation system implemented in Java as part of a school assignment. It models a complete lottery ecosystem, including ticket purchasing, draw processing, prize calculation, and financial management. The system is designed with object-oriented principles, ensuring modularity, extensibility, and maintainability. The project includes unit tests to verify functionality, demonstrating proficiency in Java programming, software design, and testing methodologies as a student project.

## Project Structure
The project is organized into the following key directories and packages:

- **src/totolotek**: Core Java classes implementing the lottery system's functionality.
- **src/testyJednostkowe**: JUnit tests for validating the system's components.
- **src/wyjątki**: Custom exception classes for robust error handling.

### Key Files
- **totolotek/**:
  - `Main.java`: Entry point for the simulation, initializing the system and running a predefined number of draws.
  - `Centrala.java`: Manages the lottery's central operations, including draws, prize pools, and financial transactions.
  - `Kupon.java`: Represents a lottery ticket with attributes like unique identifiers, bets, and draw participation.
  - `Blankiet.java`: Models a lottery ticket template for user-defined or random bets.
  - `Gracz.java` and subclasses (`GraczMinimalista`, `GraczLosowy`, `GraczStałoBlankietowy`, `GraczStałoLiczbowy`): Implement different player strategies for purchasing tickets.
  - `Losowanie.java`: Handles individual lottery draws, including random number generation and prize calculation.
  - `Kolektura.java`: Simulates a lottery point-of-sale, managing ticket sales and prize payouts.
  - `BudżetPaństwa.java`: Tracks taxes and subsidies related to lottery operations.
  - `Informacja.java`: Stores constants and provides access to system-wide information.
  - `PoleBlankietu.java`: Represents a single field on a lottery ticket.
  - `Zakład.java`: Models a single bet with a set of numbers.

- **testyJednostkowe/**:
  - `TestKuponyBlankiety.java`: Tests ticket creation, validation, and pricing.
  - `TestSystem.java`: Verifies core system functionalities like draws, prize pools, and ticket sales.
  - `TestGracze.java`: Tests player behaviors and financial transactions.

- **wyjątki/**:
  - `Wyjątek.java`: Base custom exception class.
  - `WyjątekNiepoprawnegoArgumentu.java`: Handles invalid input arguments.
  - `WyjątekNielegalnejAktywności.java`: Manages illegal operations, such as unauthorized ticket redemption.

## Key Features and Design Highlights
1. **Object-Oriented Design**:
   - The system leverages inheritance and polymorphism, with `Gracz` as an abstract base class extended by four player types (`GraczMinimalista`, `GraczLosowy`, `GraczStałoBlankietowy`, `GraczStałoLiczbowy`), each implementing unique ticket-purchasing strategies.
   - Encapsulation is enforced through private fields and controlled access via methods, ensuring data integrity.

2. **Robust Error Handling**:
   - Custom exceptions (`Wyjątek`, `WyjątekNiepoprawnegoArgumentu`, `WyjątekNielegalnejAktywności`) provide precise error management for invalid inputs and unauthorized actions, such as attempting to redeem an unauthorized ticket.

3. **Comprehensive Testing**:
   - JUnit tests cover critical functionalities, including ticket validation, draw processing, prize calculations, and player financial transactions. Reflection is used to access private methods and fields, ensuring thorough testing of internal logic.

4. **Financial Management**:
   - The system accurately tracks ticket prices, taxes, and prize payouts. The `BudżetPaństwa` class manages tax collection and subsidies, while `Centrala` handles prize pools and financial transactions.

5. **Flexible Ticket System**:
   - The `Blankiet` and `PoleBlankietu` classes support flexible ticket creation, allowing both user-defined and random bets. Validation ensures that only valid bets (six unique numbers from 1 to 49) are accepted.

6. **Draw and Prize Logic**:
   - The `Losowanie` class generates random numbers and calculates prize pools based on predefined percentages and guaranteed amounts. The system supports cumulative jackpots (`kumulacja`) when no top-tier winners are present.

## Technical Highlights
- **Modular Design**: The separation of concerns between classes like `Centrala`, `Kolektura`, and `Gracz` ensures that each component handles a specific aspect of the lottery system, making it easy to extend or modify.
- **Randomization**: The `Centrala.losujLiczby` method implements a Fisher-Yates shuffle algorithm to generate random, sorted numbers for draws and random bets, ensuring fairness and efficiency.
- **Testing Rigor**: Unit tests use reflection to access private members, allowing comprehensive validation of internal state without compromising encapsulation.
- **Exception Hierarchy**: A well-defined exception hierarchy enhances error handling, with specific exceptions for argument validation and illegal activities, improving system reliability.

## Usage
To run the simulation:
1. Compile and execute `Main.java` to simulate a lottery with 10 collection points, 200 players of different types, and 20 draws.

## Dependencies
- Java 8 or higher (for core functionality and JUnit tests).
- JUnit 5 (for unit testing).

## Future Improvements
- Add a user interface (CLI or GUI) to allow interactive ticket purchasing and result checking.
- Implement persistent storage for game state using a database or file-based system.
- Enhance player strategies with additional customization options for bet selection.

## Conclusion
This lottery simulation project showcases a robust, well-tested, and modular system that effectively models real-world lottery operations. It demonstrates proficiency in Java, object-oriented programming, and unit testing, making it a strong example of software engineering principles applied in an academic context.
