# Coding Standards

## General

- Business logic belongs in Service classes.
- Controllers must be thin.
- DTOs are never JPA entities.
- Never expose Entity directly from REST APIs.
- Use constructor injection only.
- No System.out.println().
- Use meaningful names.
- Keep methods focused on a single responsibility.

## Git

- One feature per branch.
- One logical change per commit.

## Testing

- New business logic should include automated tests.
