# syntax=docker/dockerfile:1.7
FROM maven:3.9.9-eclipse-temurin-21 AS dev

WORKDIR /workspace

# Default command for an interactive local-dev container.
CMD ["bash"]
