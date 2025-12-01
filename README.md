### Overview

- The presentation covers key architectural patterns in software design including Domain-Driven Design (DDD), Command Query Responsibility Segregation (CQRS), and Event Sourcing
- These patterns help manage complex business logic, optimize performance, and maintain data integrity in distributed systems

### Domain-Driven Design Concepts

- **Value Objects**: Used when the same value might be repeated across different entities (like a title) where equality is based on content
- **Association Blocks**: Manage relationships between entities in the domain model
    - Example: Many-to-many relationship between products and orders requires an additional table (order details)
- **Aggregates**: Group related entities together as the model grows in size and complexity

### CQRS (Command Query Responsibility Segregation)

- Separates the system into two parts: commands (write operations) and queries (read operations)
- **Commands**: Change the state of the system
    - Examples: Adding to cart, updating profiles, placing orders
    - Focus on expressing an intention rather than direct data manipulation
- **Queries**: Only retrieve information without changing state
- **Benefits**:
    - Performance optimization through independent scaling of each side
    - Flexibility to change how data is stored without affecting queries
    - Better suited for complex applications with diverse user interactions
- **Implementation Approaches**:
    - Single database with separate read/write models
    - Separate databases for reading and writing (polyglot architecture)
    - Eventual consistency when using separate databases

### Event Sourcing

- Stores every state change as an immutable event in an append-only log rather than just the current state
- **Benefits**:
    - Complete audit trail of all system changes
    - Ability to rebuild state at any point in time
    - Natural support for CQRS architecture
- **Key Concepts**:
    - **Sourcing**: Deriving current state from past events rather than storing state directly
    - **Hydration**: Rebuilding current state by applying all relevant events
    - **Replay**: Processing past events to regenerate system state for debugging, testing, or data migration
- **Performance Optimization**:
    - **Snapshots**: Periodically capturing current state to avoid replaying all events
    - **Materialized Views**: Maintaining current state in separate database, updated in real-time

### Integration of CQRS and Event Sourcing

- Commands generate events rather than modifying state directly
- Events are stored in an event log/store
- Query side processes events to update read models
- Event propagation typically uses message brokers like Kafka, RabbitMQ, or AWS SNS
- Example flow:
    - Seller updates product price, sending a command to the command service
    - Command service validates and generates a "price updated" event
    - Event is stored in event log and propagated to query service
    - Query service updates the read model with new price
    - When users query product details, they see the updated price
---


# Generative AI Integration (RAG Pattern)
Concept: Integration of Large Language Models (LLM) to interact with the application data using natural language.

Pattern: RAG (Retrieval-Augmented Generation). The AI does not access the database directly; the system retrieves relevant data and "feeds" it to the AI context.

Architecture Flow:

User Query: User asks a question (e.g., "What is my balance?").

Retrieval (Query Side): Spring Boot Query Service fetches structured data (DTOs) from the Read Database (PostgreSQL).

Prompt Engineering: The system constructs a prompt containing the user question + the retrieved JSON data.

Generation: The LLM (Ollama/OpenAI) generates a natural language response based only on the provided context.

Technology Stack:

Spring AI: Framework for orchestrating AI interactions in Java.

Ollama: Local execution of models (e.g., Llama 3.2) for privacy and cost efficiency.

Docker: Containerization of the AI model server.

# CI/CD & Automation (DevOps)
Goal: Automate the integration and delivery process to ensure code quality and deployment readiness.

Tool: GitHub Actions (Workflows defined in YAML).

Pipeline Structure:

Trigger: Activates on every git push to the main branch.

Backend Job:

Sets up Java JDK 17 environment.

Caches Maven dependencies for speed.

Compiles code and runs Unit Tests (mvn clean install).

Frontend Job:

Sets up Node.js environment.

Installs dependencies (npm install).

Builds the production bundle (npm run build).

Benefits:

Immediate feedback on build errors.

Ensures that both Backend (Spring Boot) and Frontend (React) are always in a deployable state.

Containerization & Infrastructure
Docker Compose: Orchestrates the multi-container environment locally.

# Services Managed:

Axon Server: Event Store and Command Bus hub.

PostgreSQL: Relational database for the Read Model (Projections).

Spring Boot App: The application logic (Command & Query sides).

Ollama: The AI engine running in a container with persistent model storage.

pgAdmin: Database management interface.

Networking: All services communicate via an internal Docker bridge network (accounts-net), isolating them from external access except for exposed ports.
